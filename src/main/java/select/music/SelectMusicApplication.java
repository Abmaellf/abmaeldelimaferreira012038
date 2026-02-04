package select.music;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@ConfigurationPropertiesScan
@SpringBootApplication
public class SelectMusicApplication {

	public static void main(String[] args) {
		SpringApplication.run(SelectMusicApplication.class, args);
	}
}
