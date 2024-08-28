package org.example.searchservice.controller;

import org.example.searchservice.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SearchController {

    @Autowired
    SearchService searchService;

    @GetMapping("/search/tags/{tag}/productIds")
    public List<Long> getTagProductIds(@PathVariable String tag) {
        return searchService.getProductIdsByTag(tag);
    }
}
