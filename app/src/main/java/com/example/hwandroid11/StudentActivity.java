package com.example.hwandroid11;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class StudentActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_student);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Intent intent = getIntent();
        long id = intent.getLongExtra(MainActivity.ID_KEY, -1);
        String firstName = intent.getStringExtra(MainActivity.FIRST_NAME_KEY);
        String lastName = intent.getStringExtra(MainActivity.LAST_NAME_KEY);
        int age = intent.getIntExtra(MainActivity.AGE_KEY, -1);

        TextView studentDisplayNameTextView = findViewById(R.id.studentDisplayNameTextView);
        EditText studentFirstNameEditText = findViewById(R.id.studentFirstNameEditText);
        EditText studentLastNameEditText = findViewById(R.id.studentLastNameEditText);
        EditText studentAgeEditText = findViewById(R.id.studentAgeEditText);

        studentDisplayNameTextView.setText(firstName + " " + lastName);
        studentFirstNameEditText.setText(firstName);
        studentLastNameEditText.setText(lastName);
        studentAgeEditText.setText(String.valueOf(age));

        findViewById(R.id.editStudentButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newFirstName = studentFirstNameEditText.getText().toString();
                String newLastName = studentLastNameEditText.getText().toString();
                String newAge = studentAgeEditText.getText().toString();

                Intent resultIntent = new Intent();
                resultIntent.putExtra(MainActivity.ID_KEY, id);
                resultIntent.putExtra(MainActivity.FIRST_NAME_KEY, newFirstName);
                resultIntent.putExtra(MainActivity.LAST_NAME_KEY, newLastName);
                resultIntent.putExtra(MainActivity.AGE_KEY, Integer.valueOf(newAge));
                setResult(RESULT_OK, resultIntent);

                finish();

            }
        });

        findViewById(R.id.deleteStudentButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent resultIntent = new Intent();
                if(id == -1)
                {
                    setResult(RESULT_CANCELED, resultIntent);
                    finish();
                }
                resultIntent.putExtra(MainActivity.ID_KEY, id);
                setResult(MainActivity.GONE, resultIntent);

                finish();
            }
        });

    }
}