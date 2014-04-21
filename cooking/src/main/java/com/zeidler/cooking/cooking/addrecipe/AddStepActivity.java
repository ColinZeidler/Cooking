package com.zeidler.cooking.cooking.addrecipe;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.zeidler.cooking.cooking.R;

/**
 * Created by Colin on 2014-04-18.
 */
public class AddStepActivity extends Activity {
    private int stepNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_step);

        stepNum = getIntent().getExtras().getInt("StepNum");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.recipe_edit_actions, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_cancel:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
