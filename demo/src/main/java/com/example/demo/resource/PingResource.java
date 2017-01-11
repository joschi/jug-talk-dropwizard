package com.example.demo.resource;

import com.codahale.metrics.annotation.Metered;
import com.example.demo.model.ImmutablePong;
import com.example.demo.model.Pong;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Path("/ping")
@Produces(MediaType.APPLICATION_JSON)
@Api("Ping Pong")
public class PingResource {
    @GET
    @Metered
    @ApiOperation("Ping")
    public Pong pingPong(@QueryParam("message")
                         @ApiParam("Pong message")
                         @DefaultValue("pong") String message) {
        return ImmutablePong.builder()
                .message(message)
                .build();
    }
}
