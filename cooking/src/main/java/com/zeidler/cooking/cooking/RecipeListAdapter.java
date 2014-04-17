package com.zeidler.cooking.cooking;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Colin on 2014-04-15.
 */
public class RecipeListAdapter extends ArrayAdapter<Recipe> {

    private int layoutId;
    private ArrayList<Recipe> recipes;
    private Context mContext;

    public RecipeListAdapter(Context context, int layoutId, ArrayList<Recipe> recipes) {
        super(context, layoutId, recipes);
        this.layoutId = layoutId;
        this.recipes = recipes;
        this.mContext = context;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //convertView is used to save memory in the application
        View v = convertView;
        if (v == null) {    //if there is not an existing view, create one
            LayoutInflater vi = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(layoutId, null);
        }

        Recipe recipe = recipes.get(position);
        if (recipe != null) {   //fill out the info of the view
            TextView primary = (TextView)v.findViewById(R.id.rl_primary_text);
            TextView secondary = (TextView)v.findViewById(R.id.rl_secondary_text);
            ImageView iView = (ImageView)v.findViewById(R.id.rl_image_view);

            primary.setText(recipe.getName());
            secondary.setText("Number of steps: " + recipe.getSteps().size());

            v.setTag(recipe);

            //choose what colour to use
            if ((position + 1) %3 == 0)
                iView.setBackgroundColor(mContext.getResources().getColor(R.color.red));
            else if ((position + 1) %2 == 0)
                iView.setBackgroundColor(mContext.getResources().getColor(R.color.blue));
            else
                iView.setBackgroundColor(mContext.getResources().getColor(R.color.green));

        }

        return v;
    }

}
