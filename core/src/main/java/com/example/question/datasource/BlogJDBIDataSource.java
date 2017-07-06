package com.example.question.datasource;

import com.example.question.dao.BlogDAO;
import com.example.question.model.Blog;
import com.example.question.model.BlogPart;
import com.example.question.model.Comment;
import com.google.inject.Inject;
import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.Handle;

import java.util.List;
import java.util.UUID;

/**
 * Created by akash.a on 02/07/17.
 */
public class BlogJDBIDataSource implements BlogDataSource {

    private BlogDAO blogDAO;
    private DBI blogDBI;
    private int pageSize;

    @Inject
    public BlogJDBIDataSource(BlogDAO blogDAO, DBI blogDBI,@PageSize int pageSize) {
        this.blogDAO = blogDAO;
        this.blogDBI = blogDBI;
        this.pageSize = pageSize;
    }

    public List<Blog> getBlogList(long id) {
        return blogDAO.getBlogListBeforeId(id, pageSize);
    }

    public List<Blog> getBlogList() {
        return blogDAO.getBlogList(pageSize);
    }

    public Blog getBlog(String uniqueId) {
        Blog blog = blogDAO.getBlog(uniqueId);
        if (blog != null) {
            List<BlogPart> blogParts = blogDAO.getBlogParts(blog.getId());
            for (BlogPart blogPart : blogParts) {
                List<Comment> comments = blogDAO.getComments(blogPart.getId());
                blogPart.getComments().addAll(comments);
            }
            blog.getBlogParts().addAll(blogParts);
        }
        return blog;
    }

    public void addBlog(String title, List<String> contents) {
        final Handle handle = blogDBI.open();
        BlogDAO blogDAO = handle.attach(BlogDAO.class);
        handle.begin();
        try {
            long blogId = blogDAO.addBlog(UUID.randomUUID().toString(), title);
            for (String content : contents) {
                blogDAO.addBlogParts(content, blogId);
            }
            handle.commit();
        } catch (Throwable e) {
            handle.rollback();
            throw e;
        } finally {
            handle.close();
        }
    }

    public void addComment(long blogPartId, String content) {
        blogDAO.addComment(blogPartId, content);
    }
}
