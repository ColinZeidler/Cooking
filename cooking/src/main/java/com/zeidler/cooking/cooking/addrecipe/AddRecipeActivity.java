package com.zeidler.cooking.cooking.addrecipe;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
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
    private String name;
    private String overview;
    private List<String> ingredients;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_recipe);

        ActionBar ab = getActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        Button addIng = (Button) findViewById(R.id.er_add_ing_button);
        Button addStep = (Button) findViewById(R.id.er_add_step_button);

        addIng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout parent = (LinearLayout)v.getParent();
                EditText et = new EditText(parent.getContext());
                et.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT));


                parent.addView(et, parent.getChildCount()-1);
            }
        });

        addStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout parent = (LinearLayout)v.getParent();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        //TODO load any data from the database
    }

    @Override
    protected void onPause() {
        super.onPause();
        //TODO save current data to the database to maintain current edit
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
                //TODO save data to db as new Recipe and end this activity
                String name = ((EditText) findViewById(R.id.er_recipe_name)).getText().toString();
                String overview = ((EditText)
                        findViewById(R.id.er_recipe_overview)).getText().toString();

                List<String> ingredients = new ArrayList<String>();
                LinearLayout ingLayout = (LinearLayout) findViewById(R.id.layout_ingredients);
                for (int i = 1; i < ingLayout.getChildCount() -1; i++) {
                    //first and last items are not EditTexts, the rest are guaranteed to be
                    View child = ingLayout.getChildAt(i);
                    ingredients.add(((EditText) child).getText().toString());
                }

                return true;
            case R.id.action_cancel:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
