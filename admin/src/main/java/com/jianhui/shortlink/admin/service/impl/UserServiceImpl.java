package com.jianhui.shortlink.admin.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jianhui.shortlink.admin.commons.convention.exception.ClientException;
import com.jianhui.shortlink.admin.commons.convention.result.Result;
import com.jianhui.shortlink.admin.commons.convention.result.Results;
import com.jianhui.shortlink.admin.commons.enums.UserErrorCodeEnum;
import com.jianhui.shortlink.admin.dao.entity.UserDO;
import com.jianhui.shortlink.admin.dao.mapper.UserMapper;
import com.jianhui.shortlink.admin.remote.dto.req.UserRegisterReqDTO;
import com.jianhui.shortlink.admin.remote.dto.resp.UserRespDTO;
import com.jianhui.shortlink.admin.service.UserService;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import static com.jianhui.shortlink.admin.commons.constant.RedisCacheConstant.LOCK_USER_REGISTER_KEY;

@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, UserDO> implements UserService {

    private final RBloomFilter<String> userRegisterCachePenetrationBloomFilter;
    private final RedissonClient redissonClient;

    @Override
    public Result<UserRespDTO> getUserByUsername(String username) {
        LambdaQueryWrapper<UserDO> wp = Wrappers.lambdaQuery(UserDO.class).eq(UserDO::getUsername, username);
        UserDO user = this.getOne(wp);

        if(user == null){
            throw new ClientException(UserErrorCodeEnum.USER_NULL); //传的是errorCode
        }

        UserRespDTO userRespDTO = new UserRespDTO();
        BeanUtils.copyProperties(user,userRespDTO);

        return Results.success(userRespDTO);
    }

    @Override
    public Boolean hasUsername(String username) {
        //存在用户名就返回false
        return userRegisterCachePenetrationBloomFilter.contains(username);
    }

    @Override
    public void register(UserRegisterReqDTO userReqDTO) {
        //是否存在用户名
        if (hasUsername(userReqDTO.getUsername())){
            throw new ClientException(UserErrorCodeEnum.USER_NAME_EXIST);
        }

        //创建分布式锁
        RLock lock = redissonClient.getLock(LOCK_USER_REGISTER_KEY + userReqDTO.getUsername());
        try {
            //尝试获取锁
            if(lock.tryLock()){
                int inserted = baseMapper.insert(BeanUtil.toBean(userReqDTO, UserDO.class));

                //是否插入成功
                if(inserted < 1){
                    throw new ClientException(UserErrorCodeEnum.USER_SAVE_ERROR);
                }

                //将username添加到布隆过滤器
                userRegisterCachePenetrationBloomFilter.add(userReqDTO.getUsername());
                return;
            }
            //假设获取到锁的用户一定能注册成功,没获取到锁的请求直接抛异常
            throw new ClientException(UserErrorCodeEnum.USER_EXIST);
        }finally {
            lock.unlock();
        }
    }

}
