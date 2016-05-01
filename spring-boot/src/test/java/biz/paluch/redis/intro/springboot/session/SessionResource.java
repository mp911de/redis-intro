package biz.paluch.redis.intro.springboot.session;

import javax.servlet.http.HttpSession;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Mark Paluch
 */
@Controller
public class SessionResource {

	@RequestMapping("/session")
	public ResponseEntity<String> getOrCreateSession(HttpSession session) {

		if (session.getAttribute("salt") == null) {
			session.setAttribute("salt", "" + (int) (Math.random() * 1000));
		}

		return ResponseEntity.ok("" + session.getAttribute("salt") + "\r\n\r\n");
	}
}
