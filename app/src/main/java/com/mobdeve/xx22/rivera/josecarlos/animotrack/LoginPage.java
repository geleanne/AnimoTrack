package com.mobdeve.xx22.rivera.josecarlos.animotrack;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class LoginPage extends AppCompatActivity {

    // Declare views
    private EditText emailField;
    private EditText passwordField;
    private Button loginButton;
    private Button noAccountYetButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page); // layout's filename

        // Initialize views
        emailField = findViewById(R.id.editTextTextEmailAddress);
        passwordField = findViewById(R.id.editTextNumberPassword);
        loginButton = findViewById(R.id.buttonLogin);
        noAccountYetButton = findViewById(R.id.noAccountYetButton);

        // onClickListener for the login button
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // get input values
                String email = emailField.getText().toString().trim();
                String password = passwordField.getText().toString().trim();

                // validation
                if (email.isEmpty() && password.isEmpty()) {
                    Toast.makeText(LoginPage.this, "Please enter your email and password", Toast.LENGTH_SHORT).show();
                } else if (!email.endsWith("@dlsu.edu.ph")) {
                    Toast.makeText(LoginPage.this, "Please use a valid DLSU email address", Toast.LENGTH_SHORT).show();
                } else {
                    // If email is valid, redirect to MainActivity
                    Intent intent = new Intent(LoginPage.this, MainActivity.class);
                    startActivity(intent);
                    finish(); // Optional: Call finish() if you want to remove LoginPage from the back stack
                }
            }
        });

        // onClickListener for the no account yet button
        noAccountYetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an intent to start the Sign Up activity
                Intent intent = new Intent(LoginPage.this, SignupPage.class);
                startActivity(intent);
            }
        });
    }
}
