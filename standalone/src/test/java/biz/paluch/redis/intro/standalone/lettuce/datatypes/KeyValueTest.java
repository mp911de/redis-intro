package biz.paluch.redis.intro.standalone.lettuce.datatypes;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.lambdaworks.redis.api.StatefulRedisConnection;
import com.lambdaworks.redis.api.sync.RedisCommands;

import biz.paluch.redis.intro.standalone.lettuce.AbstractLettuceTest;

/**
 * @author Mark Paluch
 */
public class KeyValueTest extends AbstractLettuceTest {

	private RedisCommands<String, String> commands;

	@BeforeEach
	public void before() throws Exception {

		StatefulRedisConnection<String, String> connection = redisClient.connect();
		connection.sync().flushall();

		commands = connection.sync();
	}

	@Test
	public void set() throws Exception {

		commands.set("key", "value");

		assertThat(commands.get("key")).isEqualTo("value");
	}

	@Test
	public void getSet() throws Exception {

		commands.set("key", "value");

		assertThat(commands.getset("key", "value2")).isEqualTo("value");
	}

	@Test
	public void incr() throws Exception {

		commands.set("key", "1");

		assertThat(commands.incr("key")).isEqualTo(Long.valueOf(2));
	}

	@Test
	public void expire() throws Exception {

		commands.set("key", "value");

		int seconds = 5;
		commands.expire("key", seconds);

		assertThat(commands.ttl("key")).isGreaterThan(seconds - 1);
	}
}
