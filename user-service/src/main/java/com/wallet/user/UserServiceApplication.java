package com.wallet.user;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class UserServiceApplication {

	public static void main(String[] args) {
		String workingDir = System.getProperty("user.dir");
		String serviceDir = "user-service";
		String dotenvPath = workingDir.endsWith(serviceDir) ? "." : "./" + serviceDir;

		// Load .env file and set as system properties
		Dotenv.configure()
				.directory(dotenvPath)
				.systemProperties()
				.ignoreIfMissing()
				.load();
		
		SpringApplication.run(UserServiceApplication.class, args);
	}

}
