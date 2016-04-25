package biz.paluch.redis.intro.springboot.repository;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

/**
 * @author Mark Paluch
 */
@Data
@RedisHash("article")
public class Article {

	@Id
	private String id;

	@Indexed
	private String headline;
	private String body;
}
