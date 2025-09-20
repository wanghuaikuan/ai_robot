package com.quanxiaoha.ai.robot.model;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;

/**
 * @author 39366
 * @date 2025-09-20 17:54
 * @desc
 */
@JsonPropertyOrder({"actor", "movies"})
public record ActorFilmgraphy(String actor, List<String> movies) {

}
