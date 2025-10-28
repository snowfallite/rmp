package com.example.my_app.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "people")
public class Person {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public String lastName;
    public String firstName;
    public int age;

    public Person(String lastName, String firstName, int age) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.age = age;
    }
}
