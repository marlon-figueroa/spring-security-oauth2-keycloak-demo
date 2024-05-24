package com.programming.techie.authcode.controller;

import jakarta.servlet.http.HttpServletRequest;
import net.minidev.json.JSONObject;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.OidcUserAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Controller
public class HomeController {

    @GetMapping("/home")
    public String home(HttpServletRequest request, ModelMap model) {
        OAuth2User user = ((OAuth2User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        model.addAttribute("usuario", user.getAttribute("name"));

        System.out.println(user.getAttributes());
        Map<String, String> data1 = new HashMap<>();

        JSONObject jsonObject1 = new JSONObject(user.getAttributes());
        String orgJsonData1 = jsonObject1.toString();
        model.addAttribute("atributos", orgJsonData1);
        String arraySplit = Arrays.toString(user.getAuthorities().toArray());
        if (arraySplit.contains("["))
            arraySplit = arraySplit.replace("[", "");
        if (arraySplit.contains("]"))
            arraySplit = arraySplit.replace("]", "");
        String[] arrayRoles = arraySplit.split(",");
        int i = 1;
        for (String it : arrayRoles) {
            data1.put("ROL_" + i, it);
            i++;
        }
        JSONObject jsonObject2 = new JSONObject(data1);
        String orgJsonData2 = jsonObject2.toString();
        System.out.println(orgJsonData2);
        model.addAttribute("autorizaciones", orgJsonData2);

        return "home";
    }

    @GetMapping(path = "/unauthenticated")
    public HashMap unauthenticatedRequests() {
        return new HashMap() {{
            put("this is ", "unauthenticated endpoint");
        }};
    }

}
