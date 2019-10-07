package com.spring.elasticsearchpractice.controller;

import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.AnalyzeRequest;
import org.elasticsearch.client.indices.AnalyzeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * The type Index ap is controller.
 */
@RestController
@RequestMapping("api")
public class IndexAPIsController {

    /**
     * The Rest high level client.
     */
    @Autowired
    RestHighLevelClient restHighLevelClient;

    /**
     * Analyze api response entity.
     *
     * @return the response entity
     */
    @GetMapping("analyze")
    public ResponseEntity analyzeAPI() {
//        AnalyzeRequest analyzeRequest = AnalyzeRequest.withGlobalAnalyzer("english", "Sumit", "Ankit");
        AnalyzeRequest analyzeRequest = AnalyzeRequest.withField("myindex", "name", "Ankit");

        AnalyzeResponse analyzeResponse = null;
        try {
            analyzeResponse = restHighLevelClient.indices().analyze(analyzeRequest, RequestOptions.DEFAULT);
            return ResponseEntity.ok(analyzeResponse);
        } catch (IOException e) {
            System.out.println("There is an Exception in analyze method.");
            e.printStackTrace();
        }

        return (ResponseEntity) ResponseEntity.badRequest();
    }

}
