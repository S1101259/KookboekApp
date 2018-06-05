package nl.raymon.henk.kookbookapp.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;

import java.io.Serializable;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(tableName = "cookingsteps", foreignKeys = @ForeignKey(entity = Recipe.class, parentColumns = "id", childColumns = "recipeID", onDelete = CASCADE))
public class CookingStep implements Serializable {

    @ColumnInfo(name = "step")
    private String step;

    @ColumnInfo(name = "desc")
    private String description;

    @ColumnInfo(name = "recipeId")
    private int recipeId;

    public CookingStep() {

    }

    public CookingStep(String step, String description, int recipeId){
        this.step = step;
        this.description = description;
        this.recipeId = recipeId;
    }

    public CookingStep(String step, String description) {
        this.step = step;
        this.description = description;
    }

    public String getStep() {
        return step;
    }

    public void setStep(String step) {
        this.step = step;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
