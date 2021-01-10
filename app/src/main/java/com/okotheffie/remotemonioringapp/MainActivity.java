package com.okotheffie.remotemonioringapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.okotheffie.remotemonioringapp.adapters.ViewPagerAdapter;
import com.okotheffie.remotemonioringapp.models.User;

public class MainActivity extends AppCompatActivity  {

    private static final String TAG = "MainActivity";
    //constants
    private FirebaseAuth.AuthStateListener mAuthListener;
    //widgets
    Toolbar toolbar;
    TabLayout tabLayout;
    ViewPager viewPager;
    ViewPagerAdapter viewPagerAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        viewPager = findViewById(R.id.viewPager);
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        setupFirebaseAuth();
        setUserDetails();
    }

    //Override methods
    @Override
    protected void onResume() {
        super.onResume();
        checkAuthenticationState();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){

            case R.id.optionAccountSettings:
                Log.d(TAG,"optionAccountSettings: Navigating to Settings Activity");
                Intent toAccountSettings = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(toAccountSettings);
                return true;

            case R.id.optionAnyOtherBusiness:
//                Log.d(TAG,"optionAccountSettings: Navigating to Settings Activity");
//                Intent toAssignTaskActivity = new Intent(MainActivity.this, AssignTaskActivity.class);
//                startActivity(toAssignTaskActivity);
                return true;

            case R.id.optionSignOut:
                Log.d(TAG,"signingOut");
                signOut();

            default:
                return true;
        }
    }
    //My methods
    private void setupFirebaseAuth(){
        Log.d(TAG, "setupFirebaseAuth: started.");

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {

                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());

                } else {
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                }
                // ...
            }
        };
    }
    public void checkAuthenticationState() {
        Log.d(TAG,"checkAuthenticationState: checking authentiction state.");
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null){
            Log.d(TAG,"checkAuthenticationState: user is null, navigate back to the Login Screen.");
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        } else{
            Log.d(TAG,"checkAuthenticationState: User is Authenticated.");
        }
    }
    private void getUserDetails(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null){
            final FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference ref = database.getReference
                    ("users")
                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid());
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    User user_properties = snapshot.getValue(User.class);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }
    private void setUserDetails(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null){
            UserProfileChangeRequest profileUpdate = new UserProfileChangeRequest.Builder()
                    .setDisplayName("Muchemi Philip")
                    .setPhotoUri(Uri.parse("https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.cnn.com%2F2020%2F05%2F22%2Fentertainment%2Fquavo-graduates-high-school-trnd%2Findex.html&psig=AOvVaw1O0F31C7msOkJLnbqxhWXA&ust=1595692309589000&source=images&cd=vfe&ved=0CA0QjhxqFwoTCMDI4Jml5uoCFQAAAAAdAAAAABAD"))
                    .build();

            user.updateProfile(profileUpdate)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Log.d(TAG,"onComplete: User Profile Updated");
                                getUserDetails();
                            }
                        }
                    });
        }
    }
    private void signOut() {
        Log.d(TAG,"signOut: signing out");
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
