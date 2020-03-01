package com.mylawyer.community.controller;

import com.mylawyer.community.dto.QuestionDTO;
import com.mylawyer.community.model.Question;
import com.mylawyer.community.model.User;
import com.mylawyer.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * 表单检查
 * 提交的登录检查
 * 表单信息的自动记忆
 */
@Controller
public class PublishController {

    @Autowired
    private QuestionService questionService;

    @GetMapping("/publish/{id}")
    public String edit(@PathVariable(name = "id") Integer id,
                       Model model) {
        QuestionDTO question = questionService.getQuestionById(id);

        model.addAttribute("title", question.getTitle());//回显信息
        model.addAttribute("description", question.getDescription());
        model.addAttribute("tag", question.getTag());
        model.addAttribute("id", question.getId());
        return "publish";
    }

    @GetMapping("/publish")
    public String publish() {
        return "publish";
    }

    //post表单后返回
    @PostMapping("/publish")
    public String doPublish(//post的值用value接收
                            @RequestParam(value = "title", required = false) String title,
                            @RequestParam(value = "description", required = false) String description,
                            @RequestParam(value = "tag", required = false) String tag,
                            @RequestParam(value = "id", required = false) Integer id,//编辑问题页面需要
                            HttpServletRequest request,
                            Model model) {

        model.addAttribute("title", title);
        model.addAttribute("description", description);
        model.addAttribute("tag", tag);

        if (title == null || title == "") {
            model.addAttribute("error", "标题为空");
            return "publish";
        }
        if (description == null || description == "") {
            model.addAttribute("error", "描述为空");
            return "publish";
        }
        if (tag == null || tag == "") {
            model.addAttribute("error", "标签为空");
            return "publish";
        }

        User user = null;
        /*Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length != 0) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    String token = cookie.getValue();
                    user = userMapper.findByToken(token);
                    if (user != null) {
                        request.getSession().setAttribute("user", user);
                    }
                    break;
                }
            }
        }//检查cookie*/
        user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            model.addAttribute("error", "用户未登录");
            return "publish";
        }

        Question question = new Question();
        question.setDescription(description);
        question.setTitle(title);
        question.setTag(tag);
        question.setCreator(user.getId());
        question.setId(id);
        questionService.CreateOrUpdate(question);
        return "redirect:/";
        //redirect: 映射url->转到接口
        //return: 映射html页面
    }
}
