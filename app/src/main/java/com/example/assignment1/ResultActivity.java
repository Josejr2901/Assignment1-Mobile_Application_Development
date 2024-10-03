package com.example.assignment1;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        // Retrieve the passed data
        double monthlyPayment = getIntent().getDoubleExtra("monthlyPayment", 0.0); // Change to double for precision

        // Update the result TextView
        TextView resultTextView = findViewById(R.id.resultTextView);
        resultTextView.setText(String.format("Monthly Payment: $%.2f", monthlyPayment)); // Format to two decimal places

        // Set up the Go Back button to go back to MainActivity when clicked by the user
        Button goBackButton = findViewById(R.id.goBackButton);
        goBackButton.setOnClickListener(v -> {
            Intent intent = new Intent(ResultActivity.this, MainActivity.class);
            startActivity(intent);
        });
    }
}
