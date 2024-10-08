package gerardo.ApiMeteorologica.Data;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class GeoLocationData {
    private String name;
    private double lat;
    private double lon;
    private String country;

}
