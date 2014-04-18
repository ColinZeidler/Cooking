package com.zeidler.cooking.cooking;

import java.util.List;
import java.util.Random;

/**
 * Created by Colin on 2014-04-13.
 */
public class Recipe implements Comparable<Recipe> {
    private String name;
    private String overview;
    private List<Step> steps;
    private List<String> ingredients;
    private long uID;   //will be set when added to database first time
    private boolean uIDLock = false; //prevents modification of the uID after it is set

    public Recipe(String n, String o, List<Step> s, List<String> i) {
        this.name = n;
        this.overview = o;
        this.steps = s;
        this.ingredients = i;

        setuID((new Random()).nextLong());
    }

    public Recipe(String n, String o, List<Step> s, List<String> i, long id) {
        this.name = n;
        this.overview = o;
        this.steps = s;
        this.ingredients = i;

        setuID(id);
    }

    public String getName() { return name; }
    public String getOverview() { return overview; }
    public List<Step> getSteps() { return steps; }
    public List<String> getIngredients() { return ingredients; }
    public long getuID() {return uID; }

    public void addStep(Step newStep) {
        steps.add(newStep);
    }

    public void setSteps(List<Step> newSteps) {
        steps = newSteps;
    }

    public void setName(String newName) {
        name = newName;
    }

    public void addIngredient(String i) {
        ingredients.add(i);
    }

    public void setIngredients(List<String> newIng) {
        ingredients = newIng;
    }

    public void setuID(long l) {
        if (!uIDLock) { //only change if not already set
            uID = l;
            uIDLock = true;
        }
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    @Override
    public int compareTo(Recipe another) {
        return name.compareTo(another.getName());
    }
}
