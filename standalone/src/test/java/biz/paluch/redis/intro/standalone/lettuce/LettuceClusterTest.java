package biz.paluch.redis.intro.standalone.lettuce;

import static org.assertj.core.api.Assertions.*;

import java.util.concurrent.TimeUnit;

import io.lettuce.core.RedisURI;
import io.lettuce.core.cluster.RedisClusterClient;
import io.lettuce.core.cluster.api.StatefulRedisClusterConnection;
import org.junit.jupiter.api.Test;

/**
 * @author Mark Paluch
 */

public class LettuceClusterTest {

	@Test
	void connectCluster() {

		RedisClusterClient clusterClient = RedisClusterClient.create(RedisURI.create("localhost", 7379));

		StatefulRedisClusterConnection<String, String> connection = clusterClient.connect();

		assertThat(connection.sync().ping()).isEqualTo("PONG");

		connection.close();
		clusterClient.shutdown(0, 0, TimeUnit.MILLISECONDS);
	}
}
