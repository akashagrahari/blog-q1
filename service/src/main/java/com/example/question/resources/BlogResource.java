package com.example.question.resources;

import com.example.question.ErrorResponse;
import com.example.question.addBlog.BlogBody;
import com.example.question.addComment.CommentBody;
import com.example.question.fetcher.BlogFetcher;
import com.example.question.getBlog.BlogPartInfo;
import com.example.question.getBlog.BlogResponse;
import com.example.question.getBlog.CommentInfo;
import com.example.question.listBlogs.BlogInfo;
import com.example.question.listBlogs.BlogListResponse;
import com.example.question.model.Blog;
import com.example.question.model.BlogPart;
import com.example.question.model.Comment;
import com.google.common.collect.Lists;
import com.google.inject.Inject;
import lombok.extern.slf4j.Slf4j;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;

/**
 * Created by akash.a on 02/07/17.
 */

@Path("/blog")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Slf4j
public class BlogResource {

    private final BlogFetcher blogFetcher;

    @Inject
    public BlogResource(BlogFetcher blogFetcher) {
        this.blogFetcher = blogFetcher;
    }

    @POST
    @Path("/add_blog")
    public Response addBlog(BlogBody blogBody) {
        if(blogBody==null || blogBody.getTitle()==null || blogBody.getContent()==null) {
            Response response = Response.status(Response.Status.BAD_REQUEST).entity(new ErrorResponse("Invalid Payload")).build();
            throw new WebApplicationException(response);
        }
        else {
            blogFetcher.addBlog(blogBody.getTitle(), blogBody.getContent());
            return Response.status(Response.Status.OK).build();
        }
    }

    @POST
    @Path("/add_comment")
    public Response addComment(CommentBody commentBody) {
        if(commentBody==null || commentBody.getContent()==null)
        {
            Response response = Response.status(Response.Status.BAD_REQUEST).entity(new ErrorResponse("Invalid Payload")).build();
            throw new WebApplicationException(response);
        }
        else {
            blogFetcher.addComment(commentBody.getBlogPartId(), commentBody.getContent());
            return Response.status(Response.Status.OK).build();
        }
    }

    @GET
    @Path("/list_blogs")
    public Response getBlogList(@QueryParam("id") Long id) {
        List<BlogInfo> blogInfos = Lists.newArrayList();
        Optional<List<Blog>> blogsOptional = blogFetcher.getBlogList(Optional.ofNullable(id));
        if(blogsOptional.isPresent()) {
            for (Blog blog : blogsOptional.get()) {
                BlogInfo blogInfo = new BlogInfo(blog.getId(), blog.getUniqueId(), blog.getTitle());
                blogInfos.add(blogInfo);
            }
        }
        else {
            Response response = Response.status(Response.Status.NO_CONTENT).entity(new ErrorResponse("No Blogs Found")).build();
            throw new WebApplicationException(response);
        }
        BlogListResponse blogListResponse = new BlogListResponse(blogInfos);
        return Response.status(Response.Status.OK).entity(blogListResponse).build();
    }

    @GET
    @Path("/get_blog")
    public Response getBlog(@QueryParam("unique_id") String uniqueId) {
        if(uniqueId==null) {
            Response response = Response.status(Response.Status.BAD_REQUEST).entity(new ErrorResponse("Invalid Payload")).build();
            throw new WebApplicationException(response);
        }
        else {
            Optional<Blog> blogOptional = blogFetcher.getBlog(uniqueId);
            if (blogOptional.isPresent()) {
                Blog blog = blogOptional.get();
                List<BlogPartInfo> blogPartInfos = getBlogPartInfos(blog);
                BlogResponse blogResponse = new BlogResponse(blog.getId(), blog.getUniqueId(), blog.getTitle(),
                        blogPartInfos);
                return Response.status(Response.Status.OK).entity(blogResponse).build();
            } else {
                Response response = Response.status(Response.Status.NOT_IMPLEMENTED).entity(new ErrorResponse("Blog not Found")).build();
                throw new WebApplicationException(response);
            }
        }

    }

    private List<BlogPartInfo> getBlogPartInfos(Blog blog) {
        List<BlogPart> blogParts = blog.getBlogParts();
        List<BlogPartInfo> blogPartInfos = Lists.newArrayList();
        for (BlogPart blogPart : blogParts) {
            List<Comment> comments = blogPart.getComments();
            List<CommentInfo> commentInfos = Lists.newArrayList();
            for (Comment comment : comments) {
                CommentInfo commentInfo = new CommentInfo(comment.getId(), comment.getContent());
                commentInfos.add(commentInfo);
            }
            BlogPartInfo blogPartInfo = new BlogPartInfo(blogPart.getId(), blogPart.getContent(), commentInfos);
            blogPartInfos.add(blogPartInfo);
        }
        return blogPartInfos;
    }
}
