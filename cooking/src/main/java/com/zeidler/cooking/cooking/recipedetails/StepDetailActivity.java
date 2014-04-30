package com.zeidler.cooking.cooking.recipedetails;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.zeidler.cooking.cooking.R;
import com.zeidler.cooking.cooking.Step;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Colin on 2014-04-30.
 */
public class StepDetailActivity extends Activity {
    private Context mContext;
    private List<Step> mSteps;
    private int mPos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.id.activity_step_detail);

        mContext = this;

        Bundle extras = getIntent().getExtras();
        mSteps = (ArrayList<Step>) extras.get("steps");
        mPos = (Integer) extras.get("pos");

        ActionBar ab = getActionBar();

        if (ab != null) {
            ab.setTitle("Test");

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.step_detail_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_next:
                return true;
            case R.id.action_prev:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
