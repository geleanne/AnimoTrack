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
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.HashMap;
import java.util.Map;

public class SignupPage extends AppCompatActivity {

    private EditText fullNameField;
    private EditText idNumberField;
    private EditText emailField;
    private EditText passwordField;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_page);

        mAuth = FirebaseAuth.getInstance();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        fullNameField = findViewById(R.id.editTextFullName);
        idNumberField = findViewById(R.id.editTextIdNumber);
        emailField = findViewById(R.id.editTextEmailAddress);
        passwordField = findViewById(R.id.editTextPassword);
        Button signUpButton = findViewById(R.id.buttonLogin);
        Button hasAnAccountButton = findViewById(R.id.hasAnAccountButton);

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get input values
                String fullName = fullNameField.getText().toString().trim();
                String idNumber = idNumberField.getText().toString().trim();
                String email = emailField.getText().toString().trim();
                String password = passwordField.getText().toString().trim();

                if (fullName.isEmpty() || idNumber.isEmpty() || email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(SignupPage.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                } else if (!email.endsWith("@dlsu.edu.ph") || email.indexOf('@') == 0) {
                    // Ensure email ends with @dlsu.edu.ph and there is text before the '@'
                    Toast.makeText(SignupPage.this, "Please use a valid DLSU email address", Toast.LENGTH_SHORT).show();
                } else if (!isValidIdNumber(idNumber)) {
                    // Validate ID number (should be 8 digits and start with 1)
                    Toast.makeText(SignupPage.this, "Please input a valid ID number (8 digits starting with '12')", Toast.LENGTH_SHORT).show();
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

                                                                Map<String, Object> userData = new HashMap<>();
                                                                userData.put("name", fullName);
                                                                userData.put("id_number", idNumber);
                                                                userData.put("email", email);
                                                                userData.put("password", password);

                                                                // Reference to the 'AnimoTrackUsers' collection
                                                                CollectionReference usersRef = db.collection("AnimoTrackUsers");

                                                                // Add the new user document with the user's UID as the document ID
                                                                usersRef.document(user.getUid())
                                                                        .set(userData)
                                                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                            @Override
                                                                            public void onSuccess(Void aVoid) {
                                                                                Log.d("SignupPage", "User data saved to Firestore!");


                                                                                // After successful sign-up, retrieve FCM token
                                                                                FirebaseMessaging.getInstance().getToken()
                                                                                        .addOnCompleteListener(new OnCompleteListener<String>() {
                                                                                            @Override
                                                                                            public void onComplete(@NonNull Task<String> task) {
                                                                                                if (!task.isSuccessful()) {
                                                                                                    Log.w("SignupPage", "Fetching FCM registration token failed", task.getException());
                                                                                                    System.out.println("Fetching FCM registration token failed");
                                                                                                    return;
                                                                                                }

                                                                                                String token = task.getResult();

                                                                                                FirebaseUser user = mAuth.getCurrentUser();
                                                                                                if (user != null) {
                                                                                                    db.collection("AnimoTrackUsers")
                                                                                                            .document(user.getUid())
                                                                                                            .update("fcm_token", token);
                                                                                                }
                                                                                            }
                                                                                        });

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

        hasAnAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignupPage.this, LoginPage.class);
                startActivity(intent);
                finish();
            }
        });
    }

    // Validate ID number (8 digits, starts with '1')
    private boolean isValidIdNumber(String idNumber) {
        return idNumber.matches("^12\\d{6}$");
    }
}
