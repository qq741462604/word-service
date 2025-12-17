package com.aiassistant.llm;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class OpenAIClient {


    @Autowired
    private OkHttpClient client;

    @Value("${llm.api.url}")
    private String apiUrl;

    @Value("${llm.api.key:}")
    private String apiKey;

    @Value("${llm.model:qwen-plus}")
    private String modelName;

    private final ObjectMapper mapper = new ObjectMapper();

    public String chat(String prompt) {
        try {
//            Map<String, Object> req = Map.of(
//                    "model", "gpt-4o-mini",
//                    "messages", new Object[]{
//                            Map.of("role", "system", "content", "你是专业的文档生成助手"),
//                            Map.of("role", "user", "content", prompt)
//                    }
//            );

            // ---------- 构造 messages ----------
            List<Map<String, Object>> messages = new ArrayList<>();

            Map<String, Object> systemMessage = new HashMap<>();
            systemMessage.put("role", "system");
            systemMessage.put("content", "你是专业的文档生成助手");
            messages.add(systemMessage);

            Map<String, Object> userMessage = new HashMap<>();
            userMessage.put("role", "user");
            userMessage.put("content", prompt);
            messages.add(userMessage);



            Map<String, Object> req =  new HashMap<>();
            req.put("model", modelName);
            req.put("messages", messages);

            RequestBody body = RequestBody.create( MediaType.parse("application/json; charset=utf-8"),
                    mapper.writeValueAsBytes(req));

            Request request = new Request.Builder()
                    .url(apiUrl)
                    .addHeader("Authorization", "Bearer " + apiKey)
                    .post(body)
                    .build();

            Response response = client.newCall(request).execute();
            String result = response.body().string();
            log.info("LLM Response: {}", result);
            return result;

//            Map<String, Object> respMap = mapper.readValue(result, Map.class);
//            Map<String, Object> choice = ((java.util.List<Map<String, Object>>) respMap.get("choices")).get(0);
//            Map<String, Object> message = (Map<String, Object>) choice.get("message");
//            return message.get("content").toString();

        } catch (Exception e) {
            throw new RuntimeException("调用 LLM 失败", e);
        }
    }
}