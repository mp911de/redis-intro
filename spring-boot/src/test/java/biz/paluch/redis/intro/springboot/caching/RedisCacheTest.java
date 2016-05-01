package biz.paluch.redis.intro.springboot.caching;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Mark Paluch
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = RedisCacheTest.Config.class)
@WebIntegrationTest
public class RedisCacheTest {

	@Test
	public void runTest() throws Exception {

		System.out.println("curl -i http://localhost:8080/data");
		System.out.println("Press any key to exit...");
		System.in.read();
	}

	@Configuration
	@SpringBootApplication
	static class Config {

		@Bean
		RedisConnectionFactory redisConnectionFactory() {
			return new LettuceConnectionFactory();
		}

		@Bean
		RedisTemplate<Object, Object> redisTemplate() {
			RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<>();
			redisTemplate.setConnectionFactory(redisConnectionFactory());
			return redisTemplate;
		}
	}
}
