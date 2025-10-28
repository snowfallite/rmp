package com.example.my_app.data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;

@Dao
public interface PersonDao {

    @Insert
    void insert(Person person);

    @Query("SELECT * FROM people")
    List<Person> getAll();
}
