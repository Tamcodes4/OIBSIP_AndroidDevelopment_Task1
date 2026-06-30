package com.example.unitconverterapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    Spinner spinnerCategory, spinnerFromUnit, spinnerToUnit;
    EditText editInputValue;
    Button btnConvert;
    TextView textResult;

    // Conversion factors relative to a base unit, for each category
    Map<String, Map<String, Double>> categoryUnits = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinnerCategory = findViewById(R.id.spinnerCategory);
        spinnerFromUnit = findViewById(R.id.spinnerFromUnit);
        spinnerToUnit = findViewById(R.id.spinnerToUnit);
        editInputValue = findViewById(R.id.editInputValue);
        btnConvert = findViewById(R.id.btnConvert);
        textResult = findViewById(R.id.textResult);

        setupUnitData();

        // Category spinner
        String[] categories = {"Length", "Weight", "Temperature"};
        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, categories);
        spinnerCategory.setAdapter(categoryAdapter);

        spinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                updateUnitSpinners(categories[position]);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        btnConvert.setOnClickListener(v -> performConversion());
    }

    private void setupUnitData() {
        // Length -> base unit: meters
        Map<String, Double> length = new HashMap<>();
        length.put("Meters", 1.0);
        length.put("Centimeters", 0.01);
        length.put("Kilometers", 1000.0);
        length.put("Millimeters", 0.001);
        length.put("Inches", 0.0254);
        length.put("Feet", 0.3048);
        categoryUnits.put("Length", length);

        // Weight -> base unit: grams
        Map<String, Double> weight = new HashMap<>();
        weight.put("Grams", 1.0);
        weight.put("Kilograms", 1000.0);
        weight.put("Milligrams", 0.001);
        weight.put("Pounds", 453.592);
        weight.put("Ounces", 28.3495);
        categoryUnits.put("Weight", weight);

        // Temperature handled specially (not linear factors)
        Map<String, Double> temp = new HashMap<>();
        temp.put("Celsius", 0.0);
        temp.put("Fahrenheit", 0.0);
        temp.put("Kelvin", 0.0);
        categoryUnits.put("Temperature", temp);
    }

    private void updateUnitSpinners(String category) {
        String[] units = categoryUnits.get(category).keySet().toArray(new String[0]);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, units);
        spinnerFromUnit.setAdapter(adapter);
        spinnerToUnit.setAdapter(adapter);
    }

    private void performConversion() {
        String input = editInputValue.getText().toString();
        if (input.isEmpty()) {
            textResult.setText("Please enter a value");
            return;
        }

        double inputValue = Double.parseDouble(input);
        String category = spinnerCategory.getSelectedItem().toString();
        String fromUnit = spinnerFromUnit.getSelectedItem().toString();
        String toUnit = spinnerToUnit.getSelectedItem().toString();

        double result;

        if (category.equals("Temperature")) {
            result = convertTemperature(inputValue, fromUnit, toUnit);
        } else {
            Map<String, Double> units = categoryUnits.get(category);
            double baseValue = inputValue * units.get(fromUnit);
            result = baseValue / units.get(toUnit);
        }

        textResult.setText(formatResult(result) + " " + toUnit);
    }

    private double convertTemperature(double value, String from, String to) {
        if (from.equals(to)) return value;

        double celsius;
        // Convert input to Celsius first
        switch (from) {
            case "Fahrenheit":
                celsius = (value - 32) * 5 / 9;
                break;
            case "Kelvin":
                celsius = value - 273.15;
                break;
            default:
                celsius = value;
        }

        // Convert Celsius to target unit
        switch (to) {
            case "Fahrenheit":
                return celsius * 9 / 5 + 32;
            case "Kelvin":
                return celsius + 273.15;
            default:
                return celsius;
        }
    }

    private String formatResult(double value) {
        // Round to 4 decimal places first
        double rounded = Math.round(value * 10000.0) / 10000.0;

        // If it's a whole number, show no decimals
        if (rounded == Math.floor(rounded) && !Double.isInfinite(rounded)) {
            return String.valueOf((long) rounded);
        }

        // Otherwise show up to 4 decimals, trimming trailing zeros
        String formatted = String.format("%.4f", rounded);
        formatted = formatted.replaceAll("0+$", ""); // remove trailing zeros
        formatted = formatted.replaceAll("\\.$", ""); // remove trailing dot if it became whole
        return formatted;
    }
}