package biz.paluch.redis.intro.standalone.redisson;

import static org.assertj.core.api.Assertions.*;

import org.junit.gen5.api.Test;
import org.junit.gen5.junit4.runner.JUnit5;
import org.junit.runner.RunWith;
import org.redisson.Config;
import org.redisson.Redisson;
import org.redisson.RedissonClient;
import org.redisson.core.RMap;

/**
 * @author Mark Paluch
 */
@RunWith(JUnit5.class)
public class RedissonSentinelTest {

	@Test
	void connectSentinel() {

		Config config = new Config();
		config.useSentinelServers()
				.addSentinelAddress("127.0.0.1:26379", "127.0.0.1:26380", "127.0.0.1:26380")
				.setMasterName("mymaster");

		RedissonClient redisson = Redisson.create(config);

		RMap<Object, Object> map = redisson.getMap("map");
		map.put("key", "value");

		assertThat(map).containsEntry("key", "value");

		redisson.shutdown();
	}
}
