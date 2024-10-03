package com.example.assignment1;

import android.content.Intent;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private SeekBar seekBar;
    private TextView percentageTextView;
    private TextView mortgageInput;
    private RadioButton radioButtonMonthly;
    private double monthlyPayment;  // Change to double

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize the Spinner
        Spinner spinner = findViewById(R.id.spinnerTotalYears);
        ArrayList<Integer> years = new ArrayList<>();
        for (int i = 0; i <= 30; i++) {
            years.add(i);
        }

        ArrayAdapter<Integer> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, years);
        spinner.setAdapter(adapter);

        seekBar = findViewById(R.id.seekBarInterestRate);
        percentageTextView = findViewById(R.id.percentageTextView);
        mortgageInput = findViewById(R.id.txtInputEnterAmount);

        seekBar.setMax(30);
        percentageTextView.setText("0%");

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                percentageTextView.setText(progress + "%");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        // Add listener to Calculate button
        Button calculateButton = findViewById(R.id.calculateButton);
        calculateButton.setOnClickListener(v -> {
            calculateMonthlyPayment();
            // Start the ResultActivity and pass the result
            Intent intent = new Intent(MainActivity.this, ResultActivity.class);
            intent.putExtra("monthlyPayment", monthlyPayment); // Pass as double
            startActivity(intent);
        });
    }

    private void calculateMonthlyPayment() {
        String principalStr = mortgageInput.getText().toString();
        int interestRate = seekBar.getProgress();

        Spinner spinner = findViewById(R.id.spinnerTotalYears);
        int years = Integer.parseInt(spinner.getSelectedItem().toString());

        //Show a message in case user did not enter any mortgage principal amount but did press the "Calculate" button
        if (principalStr.isEmpty()) {
            Toast.makeText(this, "Please enter a mortgage principal amount", Toast.LENGTH_SHORT).show();
            return;
        }

        double principal = Double.parseDouble(principalStr);
        double monthlyInterestRate = interestRate / 100.0 / 12.0;
        int numberOfPayments = years * 12;

        // Monthly payment calculation
        if (monthlyInterestRate > 0) {
            monthlyPayment = (principal * monthlyInterestRate) /
                    (1 - Math.pow(1 + monthlyInterestRate, -numberOfPayments));
        } else {
            monthlyPayment = principal / numberOfPayments; // Simple calculation if interest rate is 0
        }
    }

}

