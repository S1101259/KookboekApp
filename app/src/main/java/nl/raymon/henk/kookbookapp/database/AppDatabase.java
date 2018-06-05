package nl.raymon.henk.kookbookapp.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import nl.raymon.henk.kookbookapp.models.Recipe;

@Database(entities = {Recipe.class}, version = 1)
public abstract class AppDatabase  extends RoomDatabase{

    private static AppDatabase appDatabase;

    public static AppDatabase getInstance(Context context) {
        if(appDatabase == null) {
            appDatabase = Room.databaseBuilder(context, AppDatabase.class, "recipes").allowMainThreadQueries().fallbackToDestructiveMigration().build();
        }
        return appDatabase;
    }

    public abstract RecipeDao recipeDao();
    public abstract CookingStepDao cookingStepDao();
    public abstract PreparationStepDao preparationStepDao();
}
