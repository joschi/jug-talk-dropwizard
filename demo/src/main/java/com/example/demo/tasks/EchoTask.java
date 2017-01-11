package com.example.demo.tasks;

import com.google.common.collect.ImmutableMultimap;
import io.dropwizard.servlets.tasks.Task;

import java.io.PrintWriter;

public class EchoTask extends Task {
    public EchoTask() {
        super("echo");
    }

    @Override
    public void execute(ImmutableMultimap<String, String> parameters,
                        PrintWriter output) throws Exception {
        output.println(parameters);
    }
}
