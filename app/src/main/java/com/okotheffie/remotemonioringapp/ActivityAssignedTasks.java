package com.okotheffie.remotemonioringapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;
import com.okotheffie.remotemonioringapp.adapters.TaskAdapter;
import com.okotheffie.remotemonioringapp.models.Task;

import java.util.ArrayList;
import java.util.List;

public final class ActivityAssignedTasks extends AppCompatActivity {

    private static final String TAG = "RegisterActivity";
    private DatabaseReference mTaskDatabase;
    private DatabaseReference mUsersDatabase;
    private String mCurrent_user_id;
    private FirebaseAuth mAuth;

    public static final String Database_Path = "tasks";
    ProgressDialog progressDialog;
    List<Task> list = new ArrayList<>();
    RecyclerView taskRecycler;
    TaskAdapter adapter ;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assigned_tasks_layout);
        Toolbar toolbar = findViewById(R.id.tasks_toolbar);
        setSupportActionBar(toolbar);
        Log.d(TAG, "onCreate ActivityAssignedTasks: started.");

        mTaskDatabase = FirebaseDatabase.getInstance().getReference(Database_Path);

        taskRecycler = (RecyclerView) findViewById(R.id.task_rv);
        taskRecycler.setHasFixedSize(true);
        taskRecycler.setLayoutManager(new LinearLayoutManager(ActivityAssignedTasks.this));
        progressDialog = new ProgressDialog(ActivityAssignedTasks.this);
        progressDialog.setMessage("Loading Data from Firebase Database");
        progressDialog.show();

        mTaskDatabase = FirebaseDatabase.getInstance().getReference(this.Database_Path);

        mTaskDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    Task task = dataSnapshot.getValue(Task.class);

                    list.add(task);
                }

                adapter = new TaskAdapter(ActivityAssignedTasks.this, list);

                taskRecycler.setAdapter(adapter);

                progressDialog.dismiss();

                //--GETTING CURRENT USER ID---
                mAuth= FirebaseAuth.getInstance();
                mCurrent_user_id = mAuth.getCurrentUser().getUid();

                //---OFFLINE FEATURE---
                mTaskDatabase.keepSynced(true);
                mUsersDatabase=FirebaseDatabase.getInstance().getReference().child("users");
                mUsersDatabase.keepSynced(true);
                mTaskDatabase = FirebaseDatabase.getInstance().getReference().child("tasks");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                progressDialog.dismiss();
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu_assign_task, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.optionAnyOtherBusiness:
                Log.d(TAG,"optionAccountSettings: Navigating to Settings Activity");
                Intent toAssignTaskActivity = new Intent(ActivityAssignedTasks.this, AssignTaskActivity.class);
                startActivity(toAssignTaskActivity);
                return true;

            default:
                return true;
        }
    }
}
