package com.zwx.langchain.controller;

import dev.langchain4j.community.model.dashscope.QwenChatModel;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class ChatController {

    @Resource
    private QwenChatModel qwenChatModel;

    @GetMapping("/chat")
    public String model(@RequestParam(value = "message", defaultValue = "Hello") String message) {
        return qwenChatModel.chat(message);
    }
}
