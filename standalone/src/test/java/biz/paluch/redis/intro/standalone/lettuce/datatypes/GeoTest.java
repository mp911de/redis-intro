package biz.paluch.redis.intro.standalone.lettuce.datatypes;

import static org.assertj.core.api.AssertionsForInterfaceTypes.*;

import java.util.List;
import java.util.Set;

import com.lambdaworks.redis.GeoArgs;
import com.lambdaworks.redis.GeoWithin;
import com.lambdaworks.redis.api.StatefulRedisConnection;
import com.lambdaworks.redis.api.sync.RedisGeoCommands;

import biz.paluch.redis.intro.standalone.lettuce.AbstractLettuceTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author Mark Paluch
 */
public class GeoTest extends AbstractLettuceTest {

	private RedisGeoCommands<String, String> commands;

	@BeforeEach
	public void before() throws Exception {

		StatefulRedisConnection<String, String> connection = redisClient.connect();
		connection.sync().flushall();

		commands = connection.sync();
	}

	@Test
	public void geoAdd() throws Exception {

		commands.geoadd("key", 48.8588376, 2.2773454, "Paris");
		commands.geoadd("key", 47.3774336, 8.4665034, "Zürich");
		commands.geoadd("key", 50.9571608, 6.8268958, "Köln");
	}

	@Test
	public void geoRadius() throws Exception {

		commands.geoadd("key", 48.8588376, 2.2773454, "Paris");
		commands.geoadd("key", 47.3774336, 8.4665034, "Zürich");
		commands.geoadd("key", 50.9571608, 6.8268958, "Köln");

		// Orleans
		Set<String> locations = commands.georadius("key", 47.8733945, 1.841997, 200, GeoArgs.Unit.km);
		assertThat(locations).containsOnly("Paris");
	}

	@Test
	public void geoRadiusWithArgs() throws Exception {

		commands.geoadd("key", 48.8588376, 2.2773454, "Paris");
		commands.geoadd("key", 47.3774336, 8.4665034, "Zürich");
		commands.geoadd("key", 50.9571608, 6.8268958, "Köln");

		// Orleans
		List<GeoWithin<String>> result = commands.georadius("key", 47.8733945, 1.841997, 200, GeoArgs.Unit.km,
				new GeoArgs().withDistance().withCoordinates());

		System.out.println(result);
	}
}
