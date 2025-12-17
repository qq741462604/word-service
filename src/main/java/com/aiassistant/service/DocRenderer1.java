package com.aiassistant.service;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * 生成md
 */
public class DocRenderer1 {

    public static String renderMarkdown(JsonNode doc) {
        StringBuilder md = new StringBuilder();

        // 标题
        md.append("# ").append(doc.path("apiTitle").asText("接口文档")).append("\n\n");

        // 概要
        md.append("## 一、接口概要\n");
        md.append(doc.path("summary").asText("")).append("\n\n");

        // 请求结构
        md.append("## 二、请求结构定义\n\n");
        md.append("| 字段名 | 描述 | 类型 | 长度 | 必填 | 备注 |\n");
        md.append("|------|------|------|------|------|------|\n");

        for (JsonNode field : doc.path("requestFields")) {
            String remark = field.path("needs_review").asBoolean(false)
                    ? "**⚠ 请人工确认**"
                    : field.path("remarks").asText("");

            md.append("| ")
                    .append(field.path("name").asText()).append(" | ")
                    .append(field.path("description").asText()).append(" | ")
                    .append(field.path("type").asText()).append(" | ")
                    .append(field.path("length").asText()).append(" | ")
                    .append(field.path("required").asBoolean() ? "是" : "否").append(" | ")
                    .append(remark)
                    .append(" |\n");
        }

        // 请求示例
        md.append("\n## 三、请求报文示例\n\n```json\n");
        md.append(doc.path("requestExample").toPrettyString());
        md.append("\n```\n");

        // 应答结构
        md.append("\n## 四、应答结构定义\n\n");
        md.append("> （待补充）\n");

        // 应答示例
        md.append("\n## 五、应答报文示例\n\n```json\n");
        md.append(doc.path("responseExample").toPrettyString());
        md.append("\n```\n");

        // 事件源
        md.append("\n## 六、事件源创建 API\n\n```json\n");
        md.append(doc.path("eventPost").toPrettyString());
        md.append("\n```\n");

        // 映射
        md.append("\n## 七、维度映射创建 API\n\n```json\n");
        md.append(doc.path("eventMappingsPost").toPrettyString());
        md.append("\n```\n");

        // SQL
        md.append("\n## 八、建表语句\n\n```sql\n");
        md.append(doc.path("sql").asText());
        md.append("\n```\n");

        // 备注
        md.append("\n## 九、其他说明\n");
        md.append(doc.path("additionalNotes").asText(""));

        return md.toString();
    }
}
