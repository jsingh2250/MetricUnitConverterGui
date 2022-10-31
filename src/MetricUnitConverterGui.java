
/** 
 * This program is a tool with a graphical user interface for converting values from one metric unit to another.
 * 
 * @author Jaskaran Singh
*/

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

// Class definition for the metric unit conversion tool.
public class MetricUnitConverterGui extends Application {
    // Store the name of this program.
    private static final String PROGRAM_NAME = "Metric Unit Converter GUI";

    // GUI components.
    private static GridPane gridPane = new GridPane();
    private static TextField inputTextField = new TextField();
    private static TextField outputTextField = new TextField();
    private static Button convertButton = new Button("Convert");

    /**
     * Print an error message for invalid user-input.
     * 
     * @param userInput: The invalid input from the user.
     */
    private static void printErrorForInvalidUserInput(String userInput) {
        String message = "The input \"" + userInput + "\" is invalid. Please try again.";
        Alert alert = new Alert(AlertType.ERROR, message);
        alert.show();
    }

    /**
     * Print an error message to tell the user that the inputted number is invalid.
     * 
     * @param inputtedNumber: The invalid inputted number from the user.
     */
    private static void printErrorForInvalidInputtedNumber(String inputtedNumber) {
        String message = "The inputted number \"" + inputtedNumber + "\" is invalid. Please try again.";
        Alert alert = new Alert(AlertType.ERROR, message);
        alert.show();
    }

    /**
     * Print an error message to tell the user that the inputted negative number is invalid.
     * 
     * @param inputtedNegativeNumber: The invalid negative number inputted by the user.
     */
    private static void printErrorForInvalidNegativeInputtedNumber(double inputtedNegativeNumber) {
        String message = "NUMBER_TO_CONVERT, \"" + inputtedNegativeNumber + "\", is less than zero.\n" +
                "NUMBER_TO_CONVERT must be greater than or equal to zero. Please try again.";
        Alert alert = new Alert(AlertType.ERROR, message);
        alert.show();
    }

    /**
     * Extract the root unit string from the inputted unit string.
     * 
     * @param inputtedUnit: The string that is supposed to contain the prefix and root unit combined.
     * @return If a root unit is found at the end of the inputted unit, the root unit string will be returned. If the root unit is not found, null will be returned.
     */
    private static String extractRootUnit(String inputtedUnit) {
        // Declare and initialize a string array of valid root units.
        String[] validRootUnits = { "m", "g", "s", "A", "K", "cd", "mol" };

        // Check whether the inputted unit ends with a valid root unit. If it does, return the root unit.
        for (String unit : validRootUnits) {
            if ((inputtedUnit.length() >= unit.length()) && (inputtedUnit.endsWith(unit))) {
                return unit;
            }
        }

        // Return null because a root unit was not found at the end of the inputted unit.
        return null;
    }

    /**
     * Print an error message to tell the user that the inputted unit is not a valid SI unit.
     * 
     * @param inputtedUnit: The string that is supposed to contain the prefix and root unit combined.
     */
    private static void printErrorForInvalidUserInputtedUnit(String inputtedUnit) {
        String message = "The inputted unit, \"" + inputtedUnit + "\", is not a valid SI unit.\n" +
                "The inputted units must be valid SI units. Please try again.";
        Alert alert = new Alert(AlertType.ERROR, message);
        alert.show();
    }

    /**
     * Print an error message to tell the user that the root unit from the unit to convert is not the same as the root unit from the converted unit.
     * 
     * @param rootUnitFromUnitToConvert: The root unit from the unit to convert.
     * @param rootUnitFromConvertedUnit: The root unit from the converted unit.
     */
    private static void printErrorForInvalidRootUnits(String rootUnitFromUnitToConvert,
            String rootUnitFromConvertedUnit) {
        String message = "The root unit from the UNIT_TO_CONVERT, \"" + rootUnitFromUnitToConvert
                + "\", is not the same as the root unit from the CONVERTED_UNIT, \""
                + rootUnitFromConvertedUnit + "\".";
        Alert alert = new Alert(AlertType.ERROR, message);
        alert.show();
    }

