package com.aiassistant.service;

import com.aiassistant.model.DocContentDTO;
import com.aiassistant.render.*;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class DocGenerateService {

    private Path defaultOutputDir = Paths.get("output");

    public File generate(DocContentDTO dto, RenderStrategy strategy, Path outputDir) throws Exception {
        if (!outputDir.toFile().exists()) {
            outputDir.toFile().mkdirs();
        }

        DocRenderer renderer = selectRenderer(strategy);
        return renderer.render(dto, outputDir);
    }

    private DocRenderer selectRenderer(RenderStrategy strategy) {
        switch (strategy) {
            case WORD:
                return new WordDocRenderer(Paths.get("docs/api-doc-template.docx"));
            case MARKDOWN:
                return new MarkdownRenderer();
            case JSON_SCHEMA:
                return new JsonSchemaRenderer();
            default:
                throw new IllegalArgumentException("不支持的渲染策略：" + strategy);
        }
    }
}
