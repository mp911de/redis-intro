package biz.paluch.redis.intro.springboot.session;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author Mark Paluch
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringSessionTest.Config.class, properties = "server.port=8181",
		webEnvironment = WebEnvironment.DEFINED_PORT)
public class SpringSessionTest {

	@Test
	public void runTest() throws Exception {

		System.out.println("curl -i -b cookies -c cookies http://localhost:8181/session");
		System.out.println("Press any key to exit...");
		System.in.read();
	}

	@SpringBootApplication
	static class Config {

	}
}
