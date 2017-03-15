package com.example.demo.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;
import org.immutables.value.Value;

import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;

@Value.Immutable
@JsonSerialize(as = com.example.demo.model.ImmutableKitten.class)
@JsonDeserialize(as = com.example.demo.model.ImmutableKitten.class)
public interface Kitten {
    enum KittenType {
        CUTE, FLUFFY, GRUMPY
    }

    @NotEmpty
    String getName();

    @Range(min = 0L, max = 40L, message = "Age must be between 0 and 40")
    int getAge();

    @NotNull
    KittenType getType();

    @Nullable
    String getImagePath();
}
