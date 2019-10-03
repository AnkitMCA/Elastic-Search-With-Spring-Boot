package com.spring.elasticsearchpractice.controller;

import com.spring.elasticsearchpractice.model.User;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api")
public class APIController {

    @Autowired
    RestHighLevelClient restHighLevelClient;

    @PostMapping("index")
    public ResponseEntity index(@RequestBody User user) {
        Map<String, Object> json = new HashMap<>();
        json.put("name", user.getName());
        json.put("email", user.getEmail());
        json.put("mobile", user.getMobile());
        json.put("others", user.getOthers());
        IndexRequest indexRequest = new IndexRequest("myindex")
                .id(user.getId())
                .source(json);
        IndexResponse indexResponse = null;
        try {
            indexResponse = restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            System.out.println("There is an Exception in index method.");
            e.printStackTrace();
        }

        return ResponseEntity.ok(indexResponse);
    }

    @GetMapping("get/{id}")
    public ResponseEntity getAPI(@PathVariable("id") String value) {
        GetRequest getRequest = new GetRequest("myindex")
                .id(value);
        GetResponse getResponse = null;
        try {
            getResponse = restHighLevelClient.get(getRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            System.out.println("There is an Exception in get method.");
            e.printStackTrace();
        }

        return ResponseEntity.ok(getResponse);
    }

    @PostMapping("update/{id}")
    public ResponseEntity updateAPI(@RequestBody User user, @PathVariable("id") String value) {
        Map<String, Object> json = new HashMap<>();
        json.put("email", user.getEmail());

        IndexRequest indexRequest = new IndexRequest("myindex")
                .id("1")
                .source(json);

        UpdateRequest updateRequest = new UpdateRequest("myindex", value)
                .doc(indexRequest);
        UpdateResponse updateResponse = null;
        try {
            updateResponse = restHighLevelClient.update(updateRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            System.out.println("There is an Exception in update method.");
            e.printStackTrace();
        }

        return ResponseEntity.ok(updateResponse);
    }

    @GetMapping("delete/{id}")
    public ResponseEntity deleteAPI(@PathVariable("id") String value) {
        DeleteRequest deleteRequest = new DeleteRequest("myindex", value);
        DeleteResponse deleteResponse = null;
        try {
            deleteResponse = restHighLevelClient.delete(deleteRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            System.out.println("There is an Exception in delete method.");
            e.printStackTrace();
        }

        return ResponseEntity.ok(deleteResponse);
    }
}
