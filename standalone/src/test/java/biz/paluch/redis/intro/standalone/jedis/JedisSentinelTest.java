package biz.paluch.redis.intro.standalone.jedis;

import static org.assertj.core.api.Assertions.*;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.junit.jupiter.api.Test;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisSentinelPool;

/**
 * @author Mark Paluch
 */
public class JedisSentinelTest {

	@Test
	void connectSentinel() {

		Set<String> sentinels = new HashSet<>();
		sentinels.add("127.0.0.1:26379");
		sentinels.add("127.0.0.1:26380");
		sentinels.add("127.0.0.1:26381");
		JedisSentinelPool pool = new JedisSentinelPool("mymaster", sentinels, new GenericObjectPoolConfig(), 1000);

		try (Jedis jedis = pool.getResource()) {

			assertThat(jedis.ping()).isEqualTo("PONG");
		}

		pool.close();
	}
}
