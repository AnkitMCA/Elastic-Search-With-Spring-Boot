package com.spring.elasticsearchpractice.controller;

import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.admin.indices.open.OpenIndexRequest;
import org.elasticsearch.action.admin.indices.open.OpenIndexResponse;
import org.elasticsearch.action.admin.indices.shrink.ResizeRequest;
import org.elasticsearch.action.admin.indices.shrink.ResizeResponse;
import org.elasticsearch.action.admin.indices.shrink.ResizeType;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.*;
import org.elasticsearch.common.settings.Settings;
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
        createIndexRequest.settings(Settings.builder()
                .put("index.blocks.write", true)
                .put("index.number_of_shards", 5)
                .put("index.number_of_routing_shards", 15)
        );
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

    /**
     * Shrink index api response entity.
     *
     * @return the response entity
     */
    @GetMapping("shrinkindex")
    public ResponseEntity shrinkIndexAPI() {
        ResizeRequest shrinkResizeRequest = new ResizeRequest("shrinkindex", "demoindex");
        shrinkResizeRequest.setResizeType(ResizeType.SHRINK);
        shrinkResizeRequest.getTargetIndexRequest().settings(Settings.builder()
                .put("index.number_of_shards", 1));
        ResizeResponse resizeResponse = null;
        try {
            resizeResponse = restHighLevelClient.indices().shrink(shrinkResizeRequest, RequestOptions.DEFAULT);
            return ResponseEntity.ok(resizeResponse);
        } catch (IOException e) {
            System.out.println("There is an Exception in shrinkIndex method.");
            e.printStackTrace();
        }

        return (ResponseEntity) ResponseEntity.badRequest();
    }

    /**
     * Split index api response entity.
     *
     * @return the response entity
     */
    @GetMapping("splitindex")
    public ResponseEntity splitIndexAPI() {
        ResizeRequest splitResizeRequest = new ResizeRequest("splitindex", "demoindex");
        splitResizeRequest.setResizeType(ResizeType.SPLIT);
        splitResizeRequest.getTargetIndexRequest().settings(Settings.builder()
                .put("index.number_of_shards", 15));
        ResizeResponse resizeResponse = null;
        try {
            resizeResponse = restHighLevelClient.indices().split(splitResizeRequest, RequestOptions.DEFAULT);
            return ResponseEntity.ok(resizeResponse);
        } catch (IOException e) {
            System.out.println("There is an Exception in splitIndex method.");
            e.printStackTrace();
        }

        return (ResponseEntity) ResponseEntity.badRequest();
    }

}