    /**
     * Convert the prefix string into the multiplier number that is associated with the prefix.
     * 
     * @param inputtedPrefix: The prefix string that is associated with a multiplier number.
     * @return If the prefix is a valid prefix, return the multiplier number that is associated with the prefix. If the prefix isn't a valid prefix, return the number zero.
     */
    private static double convertPrefixToMultiplierNumber(String inputtedPrefix) {
        // Check if the inputted prefix is one of the prefixes in the array of valid prefixes below. If it is, return the associated multiplier number.
        String[] validPrefixesCentiToHecto = { "c", "d", "", "da", "h" };
        for (int prefixNumber = 0; prefixNumber < validPrefixesCentiToHecto.length; prefixNumber++) {
            if ((inputtedPrefix.length() == validPrefixesCentiToHecto[prefixNumber].length())
                    && (inputtedPrefix.equals(validPrefixesCentiToHecto[prefixNumber]))) {
                // Convert the index into the power of 10 associated with the prefix.
                return Math.pow(10, prefixNumber - 2);
            }
        }

        // Check if the inputted prefix is one of the prefixes in the array of valid prefixes below. If it is, return the associated multiplier number.
        String[] validPrefixesYoctoToYotta = { "y", "z", "a", "f", "p", "n", "u", "m", "", "k", "M", "G", "T", "P", "E",
                "Z", "Y" };
        for (int prefixNumber = 0; prefixNumber < validPrefixesYoctoToYotta.length; prefixNumber++) {
            if ((inputtedPrefix.length() == validPrefixesYoctoToYotta[prefixNumber].length())
                    && (inputtedPrefix.equals(validPrefixesYoctoToYotta[prefixNumber]))) {
                // Convert the index into the power of 10 associated with the prefix.
                return Math.pow(10, ((prefixNumber - 8) * 3));
            }
        }

        // Return zero because the inputted prefix was not a valid prefix.
        return 0;
    }

    /**
     * Print an error message to tell the user that the inputted prefix is not a valid SI prefix.
     * 
     * @param inputtedPrefix: The string that contains the inputted prefix.
     */
    private static void printErrorForInvalidPrefix(String inputtedPrefix) {
        String message = "The inputted prefix, \"" + inputtedPrefix + "\", is not a valid SI prefix.\n" +
                "The inputted prefix must be a valid SI prefix. Please try again.";
        Alert alert = new Alert(AlertType.ERROR, message);
        alert.show();
    }

    /**
     * Print the result of the requested conversion.
     * 
     * @param splitUserInput: The string array that contains the split user input.
     * @param convertedNumber: The double that contains the converted number.
     */
    private static void printResultOfRequestedConversion(String[] splitUserInput, double convertedNumber) {
        // Convert the converted number into a formatted string.
        String formattedConvertedNumber = String.format("%.3g", convertedNumber);

        // Print the result of the requested conversion.
        outputTextField.setText(
                splitUserInput[0] + " " + splitUserInput[1] + " " + splitUserInput[2] + " " + formattedConvertedNumber
                        + " " + splitUserInput[3]);
    }

