package com.example.demo.tasks;

import com.example.demo.health.DemoHealthCheck;
import com.google.common.collect.ImmutableMultimap;
import io.dropwizard.servlets.tasks.Task;

import java.io.PrintWriter;

import static java.util.Objects.requireNonNull;

public class ToggleHealthCheckTask extends Task {
    private final DemoHealthCheck healthCheck;

    public ToggleHealthCheckTask(String name, DemoHealthCheck healthCheck) {
        super(name);
        this.healthCheck = requireNonNull(healthCheck);
    }

    @Override
    public void execute(ImmutableMultimap<String, String> parameters,
                        PrintWriter output) throws Exception {
        healthCheck.toggleHealth();
        output.println("Health state toggled.");
    }
}
