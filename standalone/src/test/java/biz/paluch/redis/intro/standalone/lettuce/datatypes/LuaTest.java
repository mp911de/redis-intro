package biz.paluch.redis.intro.standalone.lettuce.datatypes;

import static org.assertj.core.api.AssertionsForInterfaceTypes.*;

import org.junit.gen5.api.BeforeEach;
import org.junit.gen5.api.Test;

import biz.paluch.redis.intro.standalone.lettuce.AbstractLettuceTest;

import com.lambdaworks.redis.ScriptOutputType;
import com.lambdaworks.redis.api.StatefulRedisConnection;
import com.lambdaworks.redis.api.sync.RedisCommands;
import com.lambdaworks.redis.api.sync.RedisScriptingCommands;

/**
 * @author Mark Paluch
 */
public class LuaTest extends AbstractLettuceTest {

	private RedisCommands<String, String> commands;

	@BeforeEach
	public void before() throws Exception {
		StatefulRedisConnection<String, String> connection = redisClient.connect();
		connection.sync().flushall();

		commands = connection.sync();
	}

	@Test
	public void simpleLua() throws Exception {

		commands.eval("return redis.call('set','key','value')", ScriptOutputType.STATUS);

		RedisCommands<String, String> connection2 = redisClient.connect().sync();
		assertThat(connection2.get("key")).isEqualTo("value");
	}

	@Test
	public void deleteAllKeys() throws Exception {

		commands.set("key", "value");
		commands.set("prefix:a", "value");
		commands.set("prefix:b", "value");

		commands.eval("return redis.call('del', unpack(redis.call('keys', ARGV[1])))",
				ScriptOutputType.INTEGER,
				new String[0],
				"prefix:*");

		assertThat(commands.keys("*")).containsOnly("key");
	}
}
