package nl.raymon.henk.kookbookapp.database;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class IngredientsConverter {
    @TypeConverter
    public String fromIngredientsList(List<String> ingredients){
        if(ingredients == null){
            return (null);
        }

        Gson gson = new Gson();
        Type type = new TypeToken<List<String>>(){} .getType();
        return gson.toJson(ingredients, type);
    }


    @TypeConverter
    public List<String> toIngredientsList(String json){
        if (json == null ) {return  (null);}
        Gson gson = new Gson();
        Type type = new TypeToken<List<String>>(){} .getType();
        return gson.fromJson(json, type);
    }
}
