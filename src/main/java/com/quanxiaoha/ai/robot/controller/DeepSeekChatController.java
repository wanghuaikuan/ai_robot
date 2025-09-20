package com.quanxiaoha.ai.robot.controller;

import jakarta.annotation.Resource;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.deepseek.DeepSeekChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

/**
 * @author 39366
 * @date 2025-09-09 14:22
 * @desc DeepSeekChat
 */
@RestController
@RequestMapping("/ai")
public class DeepSeekChatController {
    @Resource
    private DeepSeekChatModel chatModel;


    @GetMapping("/generate")
    public String generate(@RequestParam (value = "message",defaultValue = "你是谁") String message) {
           return chatModel.call(message);
    }


    @GetMapping(value = "/generateStream", produces = "text/html;charset=utf-8")
    public Flux<String> generateStream(@RequestParam (value = "message",defaultValue = "你是谁") String message) {
        Prompt prompt = new Prompt(new UserMessage(message));
        return chatModel.stream(prompt)
                .mapNotNull(chatResponse ->chatResponse.getResult().getOutput().getText());
    }
}
