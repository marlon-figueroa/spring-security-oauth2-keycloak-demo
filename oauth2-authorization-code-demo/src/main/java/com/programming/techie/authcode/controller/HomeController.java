package com.programming.techie.authcode.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;

import java.net.http.HttpHeaders;
import java.util.Arrays;
import java.util.HashMap;

@Controller
public class HomeController {

    @GetMapping("/home")
    public String home(HttpServletRequest request, ModelMap model) {
        for (String it: request.getParameterMap().keySet()) {
            System.out.println(it);
            System.out.println(Arrays.stream(request.getParameterMap().get(it)).toList());
            System.out.println("---------------------------");
        }

        OAuth2User user = ((OAuth2User)SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        model.addAttribute("usuario", user.getAttribute("name"));

        System.out.println("JSESSIONID" + Arrays.stream(request.getCookies())
                    .filter(c -> "JSESSIONID".equals(c.getName()))
                    .map(Cookie::getValue)
                    .findAny().get());

        return "home";
    }

    @GetMapping(path = "/unauthenticated")
    public HashMap unauthenticatedRequests() {
        return new HashMap(){{
            put("this is ", "unauthenticated endpoint");
        }};
    }

}
