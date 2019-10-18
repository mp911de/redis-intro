package biz.paluch.redis.intro.standalone.lettuce;

import static org.assertj.core.api.Assertions.*;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;

import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulRedisConnection;


/**
 * @author Mark Paluch
 */

public class LettuceSentinelTest {

	@Test
	void connectSentinel() {

		RedisURI redisURI = RedisURI.Builder.sentinel("localhost", 26379, "mymaster").withSentinel("localhost", 26380)
				.withSentinel("localhost", 26381).build();

		RedisClient redisClient = RedisClient.create(redisURI);
		StatefulRedisConnection<String, String> connection = redisClient.connect();

		assertThat(connection.sync().ping()).isEqualTo("PONG");

		connection.close();
		redisClient.shutdown(0, 0, TimeUnit.MILLISECONDS);
	}
}
