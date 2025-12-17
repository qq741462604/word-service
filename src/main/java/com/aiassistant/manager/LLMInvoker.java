package com.aiassistant.manager;

import com.aiassistant.llm.OpenAIClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class LLMInvoker {

    private final OpenAIClient openAIClient;

    public String invoke(String prompt) {
        return openAIClient.chat(prompt);
    }
}