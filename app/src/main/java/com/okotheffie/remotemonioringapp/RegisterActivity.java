package com.okotheffie.remotemonioringapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = "RegisterActivity";

    private TextInputLayout textInputLayoutFirstName;
    private TextInputLayout textInputLayoutLastName;
    private TextInputLayout textInputLayoutEmail;
    private TextInputLayout textInputLayoutContactNumber;
    private TextInputLayout textInputLayoutUserName;
    private TextInputLayout textInputLayoutPassword;
    private TextInputLayout textInputLayoutConfirmPassword;

    private TextInputEditText mFirstName;
    private TextInputEditText mLastName;
    private TextInputEditText mEmail;
    private TextInputEditText mContactNo;
    private TextInputEditText mUserName;
    private TextInputEditText mPassword;
    private TextInputEditText mConfirmPassword;
    private Button mRegister;
    private TextView mTextViewLoginLink;
    private ProgressBar mProgressBar;


    private FirebaseAuth mAuth;
    FirebaseFirestore fstore;
    private String userID;

    public static final int PASSWORD_LENGTH = 8;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Log.d(TAG, "onCreate: started.");

        initViews();

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        mRegister.setOnClickListener(v -> {
            Log.d(TAG, "onClick: attempting to register.");

            //check for null valued EditText fields
            if (isEmpty(mEmail.getText().toString())
                    && isEmpty(mPassword.getText().toString())
                    && isEmpty(mConfirmPassword.getText().toString())) {

                //check if passwords match
                if (doStringsMatch(mPassword.getText().toString(), mConfirmPassword.getText().toString())) {
                    registerNewEmail(mEmail.getText().toString(), mPassword.getText().toString());

                } else {
                    Toast.makeText(RegisterActivity.this, "Passwords do not Match", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(RegisterActivity.this, "You must fill out all the fields", Toast.LENGTH_SHORT).show();
            }
        });
        hideSoftKeyboard();

        mTextViewLoginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginAcivity.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                emptyInputEditText();
                finish();
            }
        });
    }
    private void initViews() {
        textInputLayoutFirstName = findViewById(R.id.textInputLayoutFirstName);
        textInputLayoutLastName = findViewById(R.id.textInputLayoutLastName);
        textInputLayoutEmail = findViewById(R.id.textInputLayoutEmailAddress);
        textInputLayoutContactNumber = findViewById(R.id.textInputLayoutContactNo);
        textInputLayoutUserName = findViewById(R.id.textInputLayoutUserName);
        textInputLayoutPassword = findViewById(R.id.textInputLayoutPassword);
        textInputLayoutConfirmPassword = findViewById(R.id.textInputLayoutConfirmPasssword);

        mFirstName = findViewById(R.id.editText_firstName);
        mLastName = findViewById(R.id.editText_lastName);
        mEmail = findViewById(R.id.editText_EmailAddress);
        mContactNo = findViewById(R.id.editText_ContactNo);
        mUserName = findViewById(R.id.editText_UserName);
        mPassword = findViewById(R.id.editText_password);
        mConfirmPassword = findViewById(R.id.editText_confirmPassword);
        mRegister = findViewById(R.id.btn_login);
        mTextViewLoginLink = findViewById(R.id.txtView_registerLink);
        mProgressBar = findViewById(R.id.progressBar);


    }
    public void registerNewEmail(final String email, String password){

        showDialog();
        mAuth = FirebaseAuth.getInstance();
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());

                    if (task.isSuccessful()){
                        Log.d(TAG, "onComplete: AuthState: " + FirebaseAuth.getInstance().getCurrentUser().getUid());

                        sendVerificationEmail();

                        // Create a User object
                        userID = mAuth.getCurrentUser().getUid();
                        FirebaseFirestore fstore = FirebaseFirestore.getInstance();
                        DocumentReference documentReference = fstore.collection("users").document(userID);

                        Map<String, Object> user = new HashMap<>();
                        user.put("firstname",mFirstName.getText().toString().trim().toLowerCase());
                        user.put("lastname",mLastName.getText().toString().trim().toLowerCase());
                        user.put("email",FirebaseAuth.getInstance().getCurrentUser().getEmail());
                        user.put("contactnumber",mContactNo.getText().toString().toLowerCase());
                        user.put("username",mUserName.getText().toString().toLowerCase());
                        user.put("user_id",FirebaseAuth.getInstance().getCurrentUser().getUid());

                        documentReference.set(user).addOnSuccessListener(aVoid -> {
                            Log.d(TAG, "onSuccess: user profile is created for" + userID);
                            FirebaseAuth.getInstance().signOut();
                            redirectLoginScreen();

                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d(TAG, "onFailure: " + e.toString());
                            }
                        });
                    }
                    if (!task.isSuccessful()) {
                        try{
                            throw task.getException();
                            //if user enters weak Password
                        } catch (FirebaseAuthWeakPasswordException weakPassword) {
                            Log.d(TAG,"onComplete: Weak Password");
                            Toast.makeText(RegisterActivity.this, "Weak Password, Kindly try a Stronger Password", Toast.LENGTH_SHORT).show();

                        } catch (FirebaseAuthInvalidCredentialsException malformedEmail) {
                            Log.d(TAG,"onComplete: malformedEmail");
                            Toast.makeText(RegisterActivity.this, "Malformed Email,The email you entered don't conform to a standard type ", Toast.LENGTH_SHORT).show();

                        } catch (FirebaseAuthUserCollisionException existEmail) {
                            Log.d(TAG,"onComplete: Email already Exists");
                            Toast.makeText(RegisterActivity.this, "The email entered already exists ", Toast.LENGTH_SHORT).show();

                        } catch (Exception e) {
                            Log.d(TAG,"onComplete: " + e.getMessage());
                        }
                    }
                    hideDialog();
                });
    }
    private void sendVerificationEmail(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null){
            user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(RegisterActivity.this, "Sent Verification Email! Kindly Check your Email ",
                                Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(RegisterActivity.this, "Couldn't Send Verification Email",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
    private void emptyInputEditText() {
        mFirstName.setText(null);
        mLastName.setText(null);
        mEmail.setText(null);
        mContactNo.setText(null);
        mUserName.setText(null);
        mPassword.setText(null);
        mConfirmPassword.setText(null);
    }
    private void hideSoftKeyboard(){
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }
    private boolean doStringsMatch(String s1, String s2){
        return s1.equals(s2);
    }
    private boolean isEmpty(String string){
        return !string.equals("");
    }
    private void showDialog(){
        mProgressBar.setVisibility(View.VISIBLE);

    }
    private void hideDialog(){
        if(mProgressBar.getVisibility() == View.VISIBLE){
            mProgressBar.setVisibility(View.INVISIBLE);
        }
    }
    public static boolean is_Letter(char ch) {
        ch = Character.toUpperCase(ch);
        return (ch >= 'A' && ch <= 'Z');
    }
    public static boolean is_Numeric(char ch) {

        return (ch >= '0' && ch <= '9');
    }
    private void redirectLoginScreen(){
        Log.d(TAG, "redirectLoginScreen: redirecting to login screen.");

        Intent intent = new Intent(RegisterActivity.this, LoginAcivity.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        emptyInputEditText();
        finish();
    }
}