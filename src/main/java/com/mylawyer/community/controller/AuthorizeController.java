package com.mylawyer.community.controller;

import com.mylawyer.community.dto.AccessTokenDTO;
import com.mylawyer.community.dto.GithubUserDTO;
import com.mylawyer.community.mapper.UserMapper;
import com.mylawyer.community.model.User;
import com.mylawyer.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * github授权登录：第一次交互返回code，第二次postcode返回token，第三次携带token返回用户信息
 */
@Controller
public class AuthorizeController {
    //autowired:注入上下文对象
    @Autowired
    private GithubProvider githubProvider;

    //value:从properities取值
    @Value("${github.client.id}")
    private String clientId;
    @Value("${github.client.secret}")
    private String clientSecret;
    @Value("${github.redirect_url}")
    private String redirectUrl;

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state,
            /*HttpServletRequest request,*/
                           HttpServletResponse response) {
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_url(redirectUrl);
        accessTokenDTO.setState(state);

        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        if (accessToken == null) return "redirect:/";//登录失败

        GithubUserDTO githubUserDTO = githubProvider.getUser(accessToken);
        if (githubUserDTO != null && githubUserDTO.getId() != null) {
            //
            User user = new User();
            String token = UUID.randomUUID().toString();//设备唯一标识
            user.setToken(token);
            user.setName(githubUserDTO.getName());
            user.setAccountId(String.valueOf(githubUserDTO.getId()));
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            user.setAvatarUrl(githubUserDTO.getAvatarUrl());

            userMapper.insert(user);//插入user表
            response.addCookie(new Cookie("token", token));//登录同时生成cookie

            //设置session和cookie
            //request.getSession().setAttribute("user",githubUser);
            return "redirect:/";
        } else {
            return "redirect:/";//登录失败
        }

    }
}
