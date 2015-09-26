package com.android.desafioaudionews.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ferjuarez on 25/09/15.
 */

@DatabaseTable(tableName = "category")
public class Category {
    @DatabaseField(id = true)
    public Integer id;

    @DatabaseField
    public String valor;

    public Category(){
        // needed by ormlite
    }

    public static List<Category> parseCategories(JSONArray categoriesJSONArray) {
        List<Category> categories = new ArrayList<>();

        for(int i = 0; i < categoriesJSONArray.length(); i++){
            try {
                categories.add(Category.parseCategory(categoriesJSONArray.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return categories;
     }

    public static Category parseCategory(JSONObject categoryJSONObj) {
            Category category = new Category();
            try {
                category.id = categoryJSONObj.getInt("id");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                category.valor = categoryJSONObj.getString("valor");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return category;
    }

}
