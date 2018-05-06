package com.droidrocks.demos.helloui.demos;

/**
 * Created by hollis on 5/6/18.
 */

public class Jabberwocky extends Animal {
    @Override
    public Poop eat(Food food) {
        Poop poop = new Poop(food);
        return poop;
    }
}