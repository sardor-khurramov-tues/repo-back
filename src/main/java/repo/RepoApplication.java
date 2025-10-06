package repo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class RepoApplication {

	public static void main(String[] args) {
		SpringApplication.run(RepoApplication.class, args);
	}

}
