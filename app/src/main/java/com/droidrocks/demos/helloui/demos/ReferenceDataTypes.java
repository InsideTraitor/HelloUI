package com.droidrocks.demos.helloui.demos;

import android.app.Activity;
import android.graphics.Color;

import java.util.Map;
import java.util.Random;


/**
 * Created by hollis on 5/6/18.
 */

public class ReferenceDataTypes {

    Random[] random;
    static int i = 0;
    InnerClassExample clazz;
    private String name = "Edisto";
    String[] foods;
    Map<String,Map<String,String>> hashMap;

    // Constructor
    public ReferenceDataTypes() {};

    public void setName(String name) {
        this.name = name;
    }

    public void setName(String name, boolean callDog) {
        this.name = name;

        if (callDog) {
            callDogByName(name);
        }
    }

    public void callDogByName(String name) {
        System.out.println("Here, boy! Come here, boy! Good " + name + "! Good dog!");
    }

    public void main(String[] args){
        clazz = new InnerClassExample();
        InnerClassExample clazz1 = clazz;
        clazz = new InnerClassExample();

        System.out.println(name); // Edisto
        setName("Casey");
        setName("Henry", false);
        System.out.println(name); // Henry

        foods = new String[10];
        try {
            foods[10] = "throws OutOfBoundsException";
            foods[9] = "1";
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        } finally {
            foods[foods.length -1] = "1";
            foods[9] = "1";
        }

        for (int i = 0; i < foods.length; i++) {
            System.out.println("Value of foods at index " + i + ": " + foods[i]);
        }
    }

    private class InnerClassExample extends ReferenceDataTypes{

        Random random;
        public InnerClassExample() {}

        public void main(String[] args){
            Animal dog = new Dog();
            Animal jabberwocky = new Jabberwocky();

            dog.eat(new Food());
        }
    }
}


