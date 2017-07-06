package com.example.question.datasource;

import com.example.question.model.Blog;

import java.util.List;

/**
 * Created by akash.a on 02/07/17.
 */
public interface BlogDataSource {

    List<Blog> getBlogList(long id);

    List<Blog> getBlogList();

    Blog getBlog(String uniqueId);

    void addBlog(String title, List<String> contents);

    void addComment(long blogPartId, String content);
}
