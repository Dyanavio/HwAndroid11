package com.example.hwandroid11;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
interface StudentDao {
    @Insert
    void insert(Student student);
    @Update
    void update(Student s);
    @Query("SELECT * FROM students ORDER BY lastName")
    LiveData<List<Student>> getAllStudents();
    @Query("DELETE FROM students") void deleteAll();
    @Query("Delete from students where _id == :id")
    void delete(long id);
}
