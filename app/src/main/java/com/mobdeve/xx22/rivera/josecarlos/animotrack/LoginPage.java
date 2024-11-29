package com.mobdeve.xx22.rivera.josecarlos.animotrack;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginPage extends AppCompatActivity {

    // Declare views
    private EditText emailField;
    private EditText passwordField;
    private Button loginButton;
    private Button noAccountYetButton;

    private FirebaseFirestore db;
    private FirebaseAuth mAuth;

//    @Override
//    public void onStart() {
//        super.onStart();
//        // Check if user is signed in (non-null) and update UI accordingly.
//        FirebaseUser currentUser = mAuth.getCurrentUser();
//        if (currentUser != null) {
//            // If a user is already signed in, navigate to the MainActivity
//            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//            startActivity(intent);
//            finish(); // Optional: To remove LoginPage from the back stack
//        }
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page); // layout's filename

        // Initialize Firebase components
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        // Initialize views
        emailField = findViewById(R.id.editTextTextEmailAddress);
        passwordField = findViewById(R.id.editTextNumberPassword);
        loginButton = findViewById(R.id.buttonLogin);
        noAccountYetButton = findViewById(R.id.noAccountYetButton);

        // onClickListener for the login button
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailField.getText().toString().trim();
                String password = passwordField.getText().toString().trim();

                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(LoginPage.this, "Please enter your email and password", Toast.LENGTH_SHORT).show();
                } else if (!email.endsWith("@dlsu.edu.ph") || email.indexOf('@') == 0) {
                    Toast.makeText(LoginPage.this, "Please use a valid DLSU email address", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(LoginPage.this, "Logging you in...", Toast.LENGTH_SHORT).show();
                    signInWithEmail(email, password);
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

    private void signInWithEmail(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            if (user != null) {
                                String fullName = user.getDisplayName();  // Get the full name from Firebase user profile
                                Intent intent = new Intent(LoginPage.this, MainActivity.class);
                                intent.putExtra("fullName", fullName);
                                startActivity(intent);
                                finish();
                            }
                        } else {
                            Toast.makeText(LoginPage.this, "Authentication failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

}
