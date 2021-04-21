package com.udacity.jwdnd.spring_security_basics.controller;

import com.udacity.jwdnd.spring_security_basics.model.ChatMessage;
import com.udacity.jwdnd.spring_security_basics.model.MessageForm;
import com.udacity.jwdnd.spring_security_basics.service.MessageListService;
import com.udacity.jwdnd.spring_security_basics.service.MessageService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/chat")
public class ChatController {

    private MessageService messageListService;

    public ChatController(MessageService messageListService) {
        this.messageListService = messageListService;
    }

    @GetMapping()
    public String getHomePage(MessageForm messageForm, Model model) {
        model.addAttribute("greetings", this.messageListService.getChatMessages());
        return "chat";
    }

    @PostMapping()
    public String postChatMessage(Authentication authentication, ChatMessage chatForm, Model model) {
        chatForm.setUsername(authentication.getName());
        this.messageListService.addMessage(chatForm);
        chatForm.setMessageText("");
        model.addAttribute("chatMessages", this.messageListService.getChatMessages());
        return "chat";
    }

    @ModelAttribute("allMessageTypes")
    public String[] allMessageTypes () {
        return new String[] { "Say", "Shout", "Whisper" };
    }

}

