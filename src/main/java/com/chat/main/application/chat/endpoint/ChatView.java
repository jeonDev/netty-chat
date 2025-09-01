package com.chat.main.application.chat.endpoint;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ChatView {

    @GetMapping("/view/chat/room")
    public String chatRoom() {
        return "chat";
    }
}
