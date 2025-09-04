package com.chat.main.application.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

    @GetMapping(value = {
            "/view",
            "/login",
            "/signup",
            "/chat"
    })
    public String index() {
        return "index";
    }
}