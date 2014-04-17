package com.zeidler.cooking.cooking.dbmanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.zeidler.cooking.cooking.Recipe;
import com.zeidler.cooking.cooking.Step;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Colin on 2014-04-16.
 */
public class DataManager extends SQLiteOpenHelper{

    public static final int    DATABASE_VERSION = 1;
    public static final String  DATABASE_NAME   = "cookbook";

    private static final String S_TABLENAME     = "steps";
    //Steps Table in this order
    private static final String S_KEY           = "id";
    private static final String S_NUMBER        = "number";
    private static final String S_INSTRUCT      = "instructions";
    private static final String S_TIMER         = "timer length";

    private static final String R_TABLENAME     = "recipes";
    //Recipes Table in this order
    private static final String R_KEY           = "id";
    private static final String R_NAME          = "name";
    private static final String R_OVERVIEW      = "overview";
    private static final String R_INGREDIENTS   = "ingredients";
    private static final String R_STEPS         = "msteps";

    private static final String DELIM           = "*&*";

    private Random rand;

    public DataManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        rand = new Random();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_STEPS_TABLE = "CREATE TABLE " + S_TABLENAME + "("
                + S_KEY + " INTEGER PRIMARY KEY," + S_NUMBER + " INTEGER,"
                + S_INSTRUCT + " TEXT," + S_TIMER + " INTEGER" + ")";

        String CREATE_RECIPE_TABLE = "CREATE TABLE " + R_TABLENAME + "("
                + R_KEY + " INTEGER PRIMARY KEY," + R_NAME + " TEXT,"
                + R_OVERVIEW + " TEXT," + R_INGREDIENTS + " TEXT,"
                + R_STEPS + "TEXT" + ")";

        db.execSQL(CREATE_STEPS_TABLE);
        db.execSQL(CREATE_RECIPE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + R_TABLENAME);
        db.execSQL("DROP TABLE IF EXISTS " + S_TABLENAME);

        onCreate(db);
    }

    public void addStep(Step step) {
        step.setuID(rand.nextLong());

        ContentValues values = new ContentValues();
        values.put(S_KEY, step.getuID());
        values.put(S_NUMBER, step.getNumber());
        values.put(S_INSTRUCT, step.getInstruct());
        values.put(S_TIMER, step.getTimer());

        //insert row
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(S_TABLENAME, null, values);
        db.close();
    }

    public void addRecipe(Recipe recipe) {
        recipe.setuID(rand.nextLong());
        ContentValues values = new ContentValues();

        //Creating string containing uIDs of all Steps
        int count = 0;
        String csSteps = "";
        for (Step s : recipe.getSteps()) {
            addStep(s);
            if (count !=0) {
                csSteps += DELIM;
            }
            csSteps += s.getuID();
            count++;
        }

        //Creating string containing all ingredients
        count = 0;
        String csIng = "";
        for (String s : recipe.getIngredients()) {
            if (count !=0) {
                csIng += DELIM;
            }
            csIng += s;
            count++;
        }

        values.put(R_KEY, recipe.getuID());
        values.put(R_NAME, recipe.getName());
        values.put(R_OVERVIEW, recipe.getOverview());
        values.put(R_INGREDIENTS, csIng);
        values.put(R_STEPS, csSteps);

        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(R_TABLENAME, null, values);
        db.close();
    }

    public void updateStep(Step step) {

    }

    public void updateRecipe(Recipe recipe) {

    }

    public List<Recipe> getRecipes() {
        List<Recipe> rList = new ArrayList<Recipe>();

        String selectQuery = "SELECT * FROM " + R_TABLENAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        db.close();

        if (cursor.moveToFirst()) {
            do {
                String[] temp = cursor.getString(3).split(DELIM);
                List<String> ings = new ArrayList<String>(temp.length);
                for (int i = 0; i < temp.length; i ++) {
                    ings.add(temp[i]);
                }

                temp = cursor.getString(4).split(DELIM);
                List<Step> steps = new ArrayList<Step>(temp.length);
                for (int i = 0; i < temp.length; i++) {
                    long l = Long.parseLong(temp[i]);
                    steps.add(getStep(l));
                }
                Recipe recipe = new Recipe(cursor.getString(1), cursor.getString(2),
                        steps, ings);
                rList.add(recipe);
            }while(cursor.moveToNext());
        }

        return rList;
    }

    public Step getStep(long uID) {
        return new Step(0, "", 0);
    }

    public List<Step> getSteps() {  //Probably won't ever be used
        return new ArrayList<Step>();
    }

    public void deleteStep(Step s) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(S_TABLENAME, S_KEY + " = ?", new String[] {String.valueOf(s.getuID())});
        db.close();
    }
    public void deleteRecipe(Recipe r) {
        for (Step s: r.getSteps()) {
            deleteStep(s);
        }

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(R_TABLENAME, R_KEY + " = ?", new String[] {String.valueOf(r.getuID())});
        db.close();
    }
}
