# Forecast
This application is a weather forecast application which is buit with MVVM architecture using the APIs available from openweathermap. This application includes Google places implimention to select the place for which the weather forecast is needed. A mapview of the selected location will be displayed in the background of the screen.

## Getting Started

### Clone the Repository

As usual, you get started by cloning the project to your local machine:

## Prerequisites
1. Create a project in https://console.developers.google.com/ and enable maps and places libraries.
2. Create an API key to access maps and places. 
3. Sign in to https://openweathermap.org/ and create an API key.
```
https://console.developers.google.com/
https://openweathermap.org/
```


### Open and Run Project in Android Studio

Now that you have cloned the repo:

1. Open the project up in Android Studio.

2. Copy the API key from Google developer console and replace it with the key "google_maps_key" in strings.xml.

3. Copy the API key from Openweathermap console and replace it with the key "weather_forecast_id" in strings.xml.

4. Clean the Project. 
	
At this point, you *should* be able to build and run the project in the Android device or emulator.


## Android Version Targeting

App is currently built to work with Android API 28. **However**, minimum SDK support is 21.

## Enhancements if needed. 

1. Show the forecast by getting the users current location. 
2. Option can be provided to display multiple locations forecasts, so the user can see the forecast of multiple locations on Dashboard.
3. Option for the user to select Favourite locations.
4. We can include a switch to select between Degrees and Farenheit. 
5. Viewpager on the details screen so that the user can swipe through the pages. 
6. Integrate push notifications to send importatnt notifications based on the user favourite locations. 
7. Graphical representation of hourly forecast. 
8. Navigating the user to indepth details screen showing all the parameters given by the API.
9. Retain the last API response and show to the user even when internet is not available. 
10. Implement Data Binding. 
11. Improve user experience and design. 

