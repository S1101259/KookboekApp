package nl.raymon.henk.kookbookapp.models;

import java.util.ArrayList;

public class Recipe {
    private ArrayList<CookingStep> cooking;
    private int cooking_time;
    private ArrayList<String> ingredients;
    private String name;
    private ArrayList<PreparationStep> preparation;
    private String serving;
    private String type;

    public Recipe() {

    }

    public Recipe(ArrayList<CookingStep> cooking, int cooking_time, ArrayList<String> ingredients, String name, ArrayList<PreparationStep> preparation, String serving, String type) {
        this.cooking = cooking;
        this.cooking_time = cooking_time;
        this.ingredients = ingredients;
        this.name = name;
        this.preparation = preparation;
        this.serving = serving;
        this.type = type;
    }

    public ArrayList<CookingStep> getCooking() {
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

    public ArrayList<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(ArrayList<String> ingredients) {
        this.ingredients = ingredients;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<PreparationStep> getPreparation() {
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
