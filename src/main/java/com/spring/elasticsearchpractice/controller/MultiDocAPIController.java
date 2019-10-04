package com.spring.elasticsearchpractice.controller;

import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.get.MultiGetRequest;
import org.elasticsearch.action.get.MultiGetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.DeleteByQueryRequest;
import org.elasticsearch.index.reindex.ReindexRequest;
import org.elasticsearch.index.reindex.UpdateByQueryRequest;
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

    /*
    The multiGet API executes multiple get requests in a single http request in parallel.
     */

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
            return ResponseEntity.ok(multiGetResponse);
        } catch (IOException e) {
            System.out.println("There is an Exception in multiGet method.");
            e.printStackTrace();
        }

        return (ResponseEntity) ResponseEntity.badRequest();
    }

    /*
    A BulkRequest can be used to execute multiple index, update and/or delete operations using a single request.
     */

    /**
     * Bulk api response entity.
     *
     * @return the response entity
     */
    @GetMapping("bulk")
    public ResponseEntity bulkAPI() {

        BulkRequest bulkRequest = new BulkRequest();
        bulkRequest.add(new IndexRequest("otherindex").id("5").source(XContentType.JSON, "filedname", "fieldvalue"));
        bulkRequest.add(new IndexRequest("anotherindex").id("6").source(XContentType.JSON, "Another filed_name", "Another field_value"));
        bulkRequest.add(new UpdateRequest("otherindex", "5").doc(XContentType.JSON, "Other filed_name", "Other field_value"));

        BulkResponse bulkResponse = null;
        try {
            bulkResponse = restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
            return ResponseEntity.ok(bulkResponse);
        } catch (IOException e) {
            System.out.println("There is an Exception in bulk method.");
            e.printStackTrace();
        }

        return (ResponseEntity) ResponseEntity.badRequest();
    }

    /*
    A ReindexRequest can be used to copy documents from one or more indexes into a destination index.
    */

    /**
     * Re index api response entity.
     *
     * @return the response entity
     */
    @GetMapping("reindex")
    public ResponseEntity reIndexAPI() {
        ReindexRequest reindexRequest = new ReindexRequest();
        reindexRequest.setSourceIndices("myindex", "otherindex");
        reindexRequest.setDestIndex("anotherindex");
        BulkByScrollResponse response = null;
        try {
            response = restHighLevelClient.reindex(reindexRequest, RequestOptions.DEFAULT);
            return ResponseEntity.ok(response);
        } catch (IOException e) {
            System.out.println("There is an Exception in reIndex method.");
            e.printStackTrace();
        }

        return (ResponseEntity) ResponseEntity.badRequest();
    }

    /*
    _update_by_query gets a snapshot of the index when it starts and indexes what it finds using internal versioning.
    That means you’ll get a version conflict if the document changes between the time when the snapshot was taken
    and when the index request is processed. When the versions match, the document is updated and the version number
    is incremented.
    */

    /**
     * Update by query response entity.
     *
     * @return the response entity
     */
    @GetMapping("updatebyquery")
    public ResponseEntity updateByQuery() {
        UpdateByQueryRequest updateByQueryRequest = new UpdateByQueryRequest("myindex", "otherindex");
        updateByQueryRequest.setQuery(new TermQueryBuilder("mobile", "9876543210"));

        BulkByScrollResponse response = null;
        try {
            response = restHighLevelClient.updateByQuery(updateByQueryRequest, RequestOptions.DEFAULT);
            return ResponseEntity.ok(response);
        } catch (IOException e) {
            System.out.println("There is an Exception in updateByQuery method.");
            e.printStackTrace();
        }
        return (ResponseEntity) ResponseEntity.badRequest();
    }

    /**
     * Delete by query response entity.
     *
     * @return the response entity
     */
    @GetMapping("deletebyquery")
    public ResponseEntity deleteByQuery() {
        DeleteByQueryRequest deleteByQueryRequest = new DeleteByQueryRequest("anotherindex");
        deleteByQueryRequest.setQuery(new TermQueryBuilder("mobile", "9876543210"));

        BulkByScrollResponse response = null;
        try {
            response = restHighLevelClient.deleteByQuery(deleteByQueryRequest, RequestOptions.DEFAULT);
            return ResponseEntity.ok(response);
        } catch (IOException e) {
            System.out.println("There is an Exception in deleteByQuery method.");
            e.printStackTrace();
        }

        return (ResponseEntity) ResponseEntity.badRequest();
    }

}
