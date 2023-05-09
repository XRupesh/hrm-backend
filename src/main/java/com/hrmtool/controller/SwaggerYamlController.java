//package com.hrmtool.controller;
//
//import io.github.swagger2markup.Swagger2MarkupConverter;
//import io.swagger.models.Swagger;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RestController;
//import springfox.documentation.annotations.ApiIgnore;
//import springfox.documentation.service.Documentation;
//import springfox.documentation.spring.web.DocumentationCache;
//import springfox.documentation.swagger2.mappers.ServiceModelToSwagger2Mapper;
//
//import java.nio.charset.StandardCharsets;
//
//@RestController
//@ApiIgnore
//public class SwaggerYamlController {
//    private final DocumentationCache documentationCache;
//    private final ServiceModelToSwagger2Mapper mapper;
//
//    public SwaggerYamlController(DocumentationCache documentationCache, ServiceModelToSwagger2Mapper mapper) {
//        this.documentationCache = documentationCache;
//        this.mapper = mapper;
//    }
//
//    @GetMapping(value = "/api-docs.yaml", produces = "application/yaml")
//    public ResponseEntity<String> apiDocsYaml() {
//        Documentation documentation = documentationCache.documentationByGroup("default");
//        Swagger swagger = mapper.mapDocumentation(documentation);
//        String yaml = Swagger2MarkupConverter.from(swagger).build().toString();
//        return ResponseEntity.ok(yaml);
//    }
//}
