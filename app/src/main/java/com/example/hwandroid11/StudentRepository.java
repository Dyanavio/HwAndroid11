package com.example.hwandroid11;

import androidx.lifecycle.LiveData;
import androidx.room.Update;

import java.util.List;

class StudentRepository
{
    private final StudentDao dao;
    private final LiveData<List<Student>> allStudents;

    StudentRepository(android.app.Application app)
    {
        AppDatabase db = AppDatabase.getDatabase(app);
        dao = db.studentDao();
        allStudents = dao.getAllStudents();
    }

    void update(Student s) { new Thread(() -> dao.update(s)).start(); }
    LiveData<List<Student>> getAllStudents() { return allStudents; }
    void insert(Student s) { new Thread(() -> dao.insert(s)).start(); }
    void deleteAll() { new Thread(dao::deleteAll).start(); }
    void delete(long id) { new Thread(() -> dao.delete(id)).start(); }
}