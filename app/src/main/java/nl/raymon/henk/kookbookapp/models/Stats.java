package nl.raymon.henk.kookbookapp.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;


@Entity(tableName= "stats")
public class Stats {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name="date")
    private String date;

    public Stats(){}

    @Ignore
    public Stats(String date) {
        this.date = date;
    }

    @Ignore
    public Stats(String date, int id) {
        this.date = date;
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
