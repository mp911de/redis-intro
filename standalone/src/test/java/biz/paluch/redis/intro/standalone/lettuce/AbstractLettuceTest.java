package biz.paluch.redis.intro.standalone.lettuce;

import java.util.concurrent.TimeUnit;

import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;


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
