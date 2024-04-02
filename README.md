# Lab 1: User Interface (UI)

## General description

This repository contains the complete Lab 1 of the course, which focuses on creating an easy-to-use interface for managing personal contact information. The app allows users to enter and display personal data of a contact, using the Android UI components effectively.

## Achieved goals

- **Graphic user interface development**: Implemented using the EditText, TextView, Spinner, DatePicker and RadioButton components.
- **Layout Management**: Used ConstraintLayout and LinearLayout to structure the application's UI efficiently.
- **Event Handling**: Implemented button click events and other user interactions to capture and process user input.
- **Multilingual Support**: The app is available in English and Spanish, demonstrating multilingual support within a single app.

## Characteristics

- **Personal Data Activity**: Allows users to enter personal information, including first name, last name, gender, date of birth, and educational level. Required fields are marked and validated before submission.
- **Contact data activity**: Users can enter contact related information such as phone number, address, email, country (Consumption was made to [restcountries api](https://restcountries.com/v2) to obtain countries in the world ) and city (Consumption of the [geonames api](http://api.geonames.org) was carried out).

## Technical details

- **Keyboard Navigation**: Improved user experience by replacing the default "enter" action with a "next" action on text inputs.
- **Dynamic UI adjustment**: Made sure that the virtual keyboard does not obscure text fields being edited.
- **Data persistence**: Mechanisms implemented to save user input when configuration changes (for example, screen rotation).
- **Custom App Icon**: Designed and integrated a custom icon to improve app identity and user engagement.

## Multilingual support

The app supports English and Spanish, showing our commitment to accessibility and inclusion of users in different regions.

## Team Members

- Valeria Granada Rodas
- Alejandro Castrillón Ciro
- Daniela Vasquez

For further details on the project structure and implementation, please refer to the individual files and commits within this repository.