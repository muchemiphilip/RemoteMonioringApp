package com.okotheffie.remotemonioringapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.okotheffie.remotemonioringapp.models.Task;

public final class AssignTaskActivity extends AppCompatActivity {

    private static final String TAG = "AssignTaskActivity";

    private TextInputLayout textInputLayoutEmployeeEmail;
    private TextInputLayout textInputLayoutTaskToComplete;

    private TextInputEditText mEmail;
    private TextInputEditText mTaskToComplete;
    private EditText mDescription;
    private TextView mTaskId;
    private TextView mDueDate;
    private Button mSetDueDate;
    private Button mAssignTask;

    String assignedTo, name, description, taskId, dueDate;

    DatabaseReference databaseReference;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assign_task_layout);

        initViews();

        mAssignTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Task task = new Task();

                getDataFromViews();

                // Adding name into class function object.
                task.setAssignedTo(assignedTo);
                task.setName(name);
                task.setDescription(description);
                task.setTaskId(taskId);
                task.setDueDate(dueDate);

                // Getting the ID from firebase database.
                String TaskRecordIDFromServer = databaseReference.push().getKey();

                // Adding the both name and number values using student details class object using ID.
                databaseReference.child(TaskRecordIDFromServer).setValue(task);

                // Showing Toast message after successfully data submit.
                Toast.makeText(AssignTaskActivity.this,"Data Inserted Successfully into Firebase Database", Toast.LENGTH_LONG).show();

            }
        });
    }



    public void initViews(){
        textInputLayoutEmployeeEmail = findViewById(R.id.employee_email_til);
        mEmail = findViewById(R.id.edit_text_employee_email);
        textInputLayoutTaskToComplete = findViewById(R.id.task_title_til);
        mTaskToComplete = findViewById(R.id.edit_text_task_to_complete);
        mDescription = findViewById(R.id.task_description_txv);
        mTaskId = findViewById(R.id.task_id_txv);
        mDueDate = findViewById(R.id.due_date_txv);
        mSetDueDate = findViewById(R.id.set_due_date_btn);
        mAssignTask = findViewById(R.id.assign_task_btn);
    }
    public void getDataFromViews(){
        assignedTo = mEmail.getText().toString().trim();
        name = mTaskToComplete.getText().toString().trim().toLowerCase();
        description = mDescription.getText().toString().trim().toLowerCase();
        taskId = mTaskId.getText().toString().trim().toLowerCase();
        dueDate = mDueDate.getText().toString();
    }
}
