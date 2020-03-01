package com.mylawyer.community.service;

import com.mylawyer.community.mapper.UserMapper;
import com.mylawyer.community.model.User;
import com.mylawyer.community.model.UserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public void CreateOrUpdate(User newUser) {
        UserExample userexample = new UserExample();
        userexample.createCriteria().andAccountIdEqualTo(newUser.getAccountId());
        List<User> users = userMapper.selectByExample(userexample);
        if(users.size() == 0){
            newUser.setGmtCreate(System.currentTimeMillis());
            newUser.setGmtModified(newUser.getGmtCreate());
            userMapper.insert(newUser);
        }else{//update
            User user = users.get(0);//user里有accountId,再新建一个user传入updateexampleselective
            User user1 = new User();
            user1.setGmtModified(System.currentTimeMillis());
            user1.setName(newUser.getName());
            user1.setToken(newUser.getToken());
            user1.setAvatarUrl(newUser.getAvatarUrl());
            UserExample userExample = new UserExample();
            userexample.createCriteria().andIdEqualTo(user.getId());
            userMapper.updateByExampleSelective(user1,userExample);
        }
    }
}
