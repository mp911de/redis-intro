package biz.paluch.redis.intro.standalone.jedis;

import static org.assertj.core.api.Assertions.*;

import org.junit.gen5.api.Test;
import org.junit.gen5.junit4.runner.JUnit5;
import org.junit.runner.RunWith;

import redis.clients.jedis.Jedis;

/**
 * @author Mark Paluch
 */
@RunWith(JUnit5.class)
public class JedisStandaloneTest {

	@Test
	void connectStandalone() {

		Jedis jedis = new Jedis("127.0.0.1", 6379);

		assertThat(jedis.ping()).isEqualTo("PONG");

		jedis.close();
	}

	@Test
	void getSet() {

		Jedis jedis = new Jedis("127.0.0.1", 6379);

		jedis.set("key", "value");

		assertThat(jedis.get("key")).isEqualTo("value");

		jedis.close();
	}
}
