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

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";

    private TextInputEditText mEmail;
    private TextInputEditText mPassword;

    private Button mBtnLogin;
    private TextView mRegisterLink;
    private TextView mResendVerification;

    private ProgressBar mProgressBar;

    private FirebaseAuth.AuthStateListener mAuthListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Log.d(TAG, "onCreate: started.");

        initViews();
        setupFirebaseAuth();

        mBtnLogin.setOnClickListener(v -> {
            Log.d(TAG, "onClick: logging in the user.");

            //check if the fields are filled out
            if (isEmpty(Objects.requireNonNull(mEmail.getText()).toString())
                    && isEmpty(Objects.requireNonNull(mPassword.getText()).toString())) {
                Log.d(TAG, "onClick: attempting to authenticate.");
                showDialog();
                FirebaseAuth.getInstance().signInWithEmailAndPassword(mEmail.getText().toString(),
                        mPassword.getText().toString())
                        .addOnCompleteListener((Task<AuthResult> task) -> {

                            if (task.isSuccessful()) {
                                Toast.makeText(LoginActivity.this, "Authenticated", Toast.LENGTH_SHORT).show();
                                hideDialog();
                            }
                            if (!task.isSuccessful()) {
                                try {

                                    throw Objects.requireNonNull(task.getException());

                                    //if user enters wrong Email
                                } catch (FirebaseAuthInvalidUserException invalidEmail) {
                                    Log.d(TAG, "onComplete: invalid Email");
                                    Toast.makeText(LoginActivity.this, "The email you entered doesn't conform to a standard type", Toast.LENGTH_SHORT).show();

                                } catch (FirebaseAuthInvalidCredentialsException wrongPassword) {
                                    Log.d(TAG, "onComplete: malformedEmail");
                                    Toast.makeText(LoginActivity.this, "Wrong Password!!! ", Toast.LENGTH_SHORT).show();


                                } catch (Exception e) {
                                    Log.d(TAG, "onComplete: " + e.getMessage());
                                }
                            }
                        });
            }

        });
        mRegisterLink.setOnClickListener(v -> {
            Log.d(TAG, "onClick: Navigating to Register page.");

            Intent intentRegister = new Intent(LoginActivity.this,RegisterActivity.class);
            startActivity(intentRegister);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            emptyInputEditText();
            finish();
        });
        mResendVerification.setOnClickListener(v -> {
            ResendVerificationDialog dialog = new ResendVerificationDialog();
            dialog.show(getSupportFragmentManager(), "dialog_resend_email_verification");
        });
        hideSoftKeyboard();
    }
    @Override
    public void onStart() {
        super.onStart();
        FirebaseAuth.getInstance().addAuthStateListener(mAuthListener);
    }
    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            FirebaseAuth.getInstance().removeAuthStateListener(mAuthListener);
        }
    }

    /**
     * This method is to initialize views
     */
    private void initViews() {
        TextInputLayout textInputLayoutEmail = findViewById(R.id.textInputLayoutEmailAddress);
        TextInputLayout textInputLayoutPassword = findViewById(R.id.textInputLayoutPassword);
        mEmail = findViewById(R.id.editText_EmailAddress);
        mPassword = findViewById(R.id.editText_password);
        mBtnLogin = findViewById(R.id.button_login);
        mRegisterLink = findViewById(R.id.textView_registerLink);
        mResendVerification = findViewById(R.id.resend_verification_email);
        mProgressBar = findViewById(R.id.progressBar);

    }
    private void emptyInputEditText() {
        mEmail.setText(null);
        mPassword.setText(null);
    }
    private void showDialog(){
        mProgressBar.setVisibility(View.VISIBLE);
    }
    private void hideDialog(){
        if(mProgressBar.getVisibility() == View.VISIBLE){
            mProgressBar.setVisibility(View.INVISIBLE);
        }
    }
    private boolean isEmpty(String string){
        return !string.equals("");
    }
    private void hideSoftKeyboard(){
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    /*
    ----------------------------- Firebase setup ---------------------------------
 */
    private void setupFirebaseAuth() {
        Log.d(TAG, "setupFirebaseAuth: started.");

        mAuthListener = firebaseAuth -> {
            FirebaseUser user = firebaseAuth.getCurrentUser();
            if (user != null) {

                if (user.isEmailVerified()) {
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                    Toast.makeText(LoginActivity.this, "Authenticated with: " + user.getEmail(),
                            Toast.LENGTH_SHORT).show();

                    Intent intent = new  Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "Check your Email for a Verification Link" ,
                            Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                    FirebaseAuth.getInstance().signOut();
                }
            }
        };

    }

}

