package com.spring.elasticsearchpractice.controller;

import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.core.MainResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * The type Cluster info api controller.
 */
@RestController
@RequestMapping("api")
public class ClusterInfoAPIController {

    /**
     * The Rest high level client.
     */
    @Autowired
    RestHighLevelClient restHighLevelClient;

    /**
     * Cluster info api response entity.
     *
     * @return the response entity
     */
    @GetMapping("info")
    public ResponseEntity clusterInfoAPI() {

        MainResponse mainResponse = null;
        try {
            mainResponse = restHighLevelClient.info(RequestOptions.DEFAULT);
            return ResponseEntity.ok(mainResponse);
        } catch (IOException e) {
            System.out.println("There is an Exception in clusterInfo method.");
            e.printStackTrace();
        }
        return (ResponseEntity) ResponseEntity.badRequest();
    }

    /**
     * Ping info api response entity.
     *
     * @return the response entity
     */
    @GetMapping("ping")
    public ResponseEntity pingInfoAPI() {

        boolean pingResponse = false;
        try {
            pingResponse = restHighLevelClient.ping(RequestOptions.DEFAULT);
            return ResponseEntity.ok(pingResponse);
        } catch (IOException e) {
            System.out.println("There is an Exception in pingInfo method.");
            e.printStackTrace();
        }
        return (ResponseEntity) ResponseEntity.badRequest();
    }

}
