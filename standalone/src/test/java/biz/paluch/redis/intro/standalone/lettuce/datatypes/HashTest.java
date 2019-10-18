package biz.paluch.redis.intro.standalone.lettuce.datatypes;

import static org.assertj.core.api.AssertionsForInterfaceTypes.*;

import java.util.HashMap;
import java.util.Map;

import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisHashCommands;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import biz.paluch.redis.intro.standalone.lettuce.AbstractLettuceTest;

/**
 * @author Mark Paluch
 */
public class HashTest extends AbstractLettuceTest {

	private RedisHashCommands<String, String> commands;

	@BeforeEach
	public void before() throws Exception {

		StatefulRedisConnection<String, String> connection = redisClient.connect();
		connection.sync().flushall();

		commands = connection.sync();
	}

	@Test
	public void hashAdd() throws Exception {

		commands.hset("key", "field", "value");

		Map<String, String> map = new HashMap<>();
		map.put("field2", "value2");
		map.put("field3", "value3");

		commands.hmset("key", map);

		assertThat(commands.hgetall("key")).containsEntry("field", "value").containsEntry("field2", "value2");
	}

	@Test
	public void get() throws Exception {

		commands.hset("key", "field", "value");

		Map<String, String> map = new HashMap<>();
		map.put("field2", "value2");
		map.put("field3", "value3");

		assertThat(commands.hget("key", "field")).isEqualTo("value");
	}

	@Test
	public void removeElement() throws Exception {

		commands.hset("key", "field", "value");
		commands.hdel("key", "field");

		assertThat(commands.hget("key", "field")).isNull();
	}

	@Test
	public void size() throws Exception {

		commands.hset("key", "field", "value");

		Map<String, String> map = new HashMap<>();
		map.put("field", "value2");
		map.put("field2", "value2");
		map.put("field3", "value3");
		commands.hmset("key", map);

		assertThat(commands.hlen("key")).isEqualTo(3);
	}
}
