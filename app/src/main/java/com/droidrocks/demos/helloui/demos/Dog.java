package com.droidrocks.demos.helloui.demos;

import android.graphics.Color;

/**
 * Created by hollis on 5/6/18.
 */

public class Dog extends Animal {

    private Color color;
    private enum Breed {COLLIE, GERMAN_SHEPARD, WEIMARANER, DACHSUND};
    private Breed breed;
    private String name;

    public Dog(){};

    @Override
    public Poop eat(Food food) {
        return null;
    }

    public void sit() {
        // Make dog sit
    }

    public Color getColor() {
        return this.color;
    }

    public Breed getBreed() {
        return this.breed;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
