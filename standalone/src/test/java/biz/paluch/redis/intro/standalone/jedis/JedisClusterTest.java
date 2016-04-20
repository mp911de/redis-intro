package biz.paluch.redis.intro.standalone.jedis;

import static org.assertj.core.api.Assertions.*;

import java.io.IOException;

import org.junit.gen5.api.Test;
import org.junit.gen5.junit4.runner.JUnit5;
import org.junit.runner.RunWith;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

/**
 * @author Mark Paluch
 */
@RunWith(JUnit5.class)
public class JedisClusterTest {

	@Test
	void connectCluster() throws IOException {

		JedisCluster jedisCluster = new JedisCluster(new HostAndPort("127.0.0.1", 7379));

		jedisCluster.set("key", "value");
		assertThat(jedisCluster.get("key")).isEqualTo("value");

		jedisCluster.close();
	}

}
