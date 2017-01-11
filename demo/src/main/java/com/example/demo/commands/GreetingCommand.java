package com.example.demo.commands;

import com.example.demo.DemoConfiguration;
import io.dropwizard.cli.ConfiguredCommand;
import io.dropwizard.setup.Bootstrap;
import net.sourceforge.argparse4j.inf.Namespace;

import java.util.Locale;

public class GreetingCommand extends ConfiguredCommand<DemoConfiguration> {
    public GreetingCommand(String name, String description) {
        super(name, description);
    }

    @Override
    protected void run(Bootstrap<DemoConfiguration> bootstrap,
                       Namespace namespace,
                       DemoConfiguration configuration) throws Exception {
        System.out.format(
                Locale.ENGLISH,
                "Hi! This demo is presented by %s in %d.\n",
                configuration.getSpeakerName(),
                configuration.getYear());
    }
}
