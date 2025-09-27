package com.quanxiaoha.ai.robot.controller;

import jakarta.annotation.Resource;

import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.Generation;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.content.Media;
import org.springframework.ai.openai.OpenAiChatModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author 39366
 * @date 2025-09-27 10:48
 * @desc 全模态
 */

@RestController
@RequestMapping("/v9/ai")
public class MutimodalityController {
    @Resource
    private OpenAiChatModel chatModel;


    @GetMapping(value="/generateStream",produces = "text/html;charset=utf-8")
    public Flux<String> generateStream(@RequestParam(value="message") String message) {
        Media image =new Media(
            MimeTypeUtils.IMAGE_PNG,
                    new ClassPathResource("/images/multimodal-test.png")
        );
        Map<String, Object> metadata = new HashMap<>();
        metadata.put("temperature", 0.7);
        UserMessage userMessage = UserMessage.builder()
                .text(message)
                .metadata(metadata)
                .media(image)
                .build();

        //构建提示词
        Prompt prompt = new Prompt(List.of(userMessage));
        //流式调用
        return chatModel.stream(prompt)
                .mapNotNull(chatResponse -> {
                    Generation generation = chatResponse.getResult();
                    return generation.getOutput().getText();
                });
    }
}
