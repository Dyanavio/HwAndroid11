package com.example.hwandroid11;

import static android.content.Intent.FLAG_ACTIVITY_REORDER_TO_FRONT;
import static android.widget.Toast.LENGTH_LONG;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class MainActivity extends AppCompatActivity implements RecyclerViewInterface {

    public final static String ID_KEY = "STUDENT_ID";
    public final static String FIRST_NAME_KEY = "STUDENT_FIRST_NAME";
    public final static String LAST_NAME_KEY = "STUDENT_LAST_NAME";
    public final static String AGE_KEY = "STUDENT_AGE";
    public final static int GONE = 410;

    private ActivityResultLauncher<Intent> studentLauncher;

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(MainActivity.this, StudentActivity.class);

        Student student = viewModel.getAllStudents().getValue().get(position);

        intent.putExtra(ID_KEY, student._id);
        intent.putExtra(FIRST_NAME_KEY, student.firstName);
        intent.putExtra(LAST_NAME_KEY, student.lastName);
        intent.putExtra(AGE_KEY, student.age);

        studentLauncher.launch(intent);
    }

    public static class StudentViewModel extends AndroidViewModel {
        private final StudentRepository repo;
        private final LiveData<List<Student>> allStudents;

        public StudentViewModel(android.app.Application app) {
            super(app);
            repo = new StudentRepository(app);
            allStudents = repo.getAllStudents();
        }

        public void update(Student s) { repo.update(s); }
        public LiveData<List<Student>> getAllStudents() { return allStudents; }
        public void insert(Student s) { repo.insert(s); }
        public void deleteAll() { repo.deleteAll(); }
        public void delete(long id) { repo.delete(id); }
    }

    private StudentViewModel viewModel;
    private final StudentAdapter adapter = new StudentAdapter(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText etFirstName = findViewById(R.id.etFirstName);
        EditText etLastName  = findViewById(R.id.etLastName);
        EditText etAge       = findViewById(R.id.etAge);
        Button btnAdd        = findViewById(R.id.btnAdd);
        Button btnClear      = findViewById(R.id.btnClear);
        RecyclerView rv      = findViewById(R.id.recyclerView);

        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(adapter);

        viewModel = new ViewModelProvider(
                this,
                ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication())
        ).get(StudentViewModel.class);

        viewModel.getAllStudents().observe(this, adapter::setStudents);

        btnAdd.setOnClickListener(v -> {
            String fn = etFirstName.getText().toString().trim();
            String ln = etLastName.getText().toString().trim();
            String ageStr = etAge.getText().toString().trim();
            if (!fn.isEmpty() && !ln.isEmpty() && !ageStr.isEmpty()) {
                int age = Integer.parseInt(ageStr);
                viewModel.insert(new Student(fn, ln, age));
                etFirstName.setText("");
                etLastName.setText("");
                etAge.setText("");
            }
        });

        btnClear.setOnClickListener(v -> viewModel.deleteAll());

        studentLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if(result.getResultCode() == RESULT_OK)
                {
                    Intent data = result.getData();
                    if(data != null)
                    {
                        long id = data.getLongExtra(ID_KEY, -1);
                        String firstName = data.getStringExtra(FIRST_NAME_KEY);
                        String lastName = data.getStringExtra(LAST_NAME_KEY);
                        int age = data.getIntExtra(AGE_KEY, -1);

                        if(id == -1 || age == -1)
                        {
                            Toast.makeText(getApplicationContext(), "Error", LENGTH_LONG).show();
                            return;
                        }

                        Student student = new Student(id, firstName ,lastName, age);
                        viewModel.update(student);

                        Toast.makeText(getApplicationContext(), "Updated", LENGTH_LONG).show();

                    }
                }
                else if(result.getResultCode() == GONE)
                {
                    Intent data = result.getData();
                    if(data != null)
                    {
                        long id = data.getLongExtra(ID_KEY, -1);
                        if(id == -1)
                        {
                            Toast.makeText(getApplicationContext(), "Error", LENGTH_LONG).show();
                            return;
                        }
                        viewModel.delete(id);
                        Toast.makeText(getApplicationContext(), "Deleted", LENGTH_LONG).show();

                    }
                }
                else if(result.getResultCode() == RESULT_CANCELED)
                {
                    Toast.makeText(getApplicationContext(), "Cancelled", LENGTH_LONG).show();
                }
            }
        });

    }
}