package com.example.question.listBlogs;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by akash.a on 02/07/17.
 */
@AllArgsConstructor
@Getter
public class BlogInfo {

    private long id;

    @JsonProperty("unique_id")
    private String uniqueId;

    private String title;
}
