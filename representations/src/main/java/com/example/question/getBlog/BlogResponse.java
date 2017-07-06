package com.example.question.getBlog;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

/**
 * Created by akash.a on 02/07/17.
 */
@AllArgsConstructor
@Getter
public class BlogResponse {

    private long id;

    @JsonProperty("unique_id")
    private String uniqueId;

    private String title;

    private List<BlogPartInfo> blogPartInfos;

}
