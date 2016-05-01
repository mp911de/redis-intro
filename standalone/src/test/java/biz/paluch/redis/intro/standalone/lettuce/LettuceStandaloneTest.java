package biz.paluch.redis.intro.standalone.lettuce;

import static org.assertj.core.api.Assertions.*;

import java.util.concurrent.TimeUnit;

import org.junit.gen5.api.Test;
import org.junit.gen5.junit4.runner.JUnit5;
import org.junit.runner.RunWith;

import com.lambdaworks.redis.RedisClient;
import com.lambdaworks.redis.RedisURI;
import com.lambdaworks.redis.api.StatefulRedisConnection;
import com.lambdaworks.redis.api.sync.RedisCommands;

/**
 * @author Mark Paluch
 */

@RunWith(JUnit5.class)
public class LettuceStandaloneTest {

	@Test
	void connectStandalone() {

		RedisClient redisClient = RedisClient.create(RedisURI.create("redis://localhost:6379"));

		StatefulRedisConnection<String, String> connection = redisClient.connect();

		assertThat(connection.sync().ping()).isEqualTo("PONG");

		connection.close();
		redisClient.shutdown(0, 0, TimeUnit.MILLISECONDS);
	}

	@Test
	void getSet() {

		RedisClient redisClient = RedisClient.create(RedisURI.create("localhost", 6379));

		StatefulRedisConnection<String, String> connection = redisClient.connect();
		RedisCommands<String, String> sync = connection.sync();

		sync.set("key", "value");

		assertThat(sync.get("key")).isEqualTo("value");

		connection.close();
		redisClient.shutdown(0, 0, TimeUnit.MILLISECONDS);
	}
}
