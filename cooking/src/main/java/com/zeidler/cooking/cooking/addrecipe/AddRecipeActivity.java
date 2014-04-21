package com.zeidler.cooking.cooking.addrecipe;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.zeidler.cooking.cooking.R;
import com.zeidler.cooking.cooking.Recipe;
import com.zeidler.cooking.cooking.Step;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Colin on 2014-04-16.
 */
public class AddRecipeActivity extends Activity{

    private int stepCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_recipe);

        ActionBar ab = getActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        stepCount = 0;
    }

    public void addIngredient(View v) {
        LinearLayout parent = (LinearLayout)v.getParent();

        ViewGroup.LayoutParams layoutParams = parent.getChildAt(1).getLayoutParams();
        EditText et = new EditText(parent.getContext());
        et.setLayoutParams(layoutParams);
        et.setInputType(InputType.TYPE_TEXT_FLAG_AUTO_CORRECT);

        parent.addView(et, parent.getChildCount()-1);

    }

    public void addStep(View v) {
        LinearLayout parent = (LinearLayout)v.getParent();


    }

    public void editStep(View v) {
        //start edit Step intent with the Tag that is attached (Step)
        stepCount++;

        Intent intent = new Intent();
        intent.setClass(this, AddStepActivity.class);
        Bundle nBundle = intent.getExtras();
        nBundle.putInt("StepNum", stepCount);
        startActivity(intent);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.recipe_edit_actions, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.action_done:
                //Name of Recipe
                String name = ((EditText) findViewById(R.id.er_recipe_name)).getText().toString();

                //Overview for Recipe
                String overview = ((EditText)
                        findViewById(R.id.er_recipe_overview)).getText().toString();

                //Ingredients for Recipe
                List<String> ingredients = new ArrayList<String>();
                LinearLayout ingLayout = (LinearLayout) findViewById(R.id.layout_ingredients);
                for (int i = 1; i < ingLayout.getChildCount() -1; i++) {
                    //first and last items are not EditTexts, the rest are guaranteed to be
                    View child = ingLayout.getChildAt(i);
                    ingredients.add(((EditText) child).getText().toString());
                }

                //Steps for Recipe TODO get Steps for Recipe

                //save recipe in DB TODO save to db

                return true;
            case R.id.action_cancel:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
