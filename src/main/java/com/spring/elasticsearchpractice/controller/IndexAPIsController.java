package com.spring.elasticsearchpractice.controller;

import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.admin.indices.open.OpenIndexRequest;
import org.elasticsearch.action.admin.indices.open.OpenIndexResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.*;
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

    /*
    Analyze Request is used to analyze the word or the sentence using default as well as custom analyzer.
    */

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

    /**
     * Create index api response entity.
     *
     * @return the response entity
     */
    @GetMapping("createindex")
    public ResponseEntity createIndexAPI() {
        CreateIndexRequest createIndexRequest = new CreateIndexRequest("demoindex");
        /*createIndexRequest.settings(Settings.builder()
                .put("index.number_of_shards", 3)
                .put("index.number_of_replicas", 2)
        );*/
        CreateIndexResponse createIndexResponse = null;
        try {
            createIndexResponse = restHighLevelClient.indices().create(createIndexRequest, RequestOptions.DEFAULT);
            return ResponseEntity.ok(createIndexResponse);
        } catch (IOException e) {
            System.out.println("There is an Exception in createIndex method.");
            e.printStackTrace();
        }

        return (ResponseEntity) ResponseEntity.badRequest();
    }

    /**
     * Delete index api response entity.
     *
     * @return the response entity
     */
    @GetMapping("deleteindex")
    public ResponseEntity deleteIndexAPI() {
        DeleteIndexRequest deleteIndexRequest = new DeleteIndexRequest("demoindex");
        AcknowledgedResponse deleteIndexResponse = null;
        try {
            deleteIndexResponse = restHighLevelClient.indices().delete(deleteIndexRequest, RequestOptions.DEFAULT);
            return ResponseEntity.ok(deleteIndexResponse);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return (ResponseEntity) ResponseEntity.badRequest();
    }

    /**
     * Index exists api response entity.
     *
     * @return the response entity
     */
    @GetMapping("indexexists")
    public ResponseEntity indexExistsAPI() {
        GetIndexRequest getIndexRequest = new GetIndexRequest("demoindex");
        try {
            boolean response = restHighLevelClient.indices().exists(getIndexRequest, RequestOptions.DEFAULT);
            return ResponseEntity.ok(response);
        } catch (IOException e) {
            System.out.println("There is an Exception in indexExists method.");
            e.printStackTrace();
        }

        return (ResponseEntity) ResponseEntity.badRequest();
    }

    /**
     * Open index response entity.
     *
     * @return the response entity
     */
    @GetMapping("openindex")
    public ResponseEntity openIndexAPI() {
        OpenIndexRequest openIndexRequest = new OpenIndexRequest("myindex2");
        OpenIndexResponse openIndexResponse = null;
        try {
            openIndexResponse = restHighLevelClient.indices().open(openIndexRequest, RequestOptions.DEFAULT);
            return ResponseEntity.ok(openIndexResponse);
        } catch (IOException e) {
            System.out.println("There is an Exception in openIndex method.");
            e.printStackTrace();
        }

        return (ResponseEntity) ResponseEntity.badRequest();
    }

    /**
     * Close index api response entity.
     *
     * @return the response entity
     */
    @GetMapping("closeindex")
    public ResponseEntity closeIndexAPI() {
        CloseIndexRequest closeIndexRequest = new CloseIndexRequest("myindex2");
        CloseIndexResponse closeIndexResponse = null;
        try {
            closeIndexResponse = restHighLevelClient.indices().close(closeIndexRequest, RequestOptions.DEFAULT);
            return ResponseEntity.ok(closeIndexResponse);
        } catch (IOException e) {
            System.out.println("There is an Exception in closeIndex method.");
            e.printStackTrace();
        }

        return (ResponseEntity) ResponseEntity.badRequest();
    }

}
