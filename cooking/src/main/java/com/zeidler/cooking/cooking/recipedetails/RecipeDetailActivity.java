package com.zeidler.cooking.cooking.recipedetails;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;

/**
 * Created by Colin on 2014-04-16.
 */
public class RecipeDetailActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActionBar ab = getActionBar();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }
}
