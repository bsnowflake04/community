package com.mylawyer.community.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author bsnowflake04
 * Date on 2020/3/5  11:36
 */
@Controller
@RequestMapping("${server.error.path:${error.path:/error}}")
public class CustomizeErrorController implements ErrorController {//通用异常处理

    @Override
    public String getErrorPath() {
        return "error";
    }

    @RequestMapping(produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView errorHtml(HttpServletRequest request, Model model) {
        HttpStatus status = getStatus(request);

        if (status.is4xxClientError()) {
            model.addAttribute("message", "4xx客户端错误");
        }
        if (status.is5xxServerError()) {
            model.addAttribute("message", "5xx服务端错误");
        }

        return new ModelAndView("error");
    }

    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request
                .getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        try {
            return HttpStatus.valueOf(statusCode);
        } catch (Exception ex) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }
}
