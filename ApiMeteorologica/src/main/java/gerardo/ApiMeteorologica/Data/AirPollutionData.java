package gerardo.ApiMeteorologica.Data;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class AirPollutionData {

    private Coord coord;
    private List<Pollution> list;

    @Setter
    @Getter
    public static class Coord {
        private double lon;
        private double lat;

    }

    @Setter
    @Getter
    public static class Pollution {
        private MainData main;
        private Components components;
        private long dt;

    }

    @Setter
    @Getter
    public static class MainData {
        private int aqi;

    }

    @Setter
    @Getter
    public static class Components {
        private double co;
        private double no;
        private double no2;
        private double o3;
        private double so2;
        private double pm2_5;
        private double pm10;
        private double nh3;

    }

}
