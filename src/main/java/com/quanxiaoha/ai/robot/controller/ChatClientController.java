package com.quanxiaoha.ai.robot.controller;

import jakarta.annotation.Resource;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
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
@RequestMapping("/v2/ai")
public class ChatClientController {
    @Resource
    private ChatClient chatClient;

    @GetMapping("/generate")
    public String generate(@RequestParam (required = true,value = "message",defaultValue = "你是谁") String message) {
           return chatClient.prompt()
                   .user(message)
                   .call()
                   .content();
    }

    @GetMapping(value = "/generateStream", produces = "text/html;charset=utf-8")
    public Flux<String> generateStream(@RequestParam(value = "message", defaultValue = "你是谁？") String message,@RequestParam(value = "chatId") String chatId) {
        return chatClient.prompt()
                .user(message) // 提示词
                .advisors(a -> a.param(ChatMemory.CONVERSATION_ID, chatId))
                .stream() // 流式输出
                .content();

    }


}
