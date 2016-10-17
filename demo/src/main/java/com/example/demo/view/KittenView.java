package com.example.demo.view;

import com.example.demo.model.Kitten;
import io.dropwizard.views.View;

public class KittenView extends View {
    private final Kitten kitten;

    public KittenView(Kitten kitten) {
        super("kitten.mustache");
        this.kitten = kitten;
    }

    public Kitten getKitten() {
        return kitten;
    }
}
