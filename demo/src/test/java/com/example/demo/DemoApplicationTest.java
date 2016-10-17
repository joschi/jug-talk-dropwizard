package com.example.demo;

import com.codahale.metrics.health.HealthCheckRegistry;
import com.example.demo.health.InMemoryStoreHealthCheck;
import com.example.demo.resource.KittenResource;
import com.example.demo.resource.PingResource;
import com.example.demo.tasks.ToggleHealthCheckTask;
import io.dropwizard.jersey.setup.JerseyEnvironment;
import io.dropwizard.lifecycle.setup.LifecycleEnvironment;
import io.dropwizard.setup.AdminEnvironment;
import io.dropwizard.setup.Environment;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class DemoApplicationTest {
    private final Environment environment = mock(Environment.class);
    private final JerseyEnvironment jersey = mock(JerseyEnvironment.class);

    private final DemoApplication application = new DemoApplication();
    private final DemoConfiguration config = new DemoConfiguration();

    private final LifecycleEnvironment lifecycle = mock(LifecycleEnvironment.class);
    private final AdminEnvironment admin = mock(AdminEnvironment.class);
    private final HealthCheckRegistry healthCheckRegistry = mock(HealthCheckRegistry.class);

    @Before
    public void setup() throws Exception {
        when(environment.jersey()).thenReturn(jersey);
        when(environment.lifecycle()).thenReturn(lifecycle);
        when(environment.admin()).thenReturn(admin);
        when(environment.healthChecks()).thenReturn(healthCheckRegistry);
    }

    @Test
    public void buildsResources() throws Exception {
        application.run(config, environment);

        verify(jersey).register(Mockito.isA(PingResource.class));
        verify(jersey).register(Mockito.isA(KittenResource.class));
        verify(admin).addTask(Mockito.isA(ToggleHealthCheckTask.class));
        verify(lifecycle).manage(Mockito.isA(InMemoryStore.class));
        verify(healthCheckRegistry).register(Mockito.anyString(), Mockito.isA(InMemoryStoreHealthCheck.class));
    }
}
