package com.mobdeve.xx22.rivera.josecarlos.animotrack;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class SignupPage extends AppCompatActivity {

    // Declare views
    private EditText fullNameField;
    private EditText idNumberField;
    private EditText emailField;
    private EditText passwordField;
    private Button signUpButton;
    private Button hasAnAccountButton; // Button to redirect to login page

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_page); // Ensure this matches your XML layout name

        // Initialize views
        fullNameField = findViewById(R.id.editTextFullName);
        idNumberField = findViewById(R.id.editTextIdNumber);
        emailField = findViewById(R.id.editTextEmailAddress);
        passwordField = findViewById(R.id.editTextPassword);
        signUpButton = findViewById(R.id.buttonLogin); // This button is labeled "Sign Up" in the XML
        hasAnAccountButton = findViewById(R.id.hasAnAccountButton); // Button to go back to login

        // Set OnClickListener for the Sign Up button
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get input values
                String fullName = fullNameField.getText().toString().trim();
                String idNumber = idNumberField.getText().toString().trim();
                String email = emailField.getText().toString().trim();
                String password = passwordField.getText().toString().trim();

                // Simple validation
                if (fullName.isEmpty() || idNumber.isEmpty() || email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(SignupPage.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                } else if (!email.endsWith("@dlsu.edu.ph")) {
                    Toast.makeText(SignupPage.this, "Please use a valid DLSU email address", Toast.LENGTH_SHORT).show();
                } else {
                    // Proceed with sign up logic (e.g., save user info, connect to a database)
                    Toast.makeText(SignupPage.this, "Sign Up Successful!", Toast.LENGTH_SHORT).show();

                    // Optionally redirect to MainActivity or another activity
                    Intent intent = new Intent(SignupPage.this, MainActivity.class);
                    startActivity(intent);
                    finish(); // Optional: Call finish() if you want to remove SignUpPage from the back stack
                }
            }
        });

        // Set OnClickListener for the already have an account button
        hasAnAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Redirect to LoginPage
                Intent intent = new Intent(SignupPage.this, LoginPage.class);
                startActivity(intent);
                finish(); // Optional: Call finish() if you want to remove SignUpPage from the back stack
            }
        });
    }
}
