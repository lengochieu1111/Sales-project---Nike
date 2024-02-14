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

public class Register_Activity extends AppCompatActivity {
    FirebaseAuth mAuth;
    EditText editText_email_Register;
    EditText editText_password_Register;
    Button button_register_Register;
    TextView textView_clickToLogin_Register;
    ProgressBar progressBar_Register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        editText_email_Register = findViewById(R.id.editText_email_Register);
        editText_password_Register = findViewById(R.id.editText_password_Register);
        button_register_Register = findViewById(R.id.button_register_Register);
        textView_clickToLogin_Register = findViewById(R.id.textView_clickToLogin_Register);
        progressBar_Register = findViewById(R.id.progressBar_Register);

        textView_clickToLogin_Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginIntent = new Intent(getApplicationContext(), Login_Activity.class);
                startActivity(loginIntent);
                finish();
            }
        });

        button_register_Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email, password;
                email = String.valueOf(editText_email_Register.getText());
                password = String.valueOf(editText_password_Register.getText());

                if (TextUtils.isEmpty(email))
                {
                    Toast.makeText(Register_Activity.this, "Enter email", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password))
                {
                    Toast.makeText(Register_Activity.this, "Enter password", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressBar_Register.setVisibility(View.VISIBLE);

                mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressBar_Register.setVisibility(View.GONE);
                        if (task.isSuccessful()) {
                            Toast.makeText(Register_Activity.this, "Account created.", Toast.LENGTH_SHORT).show();

                            GoToMainActivity();
                        }
                        else {
                            Toast.makeText(Register_Activity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

    }

/*    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
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

}