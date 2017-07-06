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
import java.util.ArrayList;
import java.util.List;

/**
 * Created by akash.a on 01/07/17.
 */

@AllArgsConstructor
@Getter
@NoArgsConstructor
public class Blog {
    private long id;
    private String uniqueId;
    private String title;
    private List<BlogPart> blogParts;
    private Timestamp createdAt;

    public static class BlogMapper implements ResultSetMapper<Blog> {

        public Blog map(int i, ResultSet resultSet, StatementContext statementContext) throws SQLException {
            return new Blog(
                    resultSet.getLong(DatabaseConstants.FIELD_ID),
                    resultSet.getString(DatabaseConstants.FIELD_UNIQUE_ID),
                    resultSet.getString(DatabaseConstants.FIELD_TITLE),
                    new ArrayList<>(),
                    new Timestamp(System.currentTimeMillis())
            );
        }
    }
}
