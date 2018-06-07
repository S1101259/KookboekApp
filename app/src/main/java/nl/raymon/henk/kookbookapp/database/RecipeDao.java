package nl.raymon.henk.kookbookapp.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import java.util.List;

import nl.raymon.henk.kookbookapp.models.Recipe;

@Dao
public interface RecipeDao {
    @Delete
    void delete(Recipe recipe);

    @Query("SELECT * FROM recipes")
    List<Recipe> getAll();

    @Insert
    void insertRecipe(Recipe recipe);
}
