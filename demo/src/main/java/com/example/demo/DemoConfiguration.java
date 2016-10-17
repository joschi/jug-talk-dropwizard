package com.example.demo;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Min;

public class DemoConfiguration extends Configuration {
    @NotBlank
    private String speakerName;

    @Min(2016)
    private int year;

    @JsonProperty("swagger")
    private SwaggerBundleConfiguration swaggerBundleConfiguration;

    public String getSpeakerName() {
        return speakerName;
    }

    public int getYear() {
        return year;
    }

    public SwaggerBundleConfiguration getSwaggerBundleConfiguration() {
        return swaggerBundleConfiguration;
    }
}
