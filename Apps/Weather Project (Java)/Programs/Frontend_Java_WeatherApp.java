package Files;// Frontend Java Script for Weather App
// Raghav Ramesh


// import Action Event Settings
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// import Border Settings
import javax.swing.*;
import javax.swing.border.EmptyBorder;


public class Frontend_Java_WeatherApp 
{
    // Private information labels
    private static JLabel cityLabel, tempLabel, condLabel, clothingLabel, alertLabel;

    // Private location fields
    private static JTextField cityField, stateField, countryField;

    public static void main(String[] args) 
    {
        // Start screen
        JFrame frame = new JFrame("Modern Weather App");
        frame.setSize(450, 520);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Set banners
        JPanel mainPanel = new JPanel(new BorderLayout(10, 15));
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Data panel (City, Temperatures, Condition)
        JPanel inputPanel = new JPanel(new GridLayout(0, 2, 5, 5));
        inputPanel.setOpaque(false);

        // Default font
        Font labelFont = new Font("SansSerif", Font.BOLD, 15);
        
        // Default info labels
        JLabel lblCity = new JLabel("City:", SwingConstants.LEFT);
        JLabel lblState = new JLabel("State (Optional):", SwingConstants.LEFT);
        JLabel lblCountry = new JLabel("Country Code (e.g. US):", SwingConstants.LEFT);
        JLabel linkLabel = new JLabel("<html> <a href='' style = 'color: #fff200;'>Look up Country Codes</a></html>", SwingConstants.LEFT);

        // Set interactable text with cursor
        linkLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Detect mouse clicks
        linkLabel.addMouseListener(new java.awt.event.MouseAdapter() 
        {
            public void mouseClicked(java.awt.event.MouseEvent evt) 
            {
                // Redirect user to link
                try 
                {
                    java.awt.Desktop.getDesktop().browse(new java.net.URI("https://en.wikipedia.org/wiki/List_of_ISO_3166_country_codes"));
                } 
                catch (Exception e) 
                {
                    e.printStackTrace();
                }
            }
        });

        // Set font and size for labels
        lblCity.setFont(labelFont);
        lblState.setFont(labelFont);
        lblCountry.setFont(labelFont);
        linkLabel.setFont(labelFont);

        // Show default location for input area
        cityField = new JTextField("Danbury");
        stateField = new JTextField("CT");
        countryField = new JTextField("US");

        // Set font and size for fields
        cityField.setFont(labelFont);
        stateField.setFont(labelFont);
        countryField.setFont(labelFont);

        // Set search button 
        JButton searchButton = new JButton("Get Weather");
        searchButton.setFont(labelFont);

        // Styling inputs
        Color textColor = new Color(248, 249, 250);
        lblCity.setForeground(textColor);
        lblState.setForeground(textColor);
        lblCountry.setForeground(textColor);

        // Row 1: City
        inputPanel.add(lblCity);
        inputPanel.add(cityField);

        // Row 2: State
        inputPanel.add(lblState);
        inputPanel.add(stateField);

        // Row 3: Country
        inputPanel.add(lblCountry);
        inputPanel.add(countryField);

        // Row 4: Country Code Link
        inputPanel.add(linkLabel);

        // Spacers
        inputPanel.add(new JLabel(""));
        inputPanel.add(new JLabel("")); 

        // Row 5: Action Button
        inputPanel.add(searchButton);
        
        // Add input area to the screen
        mainPanel.add(inputPanel, BorderLayout.NORTH);

        // Data Panel (Displaying Weather Results)
        JPanel dataPanel = new JPanel(new GridLayout(4, 1, 5, 5));
        dataPanel.setOpaque(false);
        
        // Set information labels
        cityLabel = new JLabel("Enter location above", SwingConstants.CENTER);
        tempLabel = new JLabel("--°C / --°F", SwingConstants.CENTER);
        condLabel = new JLabel("--", SwingConstants.CENTER);
        clothingLabel = new JLabel("Outfit: --", SwingConstants.CENTER);

        // Set label fonts
        cityLabel.setFont(new Font("SansSerif", Font.BOLD, 22));
        tempLabel.setFont(new Font("SansSerif", Font.BOLD, 32));
        condLabel.setFont(new Font("SansSerif", Font.PLAIN, 18));
        clothingLabel.setFont(new Font("SansSerif", Font.ITALIC, 16));

        // Add information labels
        dataPanel.add(cityLabel);
        dataPanel.add(tempLabel);
        dataPanel.add(condLabel);
        dataPanel.add(clothingLabel);
        
        // Add data panel to main panel
        mainPanel.add(dataPanel, BorderLayout.CENTER);

        // Weather Status Alert Banner
        alertLabel = new JLabel("Status: Awaiting Input", SwingConstants.CENTER);

        // Alert Label's details
        alertLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        alertLabel.setOpaque(true);
        alertLabel.setBackground(new Color(52, 58, 64)); 
        alertLabel.setForeground(new Color(173, 181, 189));
        alertLabel.setBorder(new EmptyBorder(8, 8, 8, 8));
        
        // Set alert banner position
        mainPanel.add(alertLabel, BorderLayout.SOUTH);
        frame.add(mainPanel);

        // Dark Mode Background
        Color darkGray = new Color(33, 37, 41);
        mainPanel.setBackground(darkGray);
        frame.getContentPane().setBackground(darkGray);

        // Set text colors
        cityLabel.setForeground(textColor);
        tempLabel.setForeground(textColor);
        condLabel.setForeground(new Color(173, 181, 189));
        clothingLabel.setForeground(new Color(200, 214, 229));

        // Button Action Logic
        searchButton.addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent event) 
            {
                updateWeatherData();
            }
        }
        );
            
        // Finalize frame
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    // Helper method to parse values out of JSON safely
    private static String getJsonValue(String json, String key) 
    {
        try 
        {
            // Set format for key-value pair
            String pattern = "\"" + key + "\":";

            int start = json.indexOf(pattern) + pattern.length();
            int end = json.indexOf(",", start);

            if (end == -1) 
            {
                end = json.indexOf("}", start);
            }

            String value = json.substring(start, end).trim();
            return value.replace("\"", "");
        } 
        
        // Show error result
        catch (Exception e) 
        {
            return "N/A";
        }
    }

    // Works with the backend constructor and refreshes the interface
    private static void updateWeatherData() 
    {
        try 
        {
            // Proper variables
            String cleanCity = cityField.getText().trim();
            String cleanState = stateField.getText().trim();
            String cleanCountry = countryField.getText().trim();

            // Call constructor
            WeatherBackend backend = new WeatherBackend(cleanCity, cleanState, cleanCountry);

            // Spaced out verification check to see if API retrieval failed
            if (!backend.isSuccess) 
            {
                throw new Exception(backend.errorMessage);
            }

            // Location 
            String city = backend.city;
            String state = backend.state;
            String country = backend.country;

            // Temperature
            double tempC = backend.tempC;
            double tempF = backend.tempF;

            // Weather conditions
            double windSpeed = backend.windSpeed;
            String condition = backend.condition;

            // Update UI components
            if (!state.isEmpty()) 
            {
                cityLabel.setText(city + ", " + state + " (" + country + ")");
            }
            else 
            {
                cityLabel.setText(city + " (" + country + ")");
            }
            
            // Update temperature and conditions
            tempLabel.setText(Math.round(tempC) + "°C / " + Math.round(tempF) + "°F");
            condLabel.setText(condition.toUpperCase());

            // Update icon
            String iconPath = "Icons/clear.png";
            String lowerCondition = condition.toLowerCase();

            // Set icon based on weather condition
            if (lowerCondition.contains("tornado")) 
            {
                iconPath = "Icons/tornado.png";
            }
            else if (lowerCondition.contains("squall") || lowerCondition.contains("wind")) 
            {
                iconPath = "windy.png";
            }
            else if (lowerCondition.contains("cloud")) 
            {
                iconPath = "Icons/clouds.png";
            }
            else if (lowerCondition.contains("rain") || lowerCondition.contains("drizzle") || lowerCondition.contains("thunderstorm")) 
            {
                iconPath = "Icons/rain.png";
            }
            else if (lowerCondition.contains("snow")) 
            {
                iconPath = "Icons/snow.png";
            }
            else if (lowerCondition.contains("clear")) 
            {
                iconPath = "Icons/clear.png";
            }

            // Change icon
            try 
            {
                condLabel.setIcon(new ImageIcon(iconPath));
            }
            catch (Exception imgEx) 
            {
                condLabel.setIcon(null);
            }

            // Update Clothing Recommendation
            String outfit = "Outfit: ";

            // Temperature recommendations

            // Cold
            if (tempC < 10 || tempF < 50) 
            {
                outfit += "🧥 Wear a heavy coat, gloves, and a beanie.";
            }
            // Cool
            else if ((tempC >= 10 && tempC <= 20) || (tempF >= 50 && tempF <= 68)) 
            {
                outfit += "🧥 A light jacket, hoodie, or sweater is perfect.";
            }
            // Hot
            else 
            {
                outfit += "👕 T-shirt, shorts, and sunglasses!";
            }
            
            // Extra clothing recommendations based on weather conditions

            // Rainy
            if (lowerCondition.contains("rain") || lowerCondition.contains("drizzle") || lowerCondition.contains("thunderstorm")) 
            {
                outfit += "\n 🌂 Bring an umbrella! ☔";
            }

            // Snowy
            if (lowerCondition.contains("snow")) 
            {
                outfit += "\n ❄️ Wear waterproof boots!";
            }
            
            // Windy
            if (windSpeed > 10.0 || lowerCondition.contains("squall")) 
            {
                outfit += "\n 💨 Windy conditions, wear a windbreaker!";
            }

            // Sunny and warm
            if (lowerCondition.contains("clear") && (tempC > 25 || tempF > 77)) 
            {
                outfit += "\n 🌞 Stay hydrated and wear sunscreen!";
            }

            // Sunny but cold
            if (lowerCondition.contains("clear") && (tempC < 10 || tempF < 50)) 
            {
                outfit += "\n 🧣 It's clear but cold, wear layers!";
            }

            // Cloudy and warm
            if (lowerCondition.contains("cloud") && (tempC > 25 || tempF > 77)) 
            {
                outfit += "\n 🌤️ Cloudy but warm, light clothing is fine!";
            }

            // Cloudy and cold
            if (lowerCondition.contains("cloud") && (tempC < 10 || tempF < 50)) 
            {
                outfit += "\n ☁️ Cloudy and cold, wear a warm coat!";
            }

            // Natural disasters
            if (lowerCondition.contains("tornado") || lowerCondition.contains("tropical storm") || lowerCondition.contains("hurricane") || lowerCondition.contains("blizzard") || lowerCondition.contains("acid rain") || lowerCondition.contains("flood") || lowerCondition.contains("earthquake"))
            {
                outfit += "\n ⚠️ Emergency safety gear only! Take proper action based on issue!";
            }
            
            clothingLabel.setText(outfit);

            // Update Alert Banner colors
            if (lowerCondition.contains("tornado")) 
            {
                alertLabel.setText("🚨 EMERGENCY: TORNADO WARNING ACTIVE!");
                alertLabel.setBackground(new Color(192, 41, 43));
                alertLabel.setForeground(Color.WHITE);
            }
            else if (lowerCondition.contains("earthquake")) 
            {
                alertLabel.setText("🌎 EMERGENCY: EARTHQUAKE ALERT!");
                alertLabel.setBackground(new Color(192, 41, 43));
                alertLabel.setForeground(Color.WHITE);
            }
            else if (lowerCondition.contains("hail")) 
            {
                alertLabel.setText("❄️ CAUTION: HAIL ACTIVE");
                alertLabel.setBackground(new Color(41, 128, 185));
                alertLabel.setForeground(Color.WHITE);
            }
            else if (lowerCondition.contains("flood")) 
            {
                alertLabel.setText("🌊 CAUTION: FLOOD ALERT");
                alertLabel.setBackground(new Color(41, 128, 185));
                alertLabel.setForeground(Color.WHITE);
            }
            else if (lowerCondition.contains("blizzard")) 
            {
                alertLabel.setText("❄️ CAUTION: BLIZZARD ACTIVE");
                alertLabel.setBackground(new Color(41, 128, 185));
                alertLabel.setForeground(Color.WHITE);
            }
            else if (lowerCondition.contains("hurricane")) 
            {
                alertLabel.setText("🌀 CAUTION: HURRICANE ACTIVE");
                alertLabel.setBackground(new Color(41, 128, 185));
                alertLabel.setForeground(Color.WHITE);
            }
            else if (lowerCondition.contains("tropical storm")) 
            {
                alertLabel.setText("🌀 CAUTION: TROPICAL STORM ACTIVE");
                alertLabel.setBackground(new Color(41, 128, 185));
                alertLabel.setForeground(Color.WHITE);
            }
            else if (lowerCondition.contains("dust") || lowerCondition.contains("sand")) 
            {
                alertLabel.setText("🌪️ CAUTION: DUST/SAND STORM ACTIVE");
                alertLabel.setBackground(new Color(41, 128, 185));
                alertLabel.setForeground(Color.WHITE);
            }
            else if (lowerCondition.contains("rain") || lowerCondition.contains("drizzle")) 
            {
                alertLabel.setText("⚠️ CURRENTLY RAINING OUTSIDE");
                alertLabel.setBackground(new Color(41, 128, 185));
                alertLabel.setForeground(Color.WHITE);
            }
            else if (lowerCondition.contains("thunderstorm")) 
            {
                alertLabel.setText("⚡ CAUTION: THUNDERSTORM ACTIVE");
                alertLabel.setBackground(new Color(41, 128, 185));
                alertLabel.setForeground(Color.WHITE);
            }
            else if (lowerCondition.contains("fog")) 
            {
                alertLabel.setText("🌫️ CURRENTLY FOGGY OUTSIDE");
                alertLabel.setBackground(new Color(149, 165, 200));
                alertLabel.setForeground(Color.WHITE);
            }
            else if (lowerCondition.contains("cloud")) 
            {
                alertLabel.setText("☁️ CURRENTLY CLOUDY OUTSIDE");
                alertLabel.setBackground(new Color(149, 165, 166));
                alertLabel.setForeground(Color.WHITE);
            }
            else if (lowerCondition.contains("snow")) 
            {
                alertLabel.setText("❄️ CURRENTLY SNOWING OUTSIDE");
                alertLabel.setBackground(new Color(108, 117, 125));
                alertLabel.setForeground(Color.WHITE);
            } 
            else if (windSpeed > 10.0 || lowerCondition.contains("squall")) 
            { 
                alertLabel.setText("💨 CAUTION: HIGH WINDS ACTIVE");
                alertLabel.setBackground(new Color(230, 126, 34));
                alertLabel.setForeground(Color.WHITE);
            }
            else if (lowerCondition.contains("clear") && (tempC > 25 || tempF > 77)) 
            {
                alertLabel.setText("🌞 Clear and warm outside, stay hydrated!");
                alertLabel.setBackground(new Color(255, 193, 7));
                alertLabel.setForeground(Color.BLACK);
            } 
            else if (tempC > 32 || tempF > 90) 
            {
                alertLabel.setText("HEATWAVE ALERT");
                alertLabel.setBackground(new Color(255, 193, 7));
                alertLabel.setForeground(Color.BLACK);
            }
            else if (lowerCondition.contains("volcano")) 
            {
                alertLabel.setText("🌋 CAUTION: VOLCANIC ACTIVITY");
                alertLabel.setBackground(new Color(192, 41, 43));
                alertLabel.setForeground(Color.WHITE);
            }
            else if (tempC < 0 || tempF < 32) 
            {
                alertLabel.setText("❄️ FREEZING TEMPERATURES!");
                alertLabel.setBackground(new Color(52, 152, 219));
                alertLabel.setForeground(Color.WHITE);
            } 
            else if (lowerCondition.contains("clear") && (tempC < 10 || tempF < 50)) 
            {
                alertLabel.setText("❄️ Clear but cold outside, dress warmly!");
                alertLabel.setBackground(new Color(52, 152, 219));
                alertLabel.setForeground(Color.WHITE);
            }
            else 
            {
                alertLabel.setText("✓ Calm weather conditions");
                alertLabel.setBackground(new Color(46, 204, 113));
                alertLabel.setForeground(Color.WHITE);
            }

            // Temperature text colors
            if (tempC > 25) 
            {
                tempLabel.setForeground(new Color(255, 159, 67));
            }
            else if (tempC < 10) 
            {
                tempLabel.setForeground(new Color(84, 160, 255));
            }
            else 
            {
                tempLabel.setForeground(new Color(29, 209, 161));
            }
        }

        // Set labels according to errors
        catch (Exception e) 
        {
            e.printStackTrace();
            cityLabel.setText("Location Error");
            tempLabel.setText("!!");
            condLabel.setText("Check spelling / network!");
            clothingLabel.setText("");
            alertLabel.setText("✕ Failed to load data");
            alertLabel.setBackground(new Color(139, 0, 0));
            alertLabel.setForeground(Color.WHITE);
        }
    }
}