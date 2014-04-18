package com.zeidler.cooking.cooking;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.zeidler.cooking.cooking.addrecipe.AddRecipeActivity;
import com.zeidler.cooking.cooking.dbmanager.DataManager;

import java.util.ArrayList;
import java.util.List;

public class RecipeListView extends Activity {

    private DataManager dbManager;
    private RecipeListAdapter arrAdapter;
    private List<Recipe> recipes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);

        dbManager = new DataManager(this);
        recipes = dbManager.getRecipes();

        arrAdapter = new RecipeListAdapter(this, R.layout.recipe_list_item,
                (ArrayList)recipes);

        ListView lView = (ListView) findViewById(R.id.recipeList);
        lView.setAdapter(arrAdapter);

        lView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //TODO launch a new Activity
                Recipe r = (Recipe)view.getTag();
            }
        });

        lView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                return false;
            }
        });


        ActionBar actionbar = getActionBar();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.recipe_list_actions, menu);
        return super.onCreateOptionsMenu(menu);
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                Log.i("Recipe List", "Add button clicked");
                Intent intent = new Intent();
                intent.setClass(this, AddRecipeActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_testItems:
                createTestItems();
                return true;
            case R.id.action_delete_all:
                deleteAllItems();
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    private void deleteAllItems() {

        final List<Recipe> rl = new ArrayList<Recipe>(recipes);
        arrAdapter.clear();
        arrAdapter.notifyDataSetChanged();

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (Recipe r : rl) {
                    dbManager.deleteRecipe(r);
                }

                Log.v("Thread2", "done deleting items");
            }
        }).start();

        Log.v("Thread1", "new Thread is running");
    }

    private void createTestItems() {

        List<Recipe> recipes;

        //START OF TEST INFO
        Step s1, s2, s3;
        s1 = new Step(1, "put the box in", 0);
        s2 = new Step(2, "cook for 30 seconds", 30);
        s3 = new Step(3, "wait for 1 minute", 60);
        ArrayList<Step> steps = new ArrayList<Step>();

        steps.add(s1);
        steps.add(s2);
        steps.add(s3);

        ArrayList<String> ing = new ArrayList<String>();
        ing.add("Cookie mix");
        ing.add("Chocolate");

        recipes = new ArrayList<Recipe>();
        recipes.add(new Recipe("Cookies", "", steps, ing));
//        recipes.add(new Recipe("Pork chops", "", steps, ing));
//        recipes.add(new Recipe("Steak on the Grill", "", steps, ing));
//        recipes.add(new Recipe("rice", "", steps, ing));
//        recipes.add(new Recipe("Chicken Wings", "", steps, ing));
//        recipes.add(new Recipe("Cereal", "", steps, ing));
//        recipes.add(new Recipe("Brownies", "", steps, ing));
//        recipes.add(new Recipe("Tacos", "", steps, ing));
//        recipes.add(new Recipe("Fajitas", "", steps, ing));
        //END OF TEST INFO

        for (Recipe r : recipes) {
            dbManager.addRecipe(r);
        }

        arrAdapter.clear();
        arrAdapter.addAll(dbManager.getRecipes());
        arrAdapter.notifyDataSetChanged();
    }
}
