package nl.raymon.henk.kookbookapp.models;

public class PreparationStep {
    private String part;
    private String description;

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
