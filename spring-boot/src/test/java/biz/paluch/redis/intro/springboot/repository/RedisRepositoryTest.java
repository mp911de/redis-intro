package biz.paluch.redis.intro.springboot.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
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
@SpringBootTest
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

	@SpringBootApplication
	static class Config {

	}

}
