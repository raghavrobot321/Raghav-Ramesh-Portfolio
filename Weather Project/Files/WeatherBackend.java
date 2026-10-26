package Files;// Backend Java File (Logic Layer)
// Raghav Ramesh

// Import necessary libraries
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WeatherBackend 
{
    // Weather API key
    private static final String API_KEY = "d93ef95a9061fb0231a8151512818b57";

    // Location fields
    public String city = "N/A";
    public String state = "N/A";
    public String country = "N/A";

    // Temperature fields
    public double tempC = 0.0;
    public double tempF = 32.0;

    // Condition fields
    public double windSpeed = 0.0;
    public String condition = "N/A";

    // Confirmation fields
    public boolean isSuccess = false;
    public String errorMessage = "";

    // Weather Constructor: Runs the API call immediately when created
    public WeatherBackend(String cityInput, String stateInput, String countryInput) 
    {
        // Set format
        this.state = stateInput.trim().toUpperCase();
        
        try 
        {
            // Spaced out country confirmation check
            if (!countryInput.trim().isEmpty()) 
            {
                if (!CountryValidator.isValidCode(countryInput)) 
                {
                    this.isSuccess = false;
                    this.errorMessage = "Invalid ISO Country Code. Try US, CA, GB, IN, etc.";
                    return;
                }
            }

            // Take in city input and edit for format
            String query = cityInput.trim();

            if (!countryInput.trim().isEmpty()) 
            {
                query += "," + countryInput.trim();
            }

            // Find URL
            String urlString = "http://api.openweathermap.org/data/2.5/weather?q=" + query + "&appid=" + API_KEY + "&units=metric";
            URL url = new URL(urlString);

            // Connect URL
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5000);

            // Confirm status
            int status = conn.getResponseCode();

            if (status != 200) 
            {
                this.isSuccess = false;
                this.errorMessage = "API Error Code: " + status;
                return;
            }

            // Read the response from API
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuilder responseContent = new StringBuilder();

            // Read response line by each connection
            while ((inputLine = in.readLine()) != null) 
            {
                responseContent.append(inputLine);
            }
            in.close();
            conn.disconnect();

            // Find Json
            String rawJson = responseContent.toString();

            // Parse the JSON response to extract weather data

            // Location data
            this.city = parseJsonValue(rawJson, "name");
            this.country = parseJsonValue(rawJson, "country");
            
            // Temperature data
            String tempCStr = parseJsonValue(rawJson, "temp");
            String windStr = parseJsonValue(rawJson, "speed");
            this.condition = parseJsonValue(rawJson, "main");

            // Convert to double
            this.tempC = Double.parseDouble(tempCStr);
            this.tempF = (this.tempC * 9 / 5) + 32;
            this.windSpeed = Double.parseDouble(windStr);

            this.isSuccess = true;
        } 
        
        // Call error message
        catch (Exception e) 
        {
            this.isSuccess = false;
            this.errorMessage = e.getMessage();
        }
    }

    // Properly format values for displaying in the frontend
    private String parseJsonValue(String json, String key) 
    {
        try 
        {
            if (json.contains("\"" + key + "\":")) 
            {
                String pattern = "\"" + key + "\":";
                int start = json.indexOf(pattern) + pattern.length();
                if (json.charAt(start) == '"') 
                {
                    start++;
                    int end = json.indexOf("\"", start);
                    return json.substring(start, end);
                } 
                else 
                {
                    int end = json.indexOf(",", start);
                    if (end == -1 || end > json.indexOf("}", start)) {
                        end = json.indexOf("}", start);
                    }
                    return json.substring(start, end).trim();
                }
            }
        } 
        
        // Error message
        catch (Exception e) 
        {
            return "0.0";
        }
        return "N/A";
    }
}