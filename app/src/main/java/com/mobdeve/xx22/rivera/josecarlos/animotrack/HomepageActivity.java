package com.mobdeve.xx22.rivera.josecarlos.animotrack;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class HomepageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.animotrack_homepage); // Use your layout file

        // Initialize the button
        Button continueButton = findViewById(R.id.button);

        // Set an onClickListener for the button
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an intent to start LoginPage activity
                Intent intent = new Intent(HomepageActivity.this, LoginPage.class);
                startActivity(intent); // Start the LoginPage activity
            }
        });
    }
}
