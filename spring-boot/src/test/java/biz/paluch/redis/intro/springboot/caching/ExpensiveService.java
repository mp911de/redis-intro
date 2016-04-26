package biz.paluch.redis.intro.springboot.caching;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * @author Mark Paluch
 */
@Service
public class ExpensiveService {

	public String calculateSomethingExpensive() throws InterruptedException {
		Thread.sleep(3000);
		return "42\r\n\r\n";
	}
}
