package nl.raymon.henk.kookbookapp.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity(tableName="recipes")
public class Recipe implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @Ignore
    private List<CookingStep> cooking;

    @ColumnInfo(name = "time")
    private int cooking_time;

    @Ignore
    private List<String> ingredients;

    @ColumnInfo(name = "name")
    private String name;

    @Ignore
    private List<PreparationStep> preparation;

    @ColumnInfo(name = "serving")
    private String serving;

    @ColumnInfo(name = "type")
    private String type;

    public Recipe() {

    }

    public Recipe(int id, List<CookingStep> cooking, int cooking_time, List<String> ingredients, String name, List<PreparationStep> preparation, String serving, String type) {
        this.id= id;
        this.cooking = cooking;
        this.cooking_time = cooking_time;
        this.ingredients = ingredients;
        this.name = name;
        this.preparation = preparation;
        this.serving = serving;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<CookingStep> getCooking() {
        return cooking;
    }

    public void setCooking(ArrayList<CookingStep> cooking) {
        this.cooking = cooking;
    }

    public int getCooking_time() {
        return cooking_time;
    }

    public void setCooking_time(int cooking_time) {
        this.cooking_time = cooking_time;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<PreparationStep> getPreparation() {
        return preparation;
    }

    public void setPreparation(ArrayList<PreparationStep> preparation) {
        this.preparation = preparation;
    }

    public String getServing() {
        return serving;
    }

    public void setServing(String serving) {
        this.serving = serving;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
