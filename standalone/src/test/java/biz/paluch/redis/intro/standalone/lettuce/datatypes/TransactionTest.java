package biz.paluch.redis.intro.standalone.lettuce.datatypes;

import static org.assertj.core.api.AssertionsForInterfaceTypes.*;

import org.junit.gen5.api.BeforeEach;
import org.junit.gen5.api.Test;

import biz.paluch.redis.intro.standalone.lettuce.AbstractLettuceTest;

import com.lambdaworks.redis.api.StatefulRedisConnection;
import com.lambdaworks.redis.api.sync.RedisCommands;

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
		commands.set("key", "value");

		RedisCommands<String, String> connection2 = redisClient.connect().sync();
		assertThat(connection2.get("key")).isNull();

		commands.exec();
		assertThat(connection2.get("key")).isEqualTo("value");
	}

	@Test
	public void conditional() throws Exception {

		commands.watch("other-key");
		commands.multi();
		commands.set("key", "value");

		RedisCommands<String, String> connection2 = redisClient.connect().sync();
		connection2.set("other-key", "value");
		assertThat(connection2.get("key")).isNull();

		commands.exec();
		assertThat(connection2.get("key")).isNull();
	}
}
