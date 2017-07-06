package com.example.question.fetcher;

import com.example.question.datasource.BlogDataSource;
import com.example.question.model.Blog;
import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

/**
 * Created by akash.a on 04/07/17.
 */

public class BlogFetcherTest {

    @Mock
    private BlogDataSource blogDataSource;
    private BlogFetcher blogFetcher;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        blogFetcher = new BlogFetcher(blogDataSource);
        doNothing().when(blogDataSource).addBlog("Title 1", Lists.newArrayList("Content 1", "Content 2"));
        doNothing().when(blogDataSource).addComment(1, "Comment 1");
        when(blogDataSource.getBlogList(1L)).thenReturn(Lists.<Blog>newArrayList());
        when(blogDataSource.getBlogList()).thenReturn(Lists.<Blog>newArrayList());
        when(blogDataSource.getBlog("Unique_Id_1")).thenReturn(new Blog());
    }

    @Test
    public void testAddBlog() {
        blogFetcher.addBlog("Title 1", "Content 1\n\nContent 2");
        Mockito.verify(blogDataSource).addBlog("Title 1", Lists.newArrayList("Content 1", "Content 2"));
    }

    @Test
    public void testAddComment() {
        blogFetcher.addComment(1, "Comment 1");
        Mockito.verify(blogDataSource).addComment(1, "Comment 1");
    }

    @Test
    public void testGetBlogListPresentId() {
        blogFetcher.getBlogList(Optional.of(1L));
        Mockito.verify(blogDataSource).getBlogList(1L);
    }

    @Test
    public void testGetBlogListIdNotPresent() {
        blogFetcher.getBlogList(Optional.<Long>empty());
        Mockito.verify(blogDataSource).getBlogList();
    }

    @Test
    public void testGetBlog() {
        blogFetcher.getBlog("Unique_Id_1");
        Mockito.verify(blogDataSource).getBlog("Unique_Id_1");
    }

}
