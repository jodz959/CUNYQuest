package cunycodes.org.cunyquest;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private final String TAG = "LoginActivity";
    private      FirebaseAuth     mAuth;
    private      Button           btnCreateAccount, btnSignIn;
    private      EditText         eEmail, ePassword;
    private      Button           btnForgotPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth               =   FirebaseAuth.getInstance();
        eEmail              =   (EditText) findViewById(R.id.email_text);
        ePassword           =   (EditText) findViewById(R.id.password_text);
        btnCreateAccount    =   (Button) findViewById(R.id.buttonCreateAccount);
        btnSignIn           =   (Button) findViewById(R.id.buttonSignIn);
        btnForgotPassword     =    (Button) findViewById(R.id.forgot_password);

        btnCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email    =    eEmail.getText().toString();
                String password =    ePassword.getText().toString();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Email field is empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter Password", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.length() < 8) {
                    Toast.makeText(getApplicationContext(), "Password is too short", Toast.LENGTH_SHORT).show();
                    return;
                }

                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Toast.makeText(getApplicationContext(), "createUser:onComplete"+ task.isSuccessful(), Toast.LENGTH_SHORT).show();
//                                 progressBar.setVisibility(View.GONE);

                                if(!task.isSuccessful()){
                                    Toast.makeText(getApplicationContext(), "Authentication Failed. Check fields.", Toast.LENGTH_SHORT).show();
                                } else {
                                    //setContentView(R.layout.profile_info);
                                 //   finish();
                                    Intent intent = new Intent(getApplicationContext(), ProfileActvity.class);
                                    startActivity(intent);
                                }
                            }
                        });


            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email    =    eEmail.getText().toString();
                String password =    ePassword.getText().toString();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Email field is empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter Password", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.length() < 8) {
                    Toast.makeText(getApplicationContext(), "Password is too short", Toast.LENGTH_SHORT).show();
                    return;
                }

                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Toast.makeText(getApplicationContext(), "loginUser:onComplete"+ task.isSuccessful(), Toast.LENGTH_SHORT).show();
//                                progressBar.setVisibility(View.GONE);

                                if(!task.isSuccessful()){
                                    Toast.makeText(getApplicationContext(), "Authentication Failed.Check Fields", Toast.LENGTH_SHORT).show();
                                } else {
                                    //  setContentView(R.layout.profile_info);
                                   // finish();
                                    Intent intent = new Intent(getApplicationContext(), FeedActivity.class);
                                    startActivity(intent);
                                }
                            }
                        });
            }
        });

        btnForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, ResetPassword.class);
                startActivity(intent);
               // Log.d(TAG, "Sign In text works");
            }
        });
    }
}
