package com.mylawyer.community.service;

import com.mylawyer.community.mapper.UserMapper;
import com.mylawyer.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public void CreateOrUpdate(User newUser) {
        User user = userMapper.findByAccountId(newUser.getAccountId());
        if(user == null){
            newUser.setGmtCreate(System.currentTimeMillis());
            newUser.setGmtModified(user.getGmtCreate());
            userMapper.insert(newUser);
        }else{//update
            user.setGmtModified(System.currentTimeMillis());
            user.setName(newUser.getName());
            user.setToken(newUser.getToken());
            user.setAvatarUrl(newUser.getAvatarUrl());
            userMapper.update(user);
        }
    }
}
