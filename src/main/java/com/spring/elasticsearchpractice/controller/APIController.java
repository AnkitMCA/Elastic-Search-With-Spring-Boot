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
import org.elasticsearch.client.core.TermVectorsRequest;
import org.elasticsearch.client.core.TermVectorsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * The type Api controller.
 */
@RestController
@RequestMapping("api")
public class APIController {

    /**
     * The Rest high level client.
     */
    @Autowired
    RestHighLevelClient restHighLevelClient;

    /**
     * Index response entity.
     *
     * @param user the user
     * @return the response entity
     */
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
            return ResponseEntity.ok(indexResponse);
        } catch (IOException e) {
            System.out.println("There is an Exception in index method.");
            e.printStackTrace();
        }

        return (ResponseEntity) ResponseEntity.badRequest();
    }

    /**
     * Exists api response entity.
     *
     * @param value the value
     * @return the response entity
     */
    @GetMapping("exists/{id}")
    public ResponseEntity existsAPI(@PathVariable("id") String value) {
        GetRequest getRequest = new GetRequest("myindex")
                .id(value);
        Boolean exists = false;
        try {
            exists = restHighLevelClient.exists(getRequest, RequestOptions.DEFAULT);
            return ResponseEntity.ok(exists);
        } catch (IOException e) {
            System.out.println("There is an Exception in exists method.");
            e.printStackTrace();
        }

        return (ResponseEntity) ResponseEntity.badRequest();
    }

    /**
     * Gets api.
     *
     * @param value the value
     * @return the api
     */
    @GetMapping("get/{id}")
    public ResponseEntity getAPI(@PathVariable("id") String value) {
        GetRequest getRequest = new GetRequest("myindex")
                .id(value);
        GetResponse getResponse = null;
        try {
            getResponse = restHighLevelClient.get(getRequest, RequestOptions.DEFAULT);
            return ResponseEntity.ok(getResponse);
        } catch (IOException e) {
            System.out.println("There is an Exception in get method.");
            e.printStackTrace();
        }

        return (ResponseEntity) ResponseEntity.badRequest();
    }

    /**
     * Update api response entity.
     *
     * @param user  the user
     * @param value the value
     * @return the response entity
     */
    @PostMapping("update/{id}")
    public ResponseEntity updateAPI(@RequestBody User user, @PathVariable("id") String value) {
        Map<String, Object> json = new HashMap<>();
        json.put("name", user.getName());
        json.put("email", user.getEmail());
        json.put("mobile", user.getMobile());
        json.put("others", user.getOthers());

        IndexRequest indexRequest = new IndexRequest("myindex")
                .id("1")
                .source(json);

        UpdateRequest updateRequest = new UpdateRequest("myindex", value)
                .doc(indexRequest);
        UpdateResponse updateResponse = null;
        try {
            updateResponse = restHighLevelClient.update(updateRequest, RequestOptions.DEFAULT);
            return ResponseEntity.ok(updateResponse);
        } catch (IOException e) {
            System.out.println("There is an Exception in update method.");
            e.printStackTrace();
        }

        return (ResponseEntity) ResponseEntity.badRequest();
    }

    /**
     * Delete api response entity.
     *
     * @param value the value
     * @return the response entity
     */
    @GetMapping("delete/{id}")
    public ResponseEntity deleteAPI(@PathVariable("id") String value) {
        DeleteRequest deleteRequest = new DeleteRequest("myindex", value);
        DeleteResponse deleteResponse = null;
        try {
            deleteResponse = restHighLevelClient.delete(deleteRequest, RequestOptions.DEFAULT);
            return ResponseEntity.ok(deleteResponse);
        } catch (IOException e) {
            System.out.println("There is an Exception in delete method.");
            e.printStackTrace();
        }

        return (ResponseEntity) ResponseEntity.badRequest();
    }

    /*
    Term Vectors API returns information and statistics on terms in the fields of a particular document.
    The document could be stored in the index or artificially provided by the user.
     */

    /**
     * Term vectors api response entity.
     *
     * @return the response entity
     */
    @GetMapping("termvectors")
    public ResponseEntity termVectorsAPI() {

        //        This below code is for generating term vectors for the documents not present in the index.

        /*XContentBuilder docBuilder = null;
        try {
            docBuilder = XContentFactory.jsonBuilder();
            docBuilder.startObject().field("name", "Another Name").endObject();
        } catch (IOException e) {
            e.printStackTrace();
        }
        TermVectorsRequest termVectorsRequest = new TermVectorsRequest("myindex", docBuilder);
        */

        TermVectorsRequest termVectorsRequest = new TermVectorsRequest("myindex", "2");
        termVectorsRequest.setFields("name");

        TermVectorsResponse termVectorsResponse = null;
        try {
            termVectorsResponse = restHighLevelClient.termvectors(termVectorsRequest, RequestOptions.DEFAULT);
            return ResponseEntity.ok(termVectorsResponse);
        } catch (IOException e) {
            System.out.println("There is an Exception in term vectors method.");
            e.printStackTrace();
        }

        return (ResponseEntity) ResponseEntity.badRequest();
    }
}
