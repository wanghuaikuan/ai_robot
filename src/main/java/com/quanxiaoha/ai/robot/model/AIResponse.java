package com.quanxiaoha.ai.robot.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Bean;

/**
 * @author 39366
 * @date 2025-09-20 15:49
 * @desc AI
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AIResponse {
    private String v;
}
