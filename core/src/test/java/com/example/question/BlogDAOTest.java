package com.example.question;

import com.codahale.metrics.MetricRegistry;
import com.example.question.dao.BlogDAO;
import com.example.question.model.Blog;
import com.example.question.model.BlogPart;
import com.example.question.model.Comment;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.jackson.Jackson;
import io.dropwizard.jdbi.DBIFactory;
import io.dropwizard.setup.Environment;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.Handle;

import java.io.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by akash.a on 04/07/17.
 */
public class BlogDAOTest {

    protected DBI dbi;
    private Handle handle;
    BlogDAO blogDAO;

    @Before
    public void setupDatabase() throws FileNotFoundException {
        Environment environment = new Environment( "test-env", Jackson.newObjectMapper(), null, new MetricRegistry(), null );
        dbi = new DBIFactory().build( environment, getDataSourceFactory() , "test" );
        handle = dbi.open();
        blogDAO = handle.attach(BlogDAO.class);
        runMigrations();
    }

    private void runMigrations() throws FileNotFoundException {
        File directory = new File("../migrations");
        if (!directory.exists() || !directory.isDirectory()) {
            throw new IllegalArgumentException("../migration " + " - File not found");
        }
        File[] files = directory.listFiles();
        String fileScript = "";
        for(File file : files) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            fileScript = String.join("\n", reader.lines().collect(Collectors.toList()));
        }
        handle.execute(fileScript);
    }


    @Test
    public void testGetBlogListBeforeId() {
        blogDAO.addBlog("Unique Id 1", "Test Title 1");
        blogDAO.addBlog("Unique Id 2", "Test Title 2");
        blogDAO.addBlog("Unique Id 3", "Test Title 3");
        blogDAO.addBlog("Unique Id 4", "Test Title 4");
        List<Blog> blogs = blogDAO.getBlogListBeforeId(4,2);
        Assert.assertNotNull(blogs);
        Assert.assertEquals(2, blogs.size());
        Assert.assertEquals(3, blogs.get(0).getId());
    }

    @Test
    public void testGetBlogListEmpty() {
        List<Blog> blogs = blogDAO.getBlogListBeforeId(1, 2);
        Assert.assertEquals(0, blogs.size());
    }

    @Test
    public void testGetBlogList() {
        blogDAO.addBlog("Unique Id 1", "Test Title 1");
        blogDAO.addBlog("Unique Id 2", "Test Title 2");
        blogDAO.addBlog("Unique Id 3", "Test Title 3");
        blogDAO.addBlog("Unique Id 4", "Test Title 4");
        List<Blog> blogs = blogDAO.getBlogList(2);
        Assert.assertNotNull(blogs);
        Assert.assertEquals(2, blogs.size());
        Assert.assertEquals(4, blogs.get(0).getId());
    }

    @Test
    public void testGetBlog() {
        blogDAO.addBlog("Unique Id 1", "Test Title 1");
        Blog blog = blogDAO.getBlog("Unique Id 1");
        Assert.assertNotNull(blog);
        Assert.assertEquals(1, blog.getId());
    }

    @Test
    public void testGetBlogWrongId() {
        Blog blog = blogDAO.getBlog("wrong Id");
        Assert.assertNull(blog);
    }

    @Test
    public void testGetBlogParts() {
        blogDAO.addBlogParts("Content 1", 1);
        blogDAO.addBlogParts("Content 2", 1);
        List<BlogPart> blogParts = blogDAO.getBlogParts(1);
        Assert.assertNotNull(blogParts);
        Assert.assertEquals(1, blogParts.get(0).getId());
    }

    @Test
    public void testGetBlogPartsWrongId() {
        List<BlogPart> blogParts = blogDAO.getBlogParts(2);
        Assert.assertEquals(0, blogParts.size());
    }

    @Test
    public void testGetComments() {
        blogDAO.addComment(1, "comment 1");
        blogDAO.addComment(1, "comment 2");
        List<Comment> comments = blogDAO.getComments(1);
        Assert.assertNotNull(comments);
        Assert.assertEquals(1, comments.get(0).getId());
    }

    @Test
    public void testGetCommentsWrongId() {
        List<Comment> comments = blogDAO.getComments(2);
        Assert.assertEquals(0, comments.size());
    }

    @After
    public void tearDown() throws Exception {
        handle.close();
    }


    private DataSourceFactory getDataSourceFactory() {
        DataSourceFactory dataSourceFactory = new DataSourceFactory();
        dataSourceFactory.setDriverClass("org.h2.Driver");
        dataSourceFactory.setUrl("jdbc:h2:./build/h2db");
        dataSourceFactory.setUser("sa");
        dataSourceFactory.setPassword("sa");
        return dataSourceFactory;
    }
}
