package com.mylawyer.community.provider;

import com.alibaba.fastjson.JSON;
import com.mylawyer.community.dto.AccessTokenDTO;
import com.mylawyer.community.dto.GithubUserDTO;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

//provider:提供第三方支持能力
//component:将当前类初始到spring容器上下文
@Component
public class GithubProvider {
    public String getAccessToken(AccessTokenDTO accessTokenDTO) {//将json实例post，接受response返回token
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();
        //fastjson:object->json
        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTokenDTO));
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String str = response.body().string();
            System.out.println(str);
            String token = str.split("&")[0].split("=")[1];
            System.out.println("token:" + token);
            return token;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public GithubUserDTO getUser(String accessToken) {//url请求返回
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.github.com/user?access_token=" + accessToken)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String str = response.body().string();
            System.out.println(str);
            //fastjson:json->object
            GithubUserDTO githubUserDTO = JSON.parseObject(str, GithubUserDTO.class);
            return githubUserDTO;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}