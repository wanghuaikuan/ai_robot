package com.quanxiaoha.ai.robot.controller;

import com.quanxiaoha.ai.robot.model.ActorFilmgraphy;
import jakarta.annotation.Resource;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 39366
 * @date 2025-09-20 17:55
 * @desc 结构化
 */
@RestController
@RequestMapping("/v8/ai")
public class StructuredOutputController {
    @Resource
    private ChatClient chatClient;
    @GetMapping("/actor/films")
    public ActorFilmgraphy generate(@RequestParam(value = "name") String name){
        return chatClient.prompt()
                .user(u->u.text(
                        """
                       请为演员 {actor} 生成包含5部代表作的电影作品集,
                       只包含 {actor} 担任主演的电影，不要包含任何解释说明。
                       """
                ).param("actor",name))
                .call()
                .entity(ActorFilmgraphy.class);
    }
}
