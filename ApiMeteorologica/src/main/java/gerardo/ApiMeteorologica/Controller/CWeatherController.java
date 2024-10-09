package gerardo.ApiMeteorologica.Controller;

import gerardo.ApiMeteorologica.Data.AirPollutionData;
import gerardo.ApiMeteorologica.Data.ForecastData;
import gerardo.ApiMeteorologica.Data.WeatherData;
import gerardo.ApiMeteorologica.Services.SimpleRateLimiterService;
import gerardo.ApiMeteorologica.Services.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api/weather")
@RequiredArgsConstructor
public class CWeatherController {

    @Autowired
    private WeatherService weatherService;
    @Autowired
    private SimpleRateLimiterService rateLimiterService;


    @GetMapping("/current")
    public ResponseEntity<WeatherData> getCurrentWeather(@RequestParam String city, Principal principal) {
        String usuarioId = principal.getName();

        if (!rateLimiterService.isAllowed(usuarioId)) {
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body(null);
        }
            try {
                WeatherData weatherData = weatherService.getCurrentWeather(city);
                if (weatherData != null) {
                    return ResponseEntity.ok(weatherData);
                } else {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
                }
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
            }
    }
    @GetMapping("/forecast")
    public ResponseEntity<ForecastData> getForecastWeather(@RequestParam String city, Principal principal) {
        String usuarioId = principal.getName();

        if (!rateLimiterService.isAllowed(usuarioId)) {
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body(null);
        }
        try {
            ForecastData forecastData = weatherService.getForecastWeather(city);
            if (forecastData != null) {
                return ResponseEntity.ok(forecastData);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    @GetMapping("/air-pollution")
    public ResponseEntity<AirPollutionData> getAirPollutionDataByCity(@RequestParam String city, Principal principal) {
        String usuarioId = principal.getName();

        if (!rateLimiterService.isAllowed(usuarioId)) {
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body(null);
        }
        try {
            AirPollutionData airPollutionData = weatherService.getAirPollutionDataByCity(city);
            if (airPollutionData != null) {
                return ResponseEntity.ok(airPollutionData);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}

