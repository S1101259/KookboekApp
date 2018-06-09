package nl.raymon.henk.kookbookapp.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;


import nl.raymon.henk.kookbookapp.models.Stats;

@Dao
public interface StatsDao {

    @Insert
    public void insertStat(Stats stats);

    @Query("SELECT COUNT(*) FROM stats WHERE date LIKE :date")
    public int countStats(String date);

    @Query("SELECT COUNT(*) FROM stats")
    public int countAllStats();
}
