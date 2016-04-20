package biz.paluch.redis.intro.standalone.lettuce.datatypes;

import static org.assertj.core.api.Assertions.*;

import org.junit.gen5.api.BeforeEach;
import org.junit.gen5.api.Test;

import biz.paluch.redis.intro.standalone.lettuce.AbstractLettuceTest;

import com.lambdaworks.redis.api.StatefulRedisConnection;
import com.lambdaworks.redis.api.sync.RedisListCommands;

/**
 * @author Mark Paluch
 */
public class ListTest extends AbstractLettuceTest {

	private RedisListCommands<String, String> commands;

	@BeforeEach
	public void before() throws Exception {
		StatefulRedisConnection<String, String> connection = redisClient.connect();
		connection.sync().flushall();

		commands = connection.sync();

	}

	@Test
	public void listAdd() throws Exception {

		commands.rpush("key", "item1", "item2", "item3");

		assertThat(commands.lrange("key", 0, -1)).contains("item1", "item2", "item3");
	}

	@Test
	public void sublist() throws Exception {

		commands.rpush("key", "item1", "item2", "item3");

		assertThat(commands.lrange("key", 0, 1)).containsOnly("item1", "item2");

	}

	@Test
	public void removeElement() throws Exception {

		commands.rpush("key", "item1", "item2", "item3");
		commands.lrem("key", 1, "item2");

		assertThat(commands.lrange("key", 0, -1)).containsOnly("item1", "item3");

	}

	@Test
	public void size() throws Exception {

		commands.rpush("key", "item1", "item2", "item3");

		assertThat(commands.llen("key")).isEqualTo(3);

	}
}
