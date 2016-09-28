package biz.paluch.redis.intro.springboot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
import org.springframework.data.redis.support.collections.DefaultRedisList;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author Mark Paluch
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisCollectionTest {

	@Autowired RedisTemplate<String, String> redisTemplate;

	@Test
	public void list() throws Exception {
		redisTemplate.delete("my-list");

		List<String> list = new DefaultRedisList("my-list", redisTemplate);

		list.addAll(Arrays.asList("this", "is", "my", "list"));

		System.out.println(list);
		System.out.println(new ArrayList<>(list));
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
