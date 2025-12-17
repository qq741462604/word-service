package com.aiassistant.transformer;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DocContentParser {

    private final ObjectMapper mapper = new ObjectMapper();

    /**
     * 从 Qwen chat.completion 中抽出 assistant.content
     */
    public String extractContent(String llmResponse) throws Exception {
        JsonNode root = mapper.readTree(llmResponse);
        return root
                .path("choices")
                .get(0)
                .path("message")
                .path("content")
                .asText();
    }
}
