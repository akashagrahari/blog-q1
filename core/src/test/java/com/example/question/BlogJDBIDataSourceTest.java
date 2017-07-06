package com.example.question;

import com.example.question.dao.BlogDAO;
import com.example.question.datasource.BlogJDBIDataSource;
import com.example.question.model.Blog;
import com.example.question.model.BlogPart;
import com.example.question.model.Comment;
import com.google.common.collect.Lists;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.Handle;

import java.sql.Timestamp;

import static org.mockito.Mockito.when;

/**
 * Created by akash.a on 04/07/17.
 */
public class BlogJDBIDataSourceTest {

    private BlogJDBIDataSource datasource;
    @Mock
    private BlogDAO blogDAO;
    @Mock
    private DBI blogDBI;
    @Mock
    private Handle handle;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        datasource = new BlogJDBIDataSource(blogDAO, blogDBI, 2);

        when(blogDAO.getBlog("Unique Id 1")).thenReturn(new Blog(1, "Unique Id 1", "Title 1",
                Lists.newArrayList(), new Timestamp(System.currentTimeMillis())));
        when(blogDAO.getBlog("Wrong Id")).thenReturn(null);
        when(blogDAO.getBlogParts(1)).thenReturn(Lists.newArrayList(new BlogPart(1, "Content 1", Lists.newArrayList())));
        when(blogDAO.getComments(1)).thenReturn(Lists.newArrayList(new Comment(1, "Comment 1",
                new Timestamp(System.currentTimeMillis()))));
        when(blogDAO.getBlogList(1)).thenReturn(Lists.newArrayList());
        when(blogDAO.getBlogListBeforeId(1,1)).thenReturn(Lists.newArrayList());
        when(blogDAO.getBlogList(1)).thenReturn(Lists.newArrayList());
        when(blogDAO.addComment(1,"Comment 1")).thenReturn(1L);

    }

    @Test
    public void testGetBlog() {
        Blog blog = datasource.getBlog("Unique Id 1");
        Assert.assertEquals(1, blog.getId());
        Assert.assertEquals(1, blog.getBlogParts().size());
        Assert.assertEquals(1, blog.getBlogParts().get(0).getComments().size());
        Mockito.verify(blogDAO).getBlog("Unique Id 1");
        Mockito.verify(blogDAO).getBlogParts(1);
        Mockito.verify(blogDAO).getComments(1);
    }

    @Test
    public void testGetBlogNullCheck() {
        Blog blog = datasource.getBlog("Wrong Id");
        Assert.assertNull(blog);
    }

    @Test
    public void testGetBlogListWithId() {
        datasource.getBlogList(1);
        Mockito.verify(blogDAO).getBlogListBeforeId(1,2);

    }

    @Test
    public void testGetBlogListWithoutId() {
        datasource.getBlogList();
        Mockito.verify(blogDAO).getBlogList(2);
    }

}
