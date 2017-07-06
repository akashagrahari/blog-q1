package com.example.question.addComment;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by akash.a on 02/07/17.
 */

@AllArgsConstructor
@Getter
public class CommentBody {

    @JsonProperty("blog_part_id")
    private long blogPartId;

    private String content;

}
