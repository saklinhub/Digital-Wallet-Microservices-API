package com.wallet.transaction;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class TransactionServiceApplication {

	public static void main(String[] args) {
		String workingDir = System.getProperty("user.dir");
		String serviceDir = "transaction-service";
		String dotenvPath = workingDir.endsWith(serviceDir) ? "." : "./" + serviceDir;

		// Load .env file and set as system properties
		Dotenv.configure()
				.directory(dotenvPath)
				.systemProperties()
				.ignoreIfMissing()
				.load();
		
		SpringApplication.run(TransactionServiceApplication.class, args);
	}

}
