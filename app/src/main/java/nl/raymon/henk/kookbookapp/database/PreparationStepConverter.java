package nl.raymon.henk.kookbookapp.database;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import nl.raymon.henk.kookbookapp.models.PreparationStep;

public class PreparationStepConverter {

    @TypeConverter
    public String fromPreparationStepList(List<PreparationStep> preparationSteps){
        if(preparationSteps == null){
            return (null);
        }

        Gson gson = new Gson();
        Type type = new TypeToken<List<PreparationStep>>(){} .getType();
        return gson.toJson(preparationSteps, type);
    }


    @TypeConverter
    public List<PreparationStep> toPreparationStepList(String json){
        if (json == null ) {return  (null);}
        Gson gson = new Gson();
        Type type = new TypeToken<List<PreparationStep>>(){} .getType();
        return gson.fromJson(json, type);
    }
}
