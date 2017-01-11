package com.example.demo.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.hibernate.validator.constraints.NotEmpty;
import org.immutables.value.Value;

import javax.annotation.Nullable;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Value.Immutable
@JsonSerialize(as = com.example.demo.model.ImmutableKitten.class)
@JsonDeserialize(as = com.example.demo.model.ImmutableKitten.class)
public interface Kitten {
    enum KittenType {
        CUTE, FLUFFY, GRUMPY
    }

    @Min(value = 0, message = "ID must be positive")
    int getId();

    @NotEmpty
    String getName();

    @NotNull
    KittenType getType();

    @Nullable
    String getImagePath();
}
