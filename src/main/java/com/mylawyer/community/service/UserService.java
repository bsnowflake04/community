package com.mylawyer.community.service;

import com.mylawyer.community.dto.PaginationDTO;
import com.mylawyer.community.enums.UserStatusEnum;
import com.mylawyer.community.mapper.UserMapper;
import com.mylawyer.community.model.User;
import com.mylawyer.community.model.UserExample;
import org.apache.ibatis.session.RowBounds;
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
            newUser.setStatus(UserStatusEnum.USER.getStatus());
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

    public PaginationDTO list(Integer page, Integer size) {
        Integer totalCount = (int) userMapper.countByExample(new UserExample());
        Integer totalPage;
        if (totalCount % size == 0) {
            totalPage = totalCount / size;
        } else {
            totalPage = totalCount / size + 1;
        }

        if (page < 1) {
            page = 1;
        }
        if (page > totalPage) {
            page = totalPage;
        }
        PaginationDTO paginationDTO = new PaginationDTO();
        paginationDTO.setPaginationDTO(totalPage, page);
        Integer offset = size * (page - 1);
        UserExample example = new UserExample();
        example.setOrderByClause("gmt_create desc");
        List<User> users = userMapper.selectByExampleWithRowbounds(example, new RowBounds(offset, size));
        paginationDTO.setData(users);
        return paginationDTO;
    }

    public void alterStatus(Long id) {
        User user = userMapper.selectByPrimaryKey(id);
        if (user.getStatus().equals(UserStatusEnum.USER.getStatus())) {
            user.setStatus(UserStatusEnum.ADMIN.getStatus());
        }else{
            user.setStatus(UserStatusEnum.USER.getStatus());
        }
        userMapper.updateByPrimaryKey(user);
    }

    public void deleteUser(Long id) {
        userMapper.deleteByPrimaryKey(id);
    }
}
