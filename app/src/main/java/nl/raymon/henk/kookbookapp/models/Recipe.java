package nl.raymon.henk.kookbookapp.models;

import java.util.ArrayList;
import java.util.List;

public class Recipe {
    private List<CookingStep> cooking;
    private int cooking_time;
    private List<String> ingredients;
    private String name;
    private List<PreparationStep> preparation;
    private String serving;
    private String type;

    public Recipe() {

    }

    public Recipe(List<CookingStep> cooking, int cooking_time, List<String> ingredients, String name, List<PreparationStep> preparation, String serving, String type) {
        this.cooking = cooking;
        this.cooking_time = cooking_time;
        this.ingredients = ingredients;
        this.name = name;
        this.preparation = preparation;
        this.serving = serving;
        this.type = type;
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
