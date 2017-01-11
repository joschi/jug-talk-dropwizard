package com.example.demo.resource;

import com.example.demo.model.ImmutablePong;
import com.example.demo.model.Pong;
import io.dropwizard.testing.junit.ResourceTestRule;
import org.junit.ClassRule;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PingResourceTest {
    @ClassRule
    public static final ResourceTestRule resources =
            ResourceTestRule.builder()
                    .addResource(new PingResource())
                    .build();

    @Test
    public void testPingPong() throws Exception {
        final Pong expectedPong = ImmutablePong.builder()
                .message("pong")
                .build();
        final Pong actualPong = resources.client().target("/ping")
                .request()
                .get(Pong.class);

        assertEquals(expectedPong, actualPong);
    }
}
