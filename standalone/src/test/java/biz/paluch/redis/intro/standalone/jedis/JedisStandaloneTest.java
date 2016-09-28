package biz.paluch.redis.intro.standalone.jedis;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

import redis.clients.jedis.Jedis;

/**
 * @author Mark Paluch
 */
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
