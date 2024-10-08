package gerardo.ApiMeteorologica.Data;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class ForecastData {

    @Setter
    @Getter
    public static class Forecast {
        private long dt;
        private MainData main;
        private List<WeatherDescription> weather;
        private WindData wind;
        private String dt_txt;

    }

    @Setter
    @Getter
    public static class MainData {
        private double temp;
        private double feels_like;
        private int pressure;
        private int humidity;

    }

    @Setter
    @Getter
    public static class WeatherDescription {
        private String description;

    }

    @Setter
    @Getter
    public static class WindData {
        private double speed;

    }


    private List<Forecast> list;
    private CityData city;

    @Setter
    @Getter
    public static class CityData {
        private String name;
        private String country;

    }

}
