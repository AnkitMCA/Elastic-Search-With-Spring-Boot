package com.spring.elasticsearchpractice.controller;

import org.elasticsearch.action.search.MultiSearchRequest;
import org.elasticsearch.action.search.MultiSearchResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * The type Search api controller.
 */
@RestController
@RequestMapping("api")
public class SearchAPIsController {

    /**
     * The Rest high level client.
     */
    @Autowired
    RestHighLevelClient restHighLevelClient;

    /*
    SearchScroll API

    To explain Scan & scroll briefly, what it essentially does is that it scans the index for the query provided
    with the scan request and returns a scroll_id. This scroll_id can be passed to the next scroll request to
    return the next batch of results.

    //Purpose of the scroll property//.
    It does not mean that elasticsearch will fetch next page data after 30 seconds.
    When you are doing first scroll request you need to specify when scroll context should be closed.
    scroll parameter is telling to close scroll context after 30 seconds.
     */

    /**
     * Search api response entity.
     *
     * @return the response entity
     */
    @GetMapping("search")
    public ResponseEntity searchAPI() {
        SearchRequest searchRequest = new SearchRequest();
//        SearchRequest searchRequest = new SearchRequest("myindex", "otherindex");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchQuery("name", "Ankit"));
//        searchSourceBuilder.query(QueryBuilders.termsQuery("mobile", "9876543210"));
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = null;
        try {
            searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
            return ResponseEntity.ok(searchResponse);
        } catch (IOException e) {
            System.out.println("There is an Exception in search method.");
            e.printStackTrace();
        }

        return (ResponseEntity) ResponseEntity.badRequest();
    }

    /**
     * Multi search api response entity.
     *
     * @return the response entity
     */
    @GetMapping("multisearch")
    public ResponseEntity multiSearchAPI() {
        MultiSearchRequest multiSearchRequest = new MultiSearchRequest();
        SearchRequest searchRequest1 = new SearchRequest();
        SearchSourceBuilder searchSourceBuilder1 = new SearchSourceBuilder();
        searchSourceBuilder1.query(QueryBuilders.termsQuery("mobile", "9876543210"));
        searchRequest1.source(searchSourceBuilder1);
        SearchRequest searchRequest2 = new SearchRequest();
        SearchSourceBuilder searchSourceBuilder2 = new SearchSourceBuilder();
        searchSourceBuilder2.query(QueryBuilders.termsQuery("Another filed_name", "Another filed_value"));
        searchRequest2.source(searchSourceBuilder2);

        multiSearchRequest.add(searchRequest1);
        multiSearchRequest.add(searchRequest2);
        MultiSearchResponse multiSearchResponse = null;
        try {
            multiSearchResponse = restHighLevelClient.msearch(multiSearchRequest, RequestOptions.DEFAULT);
            return ResponseEntity.ok(multiSearchResponse);
        } catch (IOException e) {
            System.out.println("There is an Exception in multiSearch method.");
            e.printStackTrace();
        }

        return (ResponseEntity) ResponseEntity.badRequest();
    }


}
