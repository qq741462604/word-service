package com.aiassistant.llm;

import com.aiassistant.util.JsonUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class QwenClient {

    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private final ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private OkHttpClient client;

    @Value("${llm.api.url}")
    private String apiUrl;

    @Value("${llm.api.key:}")
    private String apiKey;

    @Value("${llm.model:qwen-plus}")
    private String modelName;


    /**
     * 调用 Qwen 接口
     * @param messages 消息列表，格式示例：
     *                 [
     *                   { "role":"system", "content":"你是助手" },
     *                   { "role":"user", "content":"匹配字段" }
     *                 ]
     * @return LLM 原始 JSON 字符串
     */
    public String chat(List<Map<String, Object>> messages) throws Exception {
        // 构建请求 JSON
        String jsonBody = String.format("{\"model\":\"%s\",\"messages\":%s}", modelName, JsonUtil.toJson(messages));

        RequestBody body = RequestBody.create( MediaType.parse("application/json; charset=utf-8"), jsonBody);
        Request request = new Request.Builder()
                .url(apiUrl)
                .addHeader("Authorization", "Bearer " + apiKey)
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new RuntimeException("Qwen API 请求失败，HTTP " + response.code());
            }
            ResponseBody responseBody = response.body();
            if (responseBody == null) {
                throw new RuntimeException("Qwen API 返回空响应");
            }
            String respStr = responseBody.string();
            log.debug("QwenClient resp: {}", respStr);
            return respStr;
        }
    }

}
