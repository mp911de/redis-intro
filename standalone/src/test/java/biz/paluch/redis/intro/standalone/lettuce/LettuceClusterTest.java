package biz.paluch.redis.intro.standalone.lettuce;

import static org.assertj.core.api.Assertions.*;

import java.util.concurrent.TimeUnit;

import org.junit.gen5.api.AfterAll;
import org.junit.gen5.api.BeforeAll;
import org.junit.gen5.api.Test;
import org.junit.gen5.junit4.runner.JUnit5;
import org.junit.runner.RunWith;

import com.lambdaworks.redis.RedisClient;
import com.lambdaworks.redis.RedisURI;
import com.lambdaworks.redis.RedisURI.Builder;
import com.lambdaworks.redis.api.StatefulRedisConnection;
import com.lambdaworks.redis.api.sync.RedisCommands;
import com.lambdaworks.redis.cluster.RedisClusterClient;
import com.lambdaworks.redis.cluster.api.StatefulRedisClusterConnection;
import com.lambdaworks.redis.resource.ClientResources;
import com.lambdaworks.redis.resource.DefaultClientResources;

/**
 * @author Mark Paluch
 */

@RunWith(JUnit5.class)
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
