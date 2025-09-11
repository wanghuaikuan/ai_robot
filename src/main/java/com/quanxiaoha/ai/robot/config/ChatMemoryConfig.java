package com.quanxiaoha.ai.robot.config;

import jakarta.annotation.Resource;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.ChatMemoryRepository;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 39366
 * @date 2025-09-11 20:49
 * @desc 记忆功能
 */
@Configuration
public class ChatMemoryConfig {
    @Resource
    private ChatMemoryRepository chatMemoryRepository;

    @Bean
    public ChatMemory chatMemory(){
        return MessageWindowChatMemory.builder()
                .maxMessages(50)
                .chatMemoryRepository(chatMemoryRepository).build();
    }
}
