package select.music.music;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController("/api/v1")
public class SelectMusicApplication {

	public static void main(String[] args) {
		SpringApplication.run(SelectMusicApplication.class, args);
	}

	@GetMapping
	public String list() {
		return "Minha API";
	}

}
