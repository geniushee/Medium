package com.ll.medium.global.Rq;

import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@RequestScope
@Component
@Getter
@RequiredArgsConstructor
public class Rq {
    private final HttpServletRequest req;
    private final HttpServletResponse resp;
    private User user;

    @PostConstruct
    public void init(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication.getPrincipal() instanceof User){
            this.user = (User) authentication.getPrincipal();
        }
    }

    public String direct(String page, String msg){
        String encodedMsg = URLEncoder.encode(msg, StandardCharsets.UTF_8);
        return page + "?msg=" + encodedMsg;
    }

    public String redirect(String page, String msg){
        String encodedMsg = URLEncoder.encode(msg, StandardCharsets.UTF_8);
        return "redirect:" + page + "?msg=" + encodedMsg;
    }



    public boolean isLogined(){
        return user != null;
    }
}
