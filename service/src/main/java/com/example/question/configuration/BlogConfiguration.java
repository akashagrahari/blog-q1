package com.example.question.configuration;

import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * Created by akash.a on 02/07/17.
 */
@Setter
@Getter
public class BlogConfiguration extends Configuration {

    @Valid
    @NotNull
    private final DataSourceFactory blogDataSourceFactory = new DataSourceFactory();

    @Valid
    @NotNull
    private int pageSize;


}
