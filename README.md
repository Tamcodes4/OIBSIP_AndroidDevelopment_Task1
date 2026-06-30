# OIBSIP_AndroidDevelopment_Task1 — Unit Converter Application

## Objective
The objective of this project is to build an Android application that allows users to convert values between different units of measurement, such as length (meters, centimeters, kilometers, inches, feet), weight (grams, kilograms, pounds, ounces), and temperature (Celsius, Fahrenheit, Kelvin). The app provides a simple and intuitive interface where users select a category and units, enter a value, and instantly see the converted result.

## Tools Used
- **Android Studio** — IDE used for development
- **Java** — Programming language used for application logic
- **XML** — Used for designing the user interface
- **CardView** — Used for a modern, card-based UI layout

## Steps Performed
1. Created a new Android Studio project using the Empty Views Activity template with Java as the language.
2. Designed the user interface in `activity_main.xml` using `ConstraintLayout`/`LinearLayout` inside a `ScrollView`, with `Spinner` dropdowns for category and unit selection, an `EditText` for value input, and `CardView` components for a clean, modern look.
3. Implemented the conversion logic in `MainActivity.java`:
   - Defined conversion factors for each unit relative to a base unit (e.g., meters for length, grams for weight).
   - Handled temperature conversion separately using standard formulas, since temperature conversions are not simple multiplicative ratios.
   - Added dynamic spinner population so that unit options update automatically based on the selected category.
4. Added input validation to handle empty fields gracefully.
5. Formatted the output result to avoid unnecessary trailing zeros (e.g., displaying `5` instead of `5.0000`).
6. Tested the application on an Android emulator (Medium Phone API 36.1) to verify accurate conversions across all three categories.

## Outcome
The application successfully converts values across Length, Weight, and Temperature categories with accurate results. It features a clean, card-based Material Design-inspired UI with a purple color theme, clear input/output sections, and a responsive layout. The app correctly handles edge cases like empty input and displays clean, readable results without unnecessary decimal places.

## How to Run
1. Clone this repository.
2. Open the project folder in Android Studio.
3. Let Gradle sync complete.
4. Run the app on an emulator or physical Android device (minimum SDK 24).

## Screenshots
<img width="377" height="840" alt="image" src="https://github.com/user-attachments/assets/f907acfe-40c3-45e6-9578-ac6ae5ead179" />
