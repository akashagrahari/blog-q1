package com.example.question;

import com.example.question.addBlog.BlogBody;
import com.example.question.addComment.CommentBody;

/**
 * Created by akash.a on 05/07/17.
 */
public class Fixture {
    public static BlogBody getValidBlogDetails() {
        return new BlogBody("Title 1", "Content 1");
    }

    public static BlogBody getInValidBlogDetails() {
        return new BlogBody(null, null);
    }

    public static CommentBody getValidCommentDetails() {
        return new CommentBody(1, "Comment 1");
    }

    public static CommentBody getInValidCommentDetails() {
        return new CommentBody(1, null);
    }
}
