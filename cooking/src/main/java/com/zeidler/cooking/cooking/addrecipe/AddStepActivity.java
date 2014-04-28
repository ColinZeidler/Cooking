package com.zeidler.cooking.cooking.addrecipe;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;

import com.zeidler.cooking.cooking.R;
import com.zeidler.cooking.cooking.Step;

/**
 * Created by Colin on 2014-04-18.
 */
public class AddStepActivity extends Activity {
    private int stepNum;
    private boolean timer = false;

    private EditText mText, sText, iText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_step);

        stepNum = getIntent().getExtras().getInt("StepNum");

        ActionBar ab = getActionBar();
        Log.v("Edit Step", "stepNum= " + stepNum);

        ab.setTitle("Step: " + stepNum);
        mText = (EditText) findViewById(R.id.es_timer_minutes);
        sText = (EditText) findViewById(R.id.es_timer_seconds);
        iText = (EditText) findViewById(R.id.es_step_instruct);

        //starts off as off so start disabled
        mText.setEnabled(false);
        sText.setEnabled(false);

        //switch to toggle the timer on/off
        Switch tSwitch = (Switch) findViewById(R.id.es_timer_switch);
        tSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    //on
                    timer = true;
                    mText.setEnabled(true);
                    sText.setEnabled(true);
                } else {
                    //off
                    timer = false;
                    mText.setEnabled(false);
                    sText.setEnabled(false);
                }

            }
        });
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
                Log.v("EditStep", "Cancel Clicked");
                setResult(RESULT_CANCELED);
                finish();
                return true;
            case R.id.action_done:
                Log.v("EditStep", "Done clicked");
                //create the step from all entered data, and pack it to send to the parent activity
                long timerSeconds;
                if (timer) {
                    timerSeconds= Long.parseLong(mText.getText().toString()) * 60; //minutes to seconds
                    timerSeconds += Long.parseLong(sText.getText().toString()); //add the seconds
                } else {
                    timerSeconds = 0;
                }

                Log.v("TIMER", "Time= " + timerSeconds);

                Intent data = new Intent();
                data.putExtra("NewStep", new Step(stepNum, iText.getText().toString(), timerSeconds));
                setResult(RESULT_OK, data);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
