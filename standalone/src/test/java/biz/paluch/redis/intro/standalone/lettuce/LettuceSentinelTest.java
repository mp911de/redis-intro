package biz.paluch.redis.intro.standalone.lettuce;

import static org.assertj.core.api.Assertions.*;

import java.util.concurrent.TimeUnit;

import org.junit.gen5.api.Test;
import org.junit.gen5.junit4.runner.JUnit5;
import org.junit.runner.RunWith;

import com.lambdaworks.redis.RedisClient;
import com.lambdaworks.redis.RedisURI;
import com.lambdaworks.redis.RedisURI.Builder;
import com.lambdaworks.redis.api.StatefulRedisConnection;

/**
 * @author Mark Paluch
 */

@RunWith(JUnit5.class)
public class LettuceSentinelTest {

	@Test
	void connectSentinel() {

		RedisURI redisURI = Builder.sentinel("localhost", 26379, "mymaster")
				.withSentinel("localhost", 26380)
				.withSentinel("localhost", 26381)
				.build();

		RedisClient redisClient = RedisClient.create(redisURI);
		StatefulRedisConnection<String, String> connection = redisClient.connect();

		assertThat(connection.sync().ping()).isEqualTo("PONG");

		connection.close();
		redisClient.shutdown(0, 0, TimeUnit.MILLISECONDS);
	}
}
