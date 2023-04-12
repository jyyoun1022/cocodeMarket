package cocode.cocodeMarket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class CocodeMarketApplication {

	public static void main(String[] args) {
		SpringApplication.run(CocodeMarketApplication.class, args);
	}

}
