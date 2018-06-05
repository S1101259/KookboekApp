package nl.raymon.henk.kookbookapp.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;

import com.google.firebase.database.PropertyName;

import java.io.Serializable;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(tableName = "preparationsteps", foreignKeys = @ForeignKey(entity = Recipe.class, parentColumns = "id", childColumns = "recipeID", onDelete = CASCADE))
public class PreparationStep implements Serializable{

    @ColumnInfo(name = "part")
    private String part;

    @ColumnInfo(name = "desc")
    private String description;

    @ColumnInfo(name = "recipeId")
    private int recipeID;

    public PreparationStep() {

    }

    public PreparationStep(String part, String description) {
        this.part = part;
        this.description = description;
    }

    public String getPart() {
        return part;
    }

    public void setPart(String part) {
        this.part = part;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