    @Override
    /**
     * Overrides the start method in the Application class.
     * 
     * @param primaryStage The stage for the GUI.
     */
    public void start(Stage primaryStage) {
        // Create labels.
        Label instructionLabel = new Label("Please enter a metric conversion query.\n\n" +
                "The metric conversion query should be in the format:\n" +
                "NUMBER_TO_CONVERT UNIT_TO_CONVERT = CONVERTED_UNIT\n\n" +
                "NUMBER_TO_CONVERT should be a positive decimal number.\n" +
                "UNIT_TO_CONVERT and CONVERTED_UNIT should have the same root unit, the portion of the unit without a prefix.\n"
                +
                "UNIT_TO_CONVERT and CONVERTED_UNIT should use prefixes and root units from the International System of Units (SI).\n\n"
                +
                "Examples of a valid metric conversion query for converting 1 kilogram to grams is:\n" +
                "1 kg = g\n" +
                "or\n" +
                "1.0 kg = g");
        Label inputTextFieldLabel = new Label("Input:");
        Label outputTextFieldLabel = new Label("Output:");

        // Arrange the GUI components.
        // Pane.
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(5);
        gridPane.setVgap(5);
        GridPane.setColumnSpan(instructionLabel, 2);
        // Instruction label.
        gridPane.add(instructionLabel, 0, 0);
        // Input text field label.
        gridPane.add(inputTextFieldLabel, 0, 1);
        // Input text field.
        gridPane.add(inputTextField, 1, 1);
        // Output text field label.
        gridPane.add(outputTextFieldLabel, 0, 2);
        // Output text field.
        gridPane.add(outputTextField, 1, 2);
        // Button.
        gridPane.add(convertButton, 1, 3);

        // Make output field non-editable.
        outputTextField.setEditable(false);

        // Define what happens when the button is pressed.
        convertButton.setOnAction(e -> convertUnits());

        // Set up the stage.
        primaryStage.setTitle(PROGRAM_NAME);
        Scene scene = new Scene(gridPane);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Convert the user-specified units from the input field and display the result in the output field.
     * 
     */
    public static void convertUnits() {
        // Store next line inputted by user.
        String userInput = inputTextField.getText();

        // Split the user input into a string array.
        String[] splitUserInput = userInput.split(" ");

        // If the string array isn't equal to the length expected for a valid input, or if the third element in the string array isn't an equal sign, print an error message for invalid user-input and let the user retry inputting.
        int lengthOfValidInput = 4;
        if ((splitUserInput.length != lengthOfValidInput) || (!splitUserInput[2].equals("="))) {
            // Print an error message for invalid user-input.
            printErrorForInvalidUserInput(userInput);

            // Let the user retry inputting.
            return;
        }

        // Try to parse a double from the first element in the string array.
        double numberToConvert;
        try {
            numberToConvert = Double.parseDouble(splitUserInput[0]);
        } catch (Exception exception) {
            // Print an error message to tell the user that the inputted number is invalid.
            printErrorForInvalidInputtedNumber(splitUserInput[0]);

            // Let the user retry inputting.
            return;
        }

        // If the number is negative, print an error message for invalid user-input and let the user retry inputting.
        if (numberToConvert < 0) {
            // Print an error message to tell the user that the inputted negative number is invalid.
            printErrorForInvalidNegativeInputtedNumber(numberToConvert);

            // Let the user retry inputting.
            return;
        }

        // Extract and store the unit that needs to be converted from the second element in the string array.
        String unitToConvert = splitUserInput[1];

        // Extract and store the root unit from the unit that needs to be converted.
        String rootUnitFromUnitToConvert = extractRootUnit(unitToConvert);

        // If the unit does not end in a valid root unit, print an error message for invalid user-input and let the user retry inputting.
        if (rootUnitFromUnitToConvert == null) {
            // Print an error message to tell the user that the inputted unit is not a valid SI unit.
            printErrorForInvalidUserInputtedUnit(unitToConvert);

            // Let the user retry inputting.
            return;
        }

        // Extract and store the converted unit from the fourth element in the string array.
        String convertedUnit = splitUserInput[3];

        // Extract and store the root unit from the converted unit.
        String rootUnitFromConvertedUnit = extractRootUnit(convertedUnit);

        // If the unit does not end in a valid root unit, print an error message for invalid user-input and let the user retry inputting.
        if (rootUnitFromConvertedUnit == null) {
            // Print an error message to tell the user that the inputted unit is not a valid SI unit.
            printErrorForInvalidUserInputtedUnit(convertedUnit);

            // Let the user retry inputting.
            return;
        }

        // If the root unit from the unit to convert is not the same as the root unit from the converted unit, print an error message for invalid user-input and let the user retry inputting.
        if (!rootUnitFromUnitToConvert.equals(rootUnitFromConvertedUnit)) {
            // Print an error message to tell the user that the root unit from the unit to convert is not the same as the root unit from the converted unit.
            printErrorForInvalidRootUnits(rootUnitFromUnitToConvert, rootUnitFromConvertedUnit);

            // Let the user retry inputting.
            return;
        }

        // Store the index of the last occurence of the root unit string in the string of the unit that needs to be converted.
        int lastIndexOfRootUnitFromUnitToConvert = unitToConvert.lastIndexOf(rootUnitFromUnitToConvert);

        // Extract and store the prefix from the unit that needs to be converted.
        String prefixOfUnitToConvert = unitToConvert.substring(0, lastIndexOfRootUnitFromUnitToConvert);

        // If the prefix is a valid prefix, store the multiplier number that is associated with the prefix. If the prefix isn't a valid prefix, store the number zero.
        double multiplierToConvertInputtedNumberToRootUnitNumber = convertPrefixToMultiplierNumber(
                prefixOfUnitToConvert);

        // Store a value to indicate what "approximately zero" means as a double value.
        double epsilon = 1e-50;

        // If the multiplier is approximately equal to zero, the prefix from the unit that needs to be converted wasn't a valid prefix. Print an error message for invalid user-input and let the user retry inputting.
        if ((multiplierToConvertInputtedNumberToRootUnitNumber - 0.0) < epsilon) {
            // Print an error message to tell the user that the inputted prefix is not a SI valid prefix.
            printErrorForInvalidPrefix(prefixOfUnitToConvert);

            // Let the user retry inputting.
            return;
        }

        // Store the index of the last occurence of the root unit string in the string of the converted unit.
        int lastIndexOfRootUnitFromConvertedUnit = convertedUnit.lastIndexOf(rootUnitFromConvertedUnit);

        // Extract and store the prefix from the converted unit.
        String prefixOfConvertedUnit = convertedUnit.substring(0, lastIndexOfRootUnitFromConvertedUnit);

        // If the prefix is a valid prefix, store the multiplier number that is associated with the prefix. If the prefix isn't a valid prefix, store the number zero.
        double multiplierToConvertRootUnitNumberToOutputNumber = convertPrefixToMultiplierNumber(
                prefixOfConvertedUnit);

        // If the multiplier is approximately equal to zero, the prefix from the converted unit wasn't a valid prefix. Print an error message for invalid user-input and let the user retry inputting.
        if ((multiplierToConvertRootUnitNumberToOutputNumber - 0.0) < epsilon) {
            // Print an error message to tell the user that the inputted prefix is not a valid SI prefix.
            printErrorForInvalidPrefix(prefixOfConvertedUnit);

            // Let the user retry inputting.
            return;
        }

        // Calculate the number that is going to be outputted with the converted unit.
        double convertedNumber = numberToConvert * multiplierToConvertInputtedNumberToRootUnitNumber
                / multiplierToConvertRootUnitNumberToOutputNumber;

        // Print the result of the requested conversion.
        printResultOfRequestedConversion(splitUserInput, convertedNumber);

    }

    /**
     * Main method for the metric unit converter tool.
     * 
     * @param args: Unused parameter.
     */
    public static void main(String[] args) throws Exception {
        launch(args);
    }
}