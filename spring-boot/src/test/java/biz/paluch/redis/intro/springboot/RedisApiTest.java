package biz.paluch.redis.intro.springboot;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author Mark Paluch
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisApiTest {

	@Autowired RedisTemplate<String, String> redisTemplate;

	@Test
	public void getSet() throws Exception {

		redisTemplate.delete("hash");
		redisTemplate.opsForHash().put("hash", "field", "value");
	}

	@Configuration
	static class Config {

		@Bean
		RedisConnectionFactory redisConnectionFactory() {
			return new LettuceConnectionFactory();
		}

		@Bean
		RedisTemplate<String, String> redisTemplate() {
			RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();
			redisTemplate.setDefaultSerializer(new StringRedisSerializer());
			redisTemplate.setConnectionFactory(redisConnectionFactory());
			return redisTemplate;
		}
	}
}
