package com.quanxiaoha.ai.robot.controller;

import com.alibaba.dashscope.aigc.videosynthesis.VideoSynthesis;
import com.alibaba.dashscope.aigc.videosynthesis.VideoSynthesisParam;
import com.alibaba.dashscope.aigc.videosynthesis.VideoSynthesisResult;
import com.alibaba.dashscope.audio.ttsv2.SpeechSynthesisParam;
import com.alibaba.dashscope.audio.ttsv2.SpeechSynthesizer;
import com.alibaba.dashscope.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: 犬小哈
 * @Date: 2025/5/29 12:32
 * @Version: v1.0.0
 * @Description: 文生音频
 **/
@RestController
@RequestMapping("/v12/ai")
@Slf4j
public class Text2VideoController {

    @Value("${spring.ai.openai.api-key}")
    private String apiKey;

    /**
     * 调用阿里百炼-语音合成大模型
     * @param prompt
     * @return
     */
    @GetMapping("/text2video")
    public String text2audio(@RequestParam(value = "prompt") String prompt) {

        // 设置视频处理参数，如指定输出视频的分辨率、视频时长等。
        Map<String, Object> parameters = new HashMap<>();
        // 是否开启 prompt 智能改写。开启后使用大模型对输入 prompt 进行智能改写。对于较短的 prompt 生成效果提升明显，但会增加耗时。
        parameters.put("prompt_extend", true);
        String imgUrl = "file:///" + "D:/Java_workspace/xiaoha-ai-robot/xiaoha-ai-robot-springboot/src/main/resources/images/multimodal-test.png"; // Windows 系统

        VideoSynthesisParam param =
                VideoSynthesisParam.builder()
                        .apiKey(apiKey)
                        .model("wanx2.1-i2v-plus") // 模型名称
                        .prompt(prompt) // 提示词
                        .imgUrl(imgUrl) // 静态图片路径
                        .parameters(parameters) // 视频处理参数
                        .build();
        log.info("## 正在生成中, 请稍等...");
        VideoSynthesis vs = new VideoSynthesis();
        VideoSynthesisResult result = null;
        try {
            result = vs.call(param);
        } catch (Exception e) {
            log.error("", e);
        }

        // 返参
        return JsonUtils.toJson(result);
    }

}
