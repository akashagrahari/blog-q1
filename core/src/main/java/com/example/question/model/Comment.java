package com.example.question.model;

import com.example.question.constants.DatabaseConstants;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 * Created by akash.a on 01/07/17.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Comment {
    private long id;
    private String content;
    private Timestamp createdAt;

    public static class CommentMapper implements ResultSetMapper<Comment> {

        public Comment map(int i, ResultSet resultSet, StatementContext statementContext) throws SQLException {
            return new Comment(
                    resultSet.getLong(DatabaseConstants.FIELD_ID),
                    resultSet.getString(DatabaseConstants.FIELD_CONTENT),
                    new Timestamp(System.currentTimeMillis())
            );
        }
    }

}
