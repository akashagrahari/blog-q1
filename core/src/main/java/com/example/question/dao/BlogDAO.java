package com.example.question.dao;

import com.example.question.model.Blog;
import com.example.question.model.BlogPart;
import com.example.question.model.Comment;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.GetGeneratedKeys;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.Mapper;

import java.util.List;

import static com.example.question.constants.DatabaseConstants.*;

/**
 * Created by akash.a on 01/07/17.
 */
public interface BlogDAO {

    @SqlQuery("SELECT * FROM " + TABLE_BLOGS +
            " WHERE " + FIELD_ID + " < :id " +
            "ORDER BY " + FIELD_ID + " DESC " +
            "LIMIT :pageSize"
    )
    @Mapper(Blog.BlogMapper.class)
    List<Blog> getBlogListBeforeId(@Bind("id") long id, @Bind("pageSize") int pageSize);

    @SqlQuery("SELECT * FROM " + TABLE_BLOGS + " " +
            "ORDER BY " + FIELD_ID + " DESC LIMIT :pageSize"
    )
    @Mapper(Blog.BlogMapper.class)
    List<Blog> getBlogList(@Bind("pageSize") int pageSize);

    @SqlQuery("SELECT * FROM " + TABLE_BLOGS +
            " WHERE " + FIELD_UNIQUE_ID + " = :uniqueId "
    )
    @Mapper(Blog.BlogMapper.class)
    Blog getBlog(@Bind("uniqueId") String uniqueId);

    @SqlQuery("SELECT * FROM " + TABLE_BLOG_PARTS +
            " WHERE " + FIELD_BLOG_ID + " = :blogId"
    )
    @Mapper(BlogPart.BlogPartMapper.class)
    List<BlogPart> getBlogParts(@Bind("blogId") long blogId);

    @SqlQuery("SELECT * FROM " + TABLE_COMMENTS +
            " WHERE " + FIELD_BLOG_PART_ID + " = :blogPartId"
    )
    @Mapper(Comment.CommentMapper.class)
    List<Comment> getComments(@Bind("blogPartId") long blogPartId);

    @SqlUpdate("INSERT INTO " + TABLE_BLOGS + "(" +
            FIELD_UNIQUE_ID + " , " + FIELD_TITLE + ") " +
            "VALUES( " +
            ":uniqueId, :title )"
    )
    @GetGeneratedKeys
    long addBlog(@Bind("uniqueId") String uniqueId, @Bind("title") String title);

    @SqlUpdate("INSERT INTO " + TABLE_BLOG_PARTS + "(" +
            FIELD_CONTENT + " ," + FIELD_BLOG_ID + ") " +
            "VALUES( " +
            ":content, :blogId )"
    )
    @GetGeneratedKeys
    long addBlogParts(@Bind("content") String content, @Bind("blogId") long blogId);

    @SqlUpdate("INSERT INTO " + TABLE_COMMENTS + "(" +
            FIELD_CONTENT + " ," + FIELD_BLOG_PART_ID + ") " +
            "VALUES( " +
            ":content, :blogPartId)"
    )
    @GetGeneratedKeys
    long addComment(@Bind("blogPartId") long blogPartId, @Bind("content") String content);
}
