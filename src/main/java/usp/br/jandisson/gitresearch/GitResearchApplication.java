package usp.br.jandisson.gitresearch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class GitResearchApplication {

	public static void main(String[] args) {

		SpringApplication springApplication =
				new SpringApplicationBuilder()
						.sources(GitResearchApplication.class)
						.web(false)
						.build();

		springApplication.run(args);

	}
}
