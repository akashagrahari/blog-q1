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
public class BlogPartInfo {

    private long id;

    private String content;

    @JsonProperty("comments")
    private List<CommentInfo> commentInfos;

}
