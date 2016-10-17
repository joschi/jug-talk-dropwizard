package com.example.demo.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

@Value.Immutable
@JsonSerialize(as = com.example.demo.model.ImmutablePong.class)
@JsonDeserialize(as = com.example.demo.model.ImmutablePong.class)
public interface Pong {
    String message();
}
