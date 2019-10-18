package biz.paluch.redis.intro.standalone.lettuce.datatypes;

import static org.assertj.core.api.AssertionsForInterfaceTypes.*;

import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import biz.paluch.redis.intro.standalone.lettuce.AbstractLettuceTest;

/**
 * @author Mark Paluch
 */
public class TransactionTest extends AbstractLettuceTest {

	private RedisCommands<String, String> commands;

	@BeforeEach
	public void before() throws Exception {

		StatefulRedisConnection<String, String> connection = redisClient.connect();
		connection.sync().flushall();

		commands = connection.sync();
	}

	@Test
	public void transaction() throws Exception {

		commands.multi();
		assertThat(commands.set("key", "value")).isNull();

		RedisCommands<String, String> connection2 = redisClient.connect().sync();
		assertThat(connection2.get("key")).isNull();

		commands.exec();
		assertThat(connection2.get("key")).isEqualTo("value");
	}

	@Test
	public void conditional() throws Exception {

		commands.watch("other-key");
		commands.multi();
		assertThat(commands.set("key", "value")).isNull();

		RedisCommands<String, String> connection2 = redisClient.connect().sync();
		connection2.set("other-key", "value");
		assertThat(connection2.get("key")).isNull();

		commands.exec();
		assertThat(connection2.get("key")).isNull();
	}
}
