package com.example.question.listBlogs;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

/**
 * Created by akash.a on 02/07/17.
 */
@AllArgsConstructor
@Getter
public class BlogListResponse {

    @JsonProperty("blogs")
    List<BlogInfo> blogInfos;


}
