package com.example.nike.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nike.MainActivity;
import com.example.nike.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import io.reactivex.rxjava3.annotations.NonNull;

public class Login_Activity extends AppCompatActivity {

    FirebaseAuth mAuth;
    EditText editText_email_Login;
    EditText editText_password_Login;
    Button button_login_Login;
    TextView textView_clickToRegister_Login;
    ProgressBar progressBar_Login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        editText_email_Login = findViewById(R.id.editText_email_Login);
        editText_password_Login = findViewById(R.id.editText_password_Login);
        button_login_Login = findViewById(R.id.button_login_Login);
        textView_clickToRegister_Login = findViewById(R.id.textView_clickToRegister_Login);
        progressBar_Login = findViewById(R.id.progressBar_Login);

        textView_clickToRegister_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GoToRegisterActivity();
            }
        });

        button_login_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar_Login.setVisibility(View.VISIBLE);
                String email, password;
                email = String.valueOf(editText_email_Login.getText());
                password = String.valueOf(editText_password_Login.getText());

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(Login_Activity.this, "Enter email", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(Login_Activity.this, "Enter password", Toast.LENGTH_SHORT).show();
                    return;
                }

                mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressBar_Login.setVisibility(View.GONE);
                        if (task.isSuccessful()) {
                            Toast.makeText(Login_Activity.this, "Login Successful.", Toast.LENGTH_SHORT).show();
                            GoToMainActivity();
                        } else {
                            Toast.makeText(Login_Activity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });

    }

/*    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            GoToMainActivity();
        }
    }*/

    private void GoToMainActivity()
    {
        Intent mainIntent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(mainIntent);
        finish();
    }

    private void GoToRegisterActivity()
    {
        Intent registerIntent = new Intent(getApplicationContext(), Register_Activity.class);
        startActivity(registerIntent);
        finish();
    }

}