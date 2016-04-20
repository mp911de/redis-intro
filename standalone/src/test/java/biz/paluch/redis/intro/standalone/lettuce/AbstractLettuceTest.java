package biz.paluch.redis.intro.standalone.lettuce;

import org.junit.gen5.api.AfterAll;
import org.junit.gen5.api.BeforeAll;
import org.junit.gen5.junit4.runner.JUnit5;
import org.junit.runner.RunWith;

import com.lambdaworks.redis.RedisClient;
import com.lambdaworks.redis.RedisURI;

/**
 * @author Mark Paluch
 */

@RunWith(JUnit5.class)
public class AbstractLettuceTest {

	protected static RedisClient redisClient;

	@BeforeAll
	public static void beforeAll() {
		redisClient = RedisClient.create(RedisURI.create("localhost", 6379));
	}

	@AfterAll
	public static void afterAll() {
		redisClient = RedisClient.create(RedisURI.create("localhost", 6379));
	}
}
