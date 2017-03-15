package com.example.demo.resource;

import com.example.demo.DemoApplication;
import com.example.demo.DemoConfiguration;
import com.example.demo.model.ImmutableKitten;
import com.example.demo.model.Kitten;
import com.google.common.collect.Iterables;
import io.dropwizard.testing.ResourceHelpers;
import io.dropwizard.testing.junit.DropwizardAppRule;
import org.junit.ClassRule;
import org.junit.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

public class KittenIntegrationTest {
    @ClassRule
    public static final DropwizardAppRule<DemoConfiguration> RULE =
            new DropwizardAppRule<>(
                    DemoApplication.class,
                    ResourceHelpers.resourceFilePath("demo_test.yml"));

    @Test
    public void testListKittens() throws IOException {
        final Client client = ClientBuilder.newClient();
        final Collection<Kitten> kittens = client
                .target(String.format("http://localhost:%d/kittens/", RULE.getLocalPort()))
                .request(MediaType.APPLICATION_JSON)
                .get(new GenericType<Collection<Kitten>>() {});

        assertEquals(1, kittens.size());
        assertEquals("Findus", Iterables.getOnlyElement(kittens).getName());
    }

    @Test
    public void testCreateAndGetRoundTrip() throws IOException {
        final Kitten kitten = ImmutableKitten.builder()
                .name("Grumpy")
                .age(4)
                .type(Kitten.KittenType.GRUMPY)
                .imagePath("images/grumpy.jpg")
                .build();

        final Client client = ClientBuilder.newClient();
        final Response response = client
                .target(String.format("http://localhost:%d", RULE.getLocalPort())).path("kittens")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.json(kitten));

        assertEquals(201, response.getStatus());

        final Kitten receivedKitten = client
                .target(String.format("http://localhost:%d/kittens/Grumpy", RULE.getLocalPort()))
                .request(MediaType.APPLICATION_JSON)
                .get(Kitten.class);

        assertEquals(kitten, receivedKitten);
    }

    @Test
    public void testKittenNotFound() throws IOException {
        final Client client = ClientBuilder.newClient();
        final Response response = client
                .target(String.format("http://localhost:%d/kittens/Carlo", RULE.getLocalPort()))
                .request(MediaType.APPLICATION_JSON)
                .get();
        assertEquals(404, response.getStatus());
    }
}
