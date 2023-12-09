package com.ll.medium.domain.home.home.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class homeController {
    @GetMapping("/")
    public String showMain(){
        return "domain/home/home/main";
    }

    @GetMapping("/session")
    @ResponseBody
    public Map<String, Object> showSesseion(HttpSession httpSession){
        return Collections.list(httpSession.getAttributeNames()).stream()
                .collect(
                        Collectors.toMap(
                                key -> key,
                                key -> httpSession.getAttribute(key)
                        )
                );
    }
}
