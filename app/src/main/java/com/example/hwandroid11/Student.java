package com.example.hwandroid11;

import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.*;
import androidx.annotation.NonNull;
import androidx.lifecycle.*;
import androidx.room.*;
import java.util.*;

@Entity(tableName = "students")
class Student
{
    @PrimaryKey(autoGenerate = true)
    public long _id;
    public String firstName;
    public String lastName;
    public int age;

    public Student() {}
    public Student(String firstName, String lastName, int age)
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }

    public Student(long id, String firstName, String lastName, int age)
    {
        this._id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }

    @NonNull
    @Override
    public String toString()
    {
        return firstName + " " + lastName + ", вік: " + age;
    }
}