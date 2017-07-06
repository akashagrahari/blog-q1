package com.example.question.resources;

import com.example.question.Fixture;
import com.example.question.fetcher.BlogFetcher;
import com.example.question.getBlog.BlogResponse;
import com.example.question.listBlogs.BlogListResponse;
import com.example.question.model.Blog;
import com.example.question.model.BlogPart;
import com.example.question.model.Comment;
import com.google.common.collect.Lists;
import io.dropwizard.testing.junit.ResourceTestRule;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.mockito.Mockito;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

/**
 * Created by akash.a on 04/07/17.
 */
public class BlogResourceTest {

    private static final BlogFetcher blogFetcher = Mockito.mock(BlogFetcher.class);

    @ClassRule
    public static final ResourceTestRule resources = ResourceTestRule.builder()
            .addResource(new BlogResource(blogFetcher))
            .build();

    @Before
    public void setup() throws Exception{
        doNothing().when(blogFetcher).addBlog("Title 1", "Content 1");
        doNothing().when(blogFetcher).addComment(1, "Comment 1");
        when(blogFetcher.getBlogList(Optional.<Long>empty())).thenReturn(Optional.<List<Blog>>of(Lists.newArrayList(
                new Blog(1, "Unique_Id_1", "Title 1", Lists.newArrayList(new BlogPart()),
                        new Timestamp(System.currentTimeMillis()))
        )));
        when(blogFetcher.getBlogList(Optional.of(1L))).thenReturn(Optional.<List<Blog>>of(Lists.newArrayList(
                new Blog(1, "Unique_Id_1", "Title 1", Lists.newArrayList(new BlogPart()),
                        new Timestamp(System.currentTimeMillis()))
        )));
        when(blogFetcher.getBlogList(Optional.of(2L))).thenReturn(Optional.<List<Blog>>empty());

        Comment comment = new Comment(1, "Comment 1", new Timestamp(System.currentTimeMillis()));
        BlogPart blogPart = new BlogPart(1, "Content 1", Lists.newArrayList(comment));
        Blog blog = new Blog(1, "Unique_Id_1", "Title 1", Lists.newArrayList(blogPart),
                new Timestamp(System.currentTimeMillis()));
        when(blogFetcher.getBlog("Unique_Id_1")).thenReturn(Optional.of(blog));
        when(blogFetcher.getBlog("blah")).thenReturn(Optional.<Blog>empty());
    }

    @Test
    public void testAddBlogSuccess() {
        Response response = resources.client()
                .target("/blog/add_blog")
                .request()
                .post(Entity.json(Fixture.getValidBlogDetails()), Response.class);
        Assert.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    public void testAddBlogFailure() {
        Response response = resources.client()
                .target("/blog/add_blog")
                .request()
                .post(Entity.json(Fixture.getInValidBlogDetails()), Response.class);
        Assert.assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
    }

    @Test
    public void testAddCommentSuccess() {
        Response response = resources.client()
                .target("/blog/add_comment")
                .request()
                .post(Entity.json(Fixture.getValidCommentDetails()), Response.class);
        Assert.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    public void testAddCommentFailure() {
        Response response = resources.client()
                .target("/blog/add_comment")
                .request()
                .post(Entity.json(Fixture.getInValidCommentDetails()), Response.class);
        Assert.assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());

    }

    @Test
    public void testGetBlogListNullId() {
        BlogListResponse blogListResponse = resources.client().target(
                "/blog/list_blogs").request().get(BlogListResponse.class);
        Assert.assertEquals(1,blogListResponse.getBlogInfos().size());
    }

    @Test
    public void testGetBlogListIdNotPresent() {
        Response response = resources.client().target(
                "/blog/list_blogs?id=2").request().get(Response.class);
        Assert.assertEquals(Response.Status.NO_CONTENT.getStatusCode(),response.getStatus());
    }

    @Test
    public void testGetBlogListIdPresent() {
        BlogListResponse blogListResponse = resources.client().target(
                "/blog/list_blogs?id=1").request().get(BlogListResponse.class);
        Assert.assertEquals(1,blogListResponse.getBlogInfos().size());
    }

    @Test
    public void testGetBlogNullId() {
        Response response = resources.client().target(
                "/blog/get_blog").request().get(Response.class);
        Assert.assertEquals(Response.Status.BAD_REQUEST.getStatusCode(),response.getStatus());
    }

    @Test
    public void testGetBlogIdNotPresent() {
        Response response = resources.client().target(
                "/blog/get_blog?unique_id=blah").request().get(Response.class);
        Assert.assertEquals(Response.Status.NOT_IMPLEMENTED.getStatusCode(),response.getStatus());
    }

    @Test
    public void testGetBlogIdPresent() {
        BlogResponse blogResponse = resources.client().target(
            "/blog/get_blog?unique_id=Unique_Id_1").request().get(BlogResponse.class);
        Assert.assertEquals(1, blogResponse.getId());
    }


}
