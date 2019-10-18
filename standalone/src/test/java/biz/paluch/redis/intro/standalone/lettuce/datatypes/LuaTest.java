package biz.paluch.redis.intro.standalone.lettuce.datatypes;

import static org.assertj.core.api.AssertionsForInterfaceTypes.*;

import biz.paluch.redis.intro.standalone.lettuce.AbstractLettuceTest;
import io.lettuce.core.ScriptOutputType;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
	public void storedScripts() throws Exception {

		String script = "return redis.call('set','key','value')";
		String digest = commands.scriptLoad(script);

		commands.evalsha(digest, ScriptOutputType.STATUS);

		RedisCommands<String, String> connection2 = redisClient.connect().sync();
		assertThat(connection2.get("key")).isEqualTo("value");
	}

	@Test
	public void deleteAllKeys() throws Exception {

		commands.set("key", "value");
		commands.set("prefix:a", "value");
		commands.set("prefix:b", "value");

		commands.eval("return redis.call('del', unpack(redis.call('keys', ARGV[1])))", ScriptOutputType.INTEGER,
				new String[0], "prefix:*");

		assertThat(commands.keys("*")).containsOnly("key");
	}
}
