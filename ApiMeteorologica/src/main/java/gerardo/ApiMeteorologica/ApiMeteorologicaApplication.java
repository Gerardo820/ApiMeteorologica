package gerardo.ApiMeteorologica;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class ApiMeteorologicaApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiMeteorologicaApplication.class, args);
	}

}
