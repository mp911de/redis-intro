package biz.paluch.redis.intro.standalone.lettuce;

import static org.assertj.core.api.Assertions.*;

import java.util.concurrent.TimeUnit;

import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import org.junit.jupiter.api.Test;


/**
 * @author Mark Paluch
 */
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
