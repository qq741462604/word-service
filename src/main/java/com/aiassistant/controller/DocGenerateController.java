package com.aiassistant.controller;

import com.aiassistant.model.DocContentDTO;
import com.aiassistant.model.Iteration1ResultDTO;
import com.aiassistant.render.RenderStrategy;
import com.aiassistant.service.DocContentAssembler;
import com.aiassistant.service.DocGenerateService;
import com.aiassistant.service.DocRenderer1;
import com.aiassistant.service.MarkdownWriter;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Paths;

@RestController
@RequestMapping("/doc")
public class DocGenerateController {

    private final DocGenerateService service = new DocGenerateService();

    @Autowired
    private DocContentAssembler assembler;

    @PostMapping("/generate")
    public Object generate(
            @RequestBody Iteration1ResultDTO input,
            @RequestParam(defaultValue = "WORD") RenderStrategy strategy
    ) throws Exception {

//        DocContentDTO docContent =  DocContentAssembler.assemble(input);
        DocContentDTO docContent =  assembler.generate(input);

        return service.generate(docContent, strategy, Paths.get("output"));
    }

//    @PostMapping("/generate1")
//    public Object generate1(
//            @RequestBody Iteration1ResultDTO input,
//            @RequestParam(defaultValue = "WORD") RenderStrategy strategy
//    ) throws Exception {
//        ObjectMapper mapper = new ObjectMapper();
////        DocContentDTO docContent =  DocContentAssembler.assemble(input);
//        DocContentDTO docContent =  assembler.generate(input);
//        JsonNode root = mapper.readTree(JSONObject.toJSONString(docContent));
//        String r = DocRenderer1.renderMarkdown(root);
//        MarkdownWriter.write("D:\\ideaProject\\AI\\word-service\\output","test",r);
////        return service.generate(docContent, strategy, Paths.get("output"));
//        return "";
//    }
}
