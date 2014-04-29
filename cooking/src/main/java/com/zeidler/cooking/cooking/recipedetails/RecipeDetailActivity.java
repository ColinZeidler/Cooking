package com.zeidler.cooking.cooking.recipedetails;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zeidler.cooking.cooking.R;
import com.zeidler.cooking.cooking.Recipe;
import com.zeidler.cooking.cooking.Step;

/**
 * Created by Colin on 2014-04-16.
 */
public class RecipeDetailActivity extends Activity {
    private Recipe recipe;
    private LinearLayout stepLayout, ingLayout;
    private Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        mContext = this;
        recipe = (Recipe) getIntent().getExtras().getSerializable("Recipe");

        ActionBar ab = getActionBar();

        ab.setTitle(recipe.getName());
        ab.setDisplayHomeAsUpEnabled(true);

        stepLayout = (LinearLayout) findViewById(R.id.r_step_list);
        ingLayout = (LinearLayout) findViewById(R.id.r_ingredient_list);

        TextView overview = (TextView) findViewById(R.id.r_overview);
        overview.setText(recipe.getOverview());

        for (Step s : recipe.getSteps()) {
            addStep(s);
        }
        for (String i : recipe.getIngredients()) {
            addIng(i);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void addStep(Step mStep) {
        LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = vi.inflate(R.layout.step_list_item, null);

        TextView num = (TextView) v.findViewById(R.id.step_num);
        TextView iOverview = (TextView) v.findViewById(R.id.step_i_overview);
        TextView timer = (TextView) v.findViewById(R.id.step_timer);

        num.setText(mStep.getNumber() + ".");
        String instructions;
        if (mStep.getInstruct().length() > 20) {
            instructions = mStep.getInstruct().substring(0, 20) + "...";
        } else {
            instructions = mStep.getInstruct();
        }
        instructions = instructions.replace("\n", " ");
        iOverview.setText(instructions);

        String time;
        if (mStep.getTimer() == 0) {
            //no timer set
            time = "No Timer";
        } else {
            time = (mStep.getTimer() / 60) + " minutes";
        }
        timer.setText(time);
        stepLayout.addView(v, stepLayout.getChildCount());
    }

    private void addIng(String ingredient) {
        View v = new TextView(mContext);
        ((TextView) v).setText(ingredient);
        v.setLayoutParams(ingLayout.getChildAt(0).getLayoutParams());
        ingLayout.addView(v, ingLayout.getChildCount());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.recipe_detail_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_start:
                return true;
            case R.id.action_exit:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
