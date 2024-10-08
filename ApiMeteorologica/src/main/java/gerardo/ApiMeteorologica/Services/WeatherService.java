package gerardo.ApiMeteorologica.Services;

import gerardo.ApiMeteorologica.Consultas.Consulta;
import gerardo.ApiMeteorologica.Consultas.ConsultaRepository;
import gerardo.ApiMeteorologica.Data.AirPollutionData;
import gerardo.ApiMeteorologica.Data.ForecastData;
import gerardo.ApiMeteorologica.Data.GeoLocationData;
import gerardo.ApiMeteorologica.Data.WeatherData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;

@Service
public class WeatherService {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private ConsultaRepository consultaRepository;

    private final String API_KEY = "713f4fb03744431cc82ac056817a7132";
    private final String CURRENT_WEATHER_URL = "http://api.openweathermap.org/data/2.5/weather?q={city}&appid={apiKey}";
    private final String FORECAST_URL = "http://api.openweathermap.org/data/2.5/forecast?q={city}&appid={apiKey}";
    private final String AIR_POLLUTION_URL = "http://api.openweathermap.org/data/2.5/air_pollution?lat={lat}&lon={lon}&appid={apiKey}";
    private final String GEO_URL = "http://api.openweathermap.org/geo/1.0/direct?q={city}&limit=1&appid={apiKey}";

    private final UserService userService;

    public WeatherService(ConsultaRepository consultaRepository, UserService userService) {
        this.consultaRepository = consultaRepository;
        this.userService = userService;
    }

    @Cacheable(value = "currentWeather", key = "#city", unless = "#result == null")
    public WeatherData getCurrentWeather(String city) {
        String solicitud = "Current weather for city: " + city;

        try {
            String url = CURRENT_WEATHER_URL.replace("{city}", city).replace("{apiKey}", API_KEY);
            WeatherData weatherData = restTemplate.getForObject(url, WeatherData.class);

            Integer usuarioId = userService.getCurrentUserId();

            if (weatherData != null) {
                Consulta consulta = new Consulta();
                consulta.setSolicitud(solicitud);
                consulta.setRespuesta(weatherData.toString());
                consulta.setFecha(LocalDateTime.now());
                consulta.setUsuarioId(usuarioId);

                consultaRepository.save(consulta);

                return weatherData;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
    @Cacheable(value = "weatherForecast", key = "#city", unless = "#result == null")
    public ForecastData getForecastWeather(String city) {
        String solicitud = "Forecast for city: " + city;
        try {
            String url = FORECAST_URL.replace("{city}", city).replace("{apiKey}", API_KEY);
            ForecastData forecastData = restTemplate.getForObject(url, ForecastData.class);
            Integer usuarioId = userService.getCurrentUserId();

            if (forecastData != null) {
                Consulta consulta = new Consulta();
                consulta.setSolicitud(solicitud);
                consulta.setRespuesta(forecastData.toString());
                consulta.setFecha(LocalDateTime.now());
                consulta.setUsuarioId(usuarioId);

                consultaRepository.save(consulta);
            }
            return forecastData;
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    @Cacheable(value = "airPollution", key = "#city", unless = "#result == null")
    public AirPollutionData getAirPollutionDataByCity(String city) {
        String solicitud = "City: " + city;

        GeoLocationData geoData = getCoordinatesByCity(city);

        if (geoData != null) {
            String url = AIR_POLLUTION_URL
                    .replace("{lat}", String.valueOf(geoData.getLat()))
                    .replace("{lon}", String.valueOf(geoData.getLon()))
                    .replace("{apiKey}", API_KEY);

            AirPollutionData airPollutionData = restTemplate.getForObject(url, AirPollutionData.class);
            Integer usuarioId = userService.getCurrentUserId();

            if (airPollutionData != null) {
                Consulta consulta = new Consulta();
                consulta.setSolicitud(solicitud);
                consulta.setRespuesta(airPollutionData.toString());
                consulta.setFecha(LocalDateTime.now());
                consulta.setUsuarioId(usuarioId);

                consultaRepository.save(consulta);

                return airPollutionData;
            }
        }
        return null;
    }
    public GeoLocationData getCoordinatesByCity(String city) {
        String url = GEO_URL.replace("{city}", city).replace("{apiKey}", API_KEY);
        GeoLocationData[] response = restTemplate.getForObject(url, GeoLocationData[].class);

        if (response != null && response.length > 0) {
            return response[0];
        }
        return null;
    }
}
