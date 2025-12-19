package com.wallet.gateway;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ApiGatewayApplication {

	public static void main(String[] args) {
		String workingDir = System.getProperty("user.dir");
		String serviceDir = "api-gateway";
		String dotenvPath = workingDir.endsWith(serviceDir) ? "." : "./" + serviceDir;
		
		// Load .env file and set as system properties
		Dotenv.configure()
				.directory(dotenvPath)
				.systemProperties()
				.ignoreIfMissing()
				.load();
		
		SpringApplication.run(ApiGatewayApplication.class, args);
	}

}
