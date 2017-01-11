package com.example.demo;

import com.example.demo.model.Kitten;
import io.dropwizard.lifecycle.Managed;

import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicBoolean;

public class InMemoryStore implements Managed {
    private final ConcurrentMap<String, Kitten> internalStore = new ConcurrentHashMap<>();
    private AtomicBoolean running = new AtomicBoolean(false);

    public Optional<Kitten> get(final String name) {
        return Optional.ofNullable(internalStore.get(name));
    }

    public void put(final Kitten kitten) {
        internalStore.put(kitten.getName(), kitten);
    }

    @Override
    public void start() throws Exception {
        running.set(true);
    }

    @Override
    public void stop() throws Exception {
        running.set(false);
    }

    public boolean isRunning() {
        return running.get();
    }

    public Collection<Kitten> getAll() {
        return internalStore.values();
    }
}
