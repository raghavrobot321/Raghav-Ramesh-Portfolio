package Files;// Country Validation & Lookup Helper
// Raghav Ramesh


// Import necessary libraries
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

public class CountryValidator {
    private static final Set<String> VALID_CODES = new HashSet<>();

    static {
        // Automatically populates every valid 2-letter ISO country code in the world
        String[] countryCodes = Locale.getISOCountries();

        for (String code : countryCodes) {
            VALID_CODES.add(code);
        }
    }

    // Check if the user's entered code is valid
    public static boolean isValidCode(String code) {
        if (code == null) {
            return false;
        }

        String uppercaseCode = code.trim().toUpperCase();

        if (VALID_CODES.contains(uppercaseCode)) {
            return true;
        }

        return false;
    }

    // Returns the full list of supported 2-letter codes
    public static Set<String> getAvailableCodes() {
        return VALID_CODES;
    }

    public static void main(String[] args) {
        System.out.println("Supported Country Codes:");

        for (String code : CountryValidator.getAvailableCodes()) {
            System.out.print(code + " ");
        }
    }
}