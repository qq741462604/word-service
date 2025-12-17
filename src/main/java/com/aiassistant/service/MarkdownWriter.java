package com.aiassistant.service;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MarkdownWriter {

    public static File write(String baseDir, String apiTitle, String content) throws Exception {
        String safeName = apiTitle.replaceAll("[^a-zA-Z0-9一-龥]", "_");

        String time = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));

        File dir = new File(baseDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        File mdFile = new File(dir, safeName + "_" + time + ".md");

        try (FileOutputStream fos = new FileOutputStream(mdFile)) {
            fos.write(content.getBytes(StandardCharsets.UTF_8));
        }

        return mdFile;
    }
}

