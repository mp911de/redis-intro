package biz.paluch.redis.intro.standalone.lettuce.datatypes;

import static org.assertj.core.api.Assertions.*;

import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisSetCommands;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import biz.paluch.redis.intro.standalone.lettuce.AbstractLettuceTest;

/**
 * @author Mark Paluch
 */
public class SetTest extends AbstractLettuceTest {

	private RedisSetCommands<String, String> commands;

	@BeforeEach
	public void before() throws Exception {

		StatefulRedisConnection<String, String> connection = redisClient.connect();
		connection.sync().flushall();

		commands = connection.sync();
	}

	@Test
	public void add() throws Exception {

		commands.sadd("key", "item1", "item2", "item3");

		assertThat(commands.smembers("key")).contains("item1", "item2", "item3");
	}

	@Test
	public void contains() throws Exception {

		commands.sadd("key", "item1", "item2", "item3");

		assertThat(commands.sismember("key", "item2")).isTrue();
	}

	@Test
	public void removeElement() throws Exception {

		commands.sadd("key", "item1", "item2", "item3");
		commands.srem("key", "item2");

		assertThat(commands.smembers("key")).containsOnly("item1", "item3");
	}
}
