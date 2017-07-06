package com.example.question.model;

import com.example.question.constants.DatabaseConstants;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by akash.a on 01/07/17.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class BlogPart {
    private long id;
    private String content;
    private List<Comment> comments;

    public static class BlogPartMapper implements ResultSetMapper<BlogPart> {

        public BlogPart map(int i, ResultSet resultSet, StatementContext statementContext) throws SQLException {
            return new BlogPart(
                    resultSet.getLong(DatabaseConstants.FIELD_ID),
                    resultSet.getString(DatabaseConstants.FIELD_CONTENT),
                    new ArrayList<Comment>()
            );
        }
    }
}
