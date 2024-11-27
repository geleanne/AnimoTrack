package com.mobdeve.xx22.rivera.josecarlos.animotrack;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.CollectionReference;

import java.util.HashMap;
import java.util.Map;

public class SignupPage extends AppCompatActivity {

    // Declare views
    private EditText fullNameField;
    private EditText idNumberField;
    private EditText emailField;
    private EditText passwordField;
    private Button signUpButton;
    private Button hasAnAccountButton; // Button to redirect to login page

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_page); // Ensure this matches your XML layout name

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        FirebaseFirestore db = FirebaseFirestore.getInstance(); // Initialize Firestore

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
                } else if (!email.endsWith("@dlsu.edu.ph") || email.indexOf('@') == 0) {
                    // Ensure email ends with @dlsu.edu.ph and there is text before the '@'
                    Toast.makeText(SignupPage.this, "Please use a valid DLSU email address", Toast.LENGTH_SHORT).show();
                } else {
                    // Create a new user with Firebase Authentication
                    mAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(SignupPage.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        if (user != null) {
                                            // Update the user's profile with the full name
                                            user.updateProfile(new UserProfileChangeRequest.Builder()
                                                            .setDisplayName(fullName)
                                                            .build())
                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            if (task.isSuccessful()) {
                                                                // Successfully updated the profile
                                                                Toast.makeText(SignupPage.this, "Account created!", Toast.LENGTH_SHORT).show();

                                                                // Save additional user data to Firestore
                                                                // Create a Map to store the user data
                                                                Map<String, Object> userData = new HashMap<>();
                                                                userData.put("name", fullName); // Store full name
                                                                userData.put("id_number", idNumber); // Store id_number
                                                                userData.put("email", email); // Store email
                                                                userData.put("password", password); // Store password

                                                                // Get a reference to the 'AnimoTrackUsers' collection
                                                                CollectionReference usersRef = db.collection("AnimoTrackUsers");

                                                                // Add the new user document with the user's UID as the document ID
                                                                usersRef.document(user.getUid()) // Use UID to ensure the document ID is unique
                                                                        .set(userData)
                                                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                            @Override
                                                                            public void onSuccess(Void aVoid) {
                                                                                // Successfully saved user data to Firestore
                                                                                Log.d("SignupPage", "User data saved to Firestore!");
                                                                                Toast.makeText(SignupPage.this, "User data saved to Firestore User UID: " + user.getUid(), Toast.LENGTH_SHORT).show();

                                                                                // Redirect to LoginPage
                                                                                Intent intent = new Intent(SignupPage.this, LoginPage.class);
                                                                                intent.putExtra("fullName", fullName);
                                                                                startActivity(intent);
                                                                                finish();
                                                                            }
                                                                        })
                                                                        .addOnFailureListener(new OnFailureListener() {
                                                                            @Override
                                                                            public void onFailure(@NonNull Exception e) {
                                                                                Toast.makeText(SignupPage.this, "Failed to save user data with User UID: " + user.getUid(), Toast.LENGTH_SHORT).show();
                                                                            }
                                                                        });
                                                            } else {
                                                                Toast.makeText(SignupPage.this, "Profile update failed", Toast.LENGTH_SHORT).show();
                                                            }
                                                        }
                                                    });
                                        }
                                    } else {
                                        Toast.makeText(SignupPage.this, "Authentication failed", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
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
