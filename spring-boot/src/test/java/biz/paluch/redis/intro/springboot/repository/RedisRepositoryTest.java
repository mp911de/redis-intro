package biz.paluch.redis.intro.springboot.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author Mark Paluch
 */
@RunWith(SpringRunner.class)
@SpringBootTest(properties = "server.port=8181", webEnvironment = WebEnvironment.DEFINED_PORT)
public class RedisRepositoryTest {

	@Autowired ArticleRepository articleRepository;

	@Test
	public void save() throws Exception {
		articleRepository.deleteAll();

		Article article = new Article();
		article.setId("breaking-news");
		article.setHeadline("Redis dominates everything");
		article.setBody("Here be dragons");

		articleRepository.save(article);

		Article loaded = articleRepository.findByHeadline("Redis dominates everything");
		System.out.println(loaded);
	}

	@Configuration
	@EnableRedisRepositories
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
