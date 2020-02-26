package com.mylawyer.community.controller;

import com.mylawyer.community.dto.PaginationDTO;
import com.mylawyer.community.mapper.UserMapper;
import com.mylawyer.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * 登录持久化：验证cookie的token
 */
//controller：接受前端的请求
@Controller
public class IndexController {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionService questionService;
    //getmapping:接受映射的地址
    @GetMapping("/")
    public String index(HttpServletRequest request,
                        Model model,//Model:传值入前端
                        @RequestParam(name = "page", defaultValue = "1") Integer page,
                        @RequestParam(name = "size", defaultValue = "9") Integer size
            /*@RequestParam(name = "name")*//*requestparam:提取请求中的参数*//* String name,
                        Model model*/) {
        /*model.addAttribute("name", name);*/
        /*Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length != 0) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    String token = cookie.getValue();
                    User user = userMapper.findByToken(token);
                    if (user != null) {
                        request.getSession().setAttribute("user", user);
                    }
                    break;
                }
            }
        }*/

        PaginationDTO paginationDTO = questionService.listAll(page, size);
        model.addAttribute("paginationDTO", paginationDTO);
        return "index";
    }
}
