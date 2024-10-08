package gerardo.ApiMeteorologica.Data;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class WeatherData {

    @Setter
    @Getter
    public static class WeatherDescription {
        private String description;

    }

    @Setter
    @Getter
    public static class MainData {
        private double temp;
        private int pressure;
        private int humidity;

    }

    @Setter
    @Getter
    public static class WindData {
        private double speed;

    }

    private String name;
    private List<WeatherDescription> weather;
    private MainData main;
    private WindData wind;

}
