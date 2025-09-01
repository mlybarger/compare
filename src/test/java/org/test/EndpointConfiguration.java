package org.test;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import javax.sql.DataSource;

@Configuration
public class EndpointConfiguration {

	@Value("${db.url}")
	private String url;

	@Value("${db.username}")
	private String username;

	@Value("${db.password}")
	private String password;

	@Bean(name = "encryptedDS")
	public DataSource dataSource() {
		// Create a new BasicDataSource instance.
		BasicDataSource dataSource = new BasicDataSource();

		dataSource.setDriverClassName("org.postgresql.Driver");
		dataSource.setUrl(url);
		dataSource.setUsername(username);
		dataSource.setPassword(decrypt(password)); // 'changeme");
		dataSource.setInitialSize(5);
		dataSource.setMaxTotal(20);

		return dataSource;
	}

	private String decrypt(String encodedValue) {
		try {
			byte[] decodedBytes = Base64.getDecoder().decode(encodedValue);
			String decodedString = new String(decodedBytes, StandardCharsets.UTF_8).trim();
			return decodedString;
		} catch (IllegalArgumentException e) {
			System.err.println("Error decoding Base64 password: " + e.getMessage());
			return null;
		}
	}

}
