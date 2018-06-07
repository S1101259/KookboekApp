package nl.raymon.henk.kookbookapp.database;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import nl.raymon.henk.kookbookapp.models.CookingStep;

public class CookingStepConverter {

    @TypeConverter
    public String fromCookingStepList(List<CookingStep> cookingSteps){
        if(cookingSteps == null){
            return (null);
        }

        Gson gson = new Gson();
        Type type = new TypeToken<List<CookingStep>>(){} .getType();
        return gson.toJson(cookingSteps, type);
    }


    @TypeConverter
    public List<CookingStep> toCookingStepList(String json){
        if (json == null ) {return  (null);}
        Gson gson = new Gson();
        Type type = new TypeToken<List<CookingStep>>(){} .getType();
        return gson.fromJson(json, type);
    }


}
