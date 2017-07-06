package com.example.question;

import com.example.question.configuration.BlogConfiguration;
import com.example.question.dao.BlogDAO;
import com.example.question.datasource.BlogDataSource;
import com.example.question.datasource.BlogJDBIDataSource;
import com.example.question.datasource.PageSize;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import io.dropwizard.jdbi.DBIFactory;
import io.dropwizard.setup.Environment;
import lombok.AllArgsConstructor;
import org.skife.jdbi.v2.DBI;

/**
 * Created by akash.a on 02/07/17.
 */
@AllArgsConstructor
public class BlogModule extends AbstractModule {

    private BlogConfiguration configuration;
    private Environment environment;

    @Override
    protected void configure() {
        final DBIFactory factory = new DBIFactory();
        final DBI blogJDBI = factory.build(environment, configuration.getBlogDataSourceFactory(), "mysql-blog");

        bind(DBI.class).toInstance(blogJDBI);
        bind(BlogDAO.class).toInstance(blogJDBI.onDemand(BlogDAO.class));
        bind(BlogDataSource.class).to(BlogJDBIDataSource.class).in(Singleton.class);
    }

    @Provides
    @PageSize
    public int getPageSize() {
        return configuration.getPageSize();
    }
}
