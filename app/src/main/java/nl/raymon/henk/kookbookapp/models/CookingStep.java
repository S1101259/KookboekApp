package nl.raymon.henk.kookbookapp.models;

import java.io.Serializable;

public class CookingStep implements Serializable {

    private String step;

    private String description;

    public CookingStep() {

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
