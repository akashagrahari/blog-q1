package com.example.question;

import com.example.question.configuration.BlogConfiguration;
import com.example.question.resources.BlogResource;
import com.google.inject.Guice;
import com.google.inject.Injector;
import io.dropwizard.Application;
import io.dropwizard.setup.Environment;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by akash.a on 02/07/17.
 */
@Slf4j
public class BlogApplication extends Application<BlogConfiguration> {
    @Override
    public void run(BlogConfiguration blogConfiguration, Environment environment) throws Exception {
        Injector injector = Guice.createInjector(new BlogModule(blogConfiguration, environment));
        environment.jersey().register(injector.getInstance(BlogResource.class));
    }

    public static void main(String[] args) throws Exception {
        new BlogApplication().run(args);
    }
}
