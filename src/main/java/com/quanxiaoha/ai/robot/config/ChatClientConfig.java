package com.quanxiaoha.ai.robot.config;

import com.quanxiaoha.ai.robot.advisor.MyLoggerAdvisor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.deepseek.DeepSeekChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 39366
 * @date 2025-09-11 19:56
 * @desc ChatClient方式接入大模型
 */
@Configuration
public class ChatClientConfig {
    @Bean
    public ChatClient chatClient(DeepSeekChatModel model, ChatMemory chatMemory) {
        return ChatClient.builder(model)
                .defaultSystem("请你扮演一名犬小哈 Java 项目实战专栏的客服人员")
                .defaultAdvisors(new SimpleLoggerAdvisor(),
                        new MyLoggerAdvisor(),
                        MessageChatMemoryAdvisor.builder(chatMemory).build()) //添加日志记录功能
                .build();
    }
}
