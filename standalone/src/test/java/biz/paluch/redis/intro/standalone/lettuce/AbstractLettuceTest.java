package biz.paluch.redis.intro.standalone.lettuce;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

import com.lambdaworks.redis.RedisClient;
import com.lambdaworks.redis.RedisURI;

/**
 * @author Mark Paluch
 */

public class AbstractLettuceTest {

	protected static RedisClient redisClient;

	@BeforeAll
	public static void beforeAll() {
		redisClient = RedisClient.create(RedisURI.create("localhost", 6379));
	}

	@AfterAll
	public static void afterAll() {
		redisClient.shutdown(0, 0, TimeUnit.MILLISECONDS);
	}
}
