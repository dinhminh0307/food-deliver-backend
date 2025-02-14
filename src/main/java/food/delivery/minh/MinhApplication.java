package food.delivery.minh;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling // Enable scheduling
@EnableCaching // Enable caching
public class MinhApplication {

	public static void main(String[] args) {
		SpringApplication.run(MinhApplication.class, args);
	}

}
