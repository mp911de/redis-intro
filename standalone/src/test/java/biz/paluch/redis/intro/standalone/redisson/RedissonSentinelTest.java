package biz.paluch.redis.intro.standalone.redisson;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.redisson.Redisson;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

/**
 * @author Mark Paluch
 */
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
