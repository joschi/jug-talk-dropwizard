package com.example.demo;

import com.example.demo.commands.GreetingCommand;
import com.example.demo.health.DemoHealthCheck;
import com.example.demo.health.InMemoryStoreHealthCheck;
import com.example.demo.model.ImmutableKitten;
import com.example.demo.model.Kitten;
import com.example.demo.resource.KittenResource;
import com.example.demo.resource.PingResource;
import com.example.demo.tasks.EchoTask;
import com.example.demo.tasks.ToggleHealthCheckTask;
import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.views.ViewBundle;
import io.federecio.dropwizard.swagger.SwaggerBundle;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;

public class DemoApplication extends Application<DemoConfiguration> {
    public static void main(final String[] args) throws Exception {
        new DemoApplication().run(args);
    }

    @Override
    public void initialize(final Bootstrap<DemoConfiguration> bootstrap) {
        bootstrap.addBundle(new AssetsBundle("/kitten-images", "/images", "index.html", "images-asset"));
        bootstrap.addBundle(new ViewBundle<>());
        bootstrap.addBundle(new SwaggerBundle<DemoConfiguration>() {
            @Override
            protected SwaggerBundleConfiguration getSwaggerBundleConfiguration(DemoConfiguration configuration) {
                return configuration.getSwaggerBundleConfiguration();
            }
        });
        bootstrap.addCommand(new GreetingCommand("greet", "Print a greeting"));
    }

    @Override
    public void run(final DemoConfiguration configuration, final Environment environment) throws Exception {
        final InMemoryStore store = new InMemoryStore();
        environment.lifecycle().manage(store);

        environment.jersey().register(new PingResource());
        environment.jersey().register(new KittenResource(store));

        final DemoHealthCheck demoHealthCheck = new DemoHealthCheck();
        environment.healthChecks().register("demo-health", demoHealthCheck);
        environment.healthChecks().register("store-health", new InMemoryStoreHealthCheck(store));

        environment.admin().addTask(new EchoTask());
        environment.admin().addTask(new ToggleHealthCheckTask("toggle-health", demoHealthCheck));

        // add one demo kitten
        final Kitten demoKitten = ImmutableKitten.builder()
                .name("Findus")
                .age(5)
                .type(Kitten.KittenType.CUTE)
                .imagePath("../../images/findus.jpg")
                .build();
        store.put(demoKitten);
    }

}
