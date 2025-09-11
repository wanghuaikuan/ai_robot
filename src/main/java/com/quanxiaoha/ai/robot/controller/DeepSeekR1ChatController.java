package com.quanxiaoha.ai.robot.controller;

import io.micrometer.common.util.StringUtils;
import jakarta.annotation.Resource;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.deepseek.DeepSeekAssistantMessage;
import org.springframework.ai.deepseek.DeepSeekChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author 39366
 * @date 2025-09-09 14:22
 * @desc DeepSeekChat
 */
@RestController
@RequestMapping("/v1/ai")
public class DeepSeekR1ChatController {
    @Resource
    private DeepSeekChatModel model;
    @Autowired
    private ChatModel chatModel;

    @GetMapping("/generate")
    public String generate(@RequestParam (required = true,value = "message",defaultValue = "你是谁") String message) {
           return model.call(message);
    }


    @GetMapping(value = "/generateStream", produces = "text/html;charset=utf-8")
    public Flux<String> generateStream(@RequestParam (value = "message",defaultValue = "你是谁") String message) {
        Prompt prompt = new Prompt(new UserMessage(message));
        AtomicBoolean needSeparator =new AtomicBoolean(true);
        return chatModel.stream(prompt)
                .mapNotNull(chatResponse -> {
                    DeepSeekAssistantMessage deepSeekAssistantMessage =(DeepSeekAssistantMessage) chatResponse.getResult().getOutput();
                    String reasoningContent = deepSeekAssistantMessage.getReasoningContent();
                    String text = deepSeekAssistantMessage.getText();
                    boolean isTextResponse =false;
                    String rawContent;
                    if(Objects.isNull(text)){
                        rawContent = reasoningContent;
                    }else {
                        rawContent = text;
                        isTextResponse = true;
                    }
                    String processed = StringUtils.isNotBlank(reasoningContent)? reasoningContent : text;

                    if(isTextResponse&&needSeparator.compareAndSet(true,false)){
                        processed = "<hr>"+processed;
                    }
                    return processed;



                });
    }
}
