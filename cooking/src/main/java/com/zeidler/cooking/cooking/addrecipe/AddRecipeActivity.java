package com.zeidler.cooking.cooking.addrecipe;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zeidler.cooking.cooking.R;
import com.zeidler.cooking.cooking.Recipe;
import com.zeidler.cooking.cooking.Step;
import com.zeidler.cooking.cooking.dbmanager.DataManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Colin on 2014-04-16.
 */
public class AddRecipeActivity extends Activity{

    private int stepCount;
    private static final int STEP_REQUEST_CODE = 1;
    private List<Step> steps;
    private LinearLayout stepLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_recipe);

        stepLayout = (LinearLayout) findViewById(R.id.step_list_layout);
        steps = new ArrayList<Step>();

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

        parent.addView(et, parent.getChildCount() - 1);

    }

    public void addStep(View v) {
        LinearLayout parent = (LinearLayout)v.getParent();

        //start edit Step intent with the Tag that is attached (Step)
        stepCount++;

        Intent intent = new Intent();
        intent.setClass(this, AddStepActivity.class);
        intent.putExtra("StepNum", stepCount);
        startActivityForResult(intent, STEP_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == STEP_REQUEST_CODE) {
            if (resultCode == RESULT_CANCELED) {
                stepCount--;
            }
            else if (resultCode == RESULT_OK) {
                //handle adding the new step to the list
                Step mStep = (Step) data.getSerializableExtra("NewStep");
                steps.add(mStep);

                addStep(steps.get(steps.size()-1));
                Log.v("Step List size", steps.size() + "");
            }
        }
    }

    private void addStep(Step mStep) {
        LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = vi.inflate(R.layout.step_list_item, null);
        //TODO add long click listener for the view, allow editing / deletion of Steps

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

                Recipe recipe = new Recipe(name, overview, steps, ingredients);

                DataManager db = DataManager.getInstance(this);
                db.addRecipe(recipe);

                finish();
                return true;
            case R.id.action_cancel:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
