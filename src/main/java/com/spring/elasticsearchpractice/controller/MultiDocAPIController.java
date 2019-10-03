package com.spring.elasticsearchpractice.controller;

import org.elasticsearch.action.get.MultiGetRequest;
import org.elasticsearch.action.get.MultiGetResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * The type Multi doc api controller.
 */
@RestController
@RequestMapping("api")
public class MultiDocAPIController {

    /**
     * The Rest high level client.
     */
    @Autowired
    RestHighLevelClient restHighLevelClient;

    /**
     * Multi get api response entity.
     *
     * @return the response entity
     */
    @GetMapping("multiget")
    public ResponseEntity multiGetAPI() {
        MultiGetRequest multiGetRequest = new MultiGetRequest();
        multiGetRequest.add(new MultiGetRequest.Item("myindex", "1"));
        multiGetRequest.add(new MultiGetRequest.Item("myindex", "2"));

        MultiGetResponse multiGetResponse = null;
        try {
            multiGetResponse = restHighLevelClient.mget(multiGetRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return ResponseEntity.ok(multiGetResponse);
    }


}
