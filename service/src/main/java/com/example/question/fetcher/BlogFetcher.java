package com.example.question.fetcher;

import com.example.question.datasource.BlogDataSource;
import com.example.question.model.Blog;
import com.google.common.base.Splitter;
import com.google.inject.Inject;

import java.util.List;
import java.util.Optional;

/**
 * Created by akash.a on 02/07/17.
 */
public class BlogFetcher {

    public static final Splitter SPLITTER = Splitter.on("\n\n");

    private final BlogDataSource blogDataSource;

    @Inject
    public BlogFetcher(BlogDataSource blogDataSource) {
        this.blogDataSource = blogDataSource;
    }


    public void addBlog(String title, String content) {
        List<String> blogParts = SPLITTER.splitToList(content.trim());
        blogDataSource.addBlog(title, blogParts);
    }

    public void addComment(long blogPartId, String content) {
        blogDataSource.addComment(blogPartId, content);
    }


    public Optional<List<Blog>> getBlogList(Optional<Long> id) {
        if (id.isPresent()) {
            return Optional.ofNullable(blogDataSource.getBlogList(id.get()));
        } else {
            return Optional.ofNullable(blogDataSource.getBlogList());
        }
    }

    public Optional<Blog> getBlog(String uniqueId) {
        return Optional.ofNullable(blogDataSource.getBlog(uniqueId));
    }
}
