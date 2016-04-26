package biz.paluch.redis.intro.springboot.caching;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Mark Paluch
 */
@Controller
public class ExpensiveResource {

	@Autowired private ExpensiveService expensiveService;

	@RequestMapping(value = "/data")
	public ResponseEntity<String> getData() throws InterruptedException {
		return ResponseEntity.ok(expensiveService.calculateSomethingExpensive());
	}
}
