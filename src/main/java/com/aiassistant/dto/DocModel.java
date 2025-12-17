package com.aiassistant.dto;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.List;

public class DocModel {

    public String apiTitle;
    public String summary;
    public List<FieldModel> requestFields;
    public JsonNode requestExample;
    public JsonNode responseExample;
    public JsonNode eventPost;
    public JsonNode eventMappingsPost;
    public String sql;
    public String additionalNotes;

    public static class FieldModel {
        public String name;
        public String description;
        public String type;
        public String length;
        public boolean required;
        public boolean needsReview;
        public String remarks;
    }
}
