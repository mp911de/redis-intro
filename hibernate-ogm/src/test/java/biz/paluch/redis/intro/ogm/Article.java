package biz.paluch.redis.intro.ogm;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import java.util.concurrent.TimeUnit;

import lombok.Data;
import org.hibernate.ogm.datastore.redis.options.TTL;

/**
 * @author Mark Paluch
 */
@Entity
@Table(name = "article")
@Data
@TTL(value = 2, unit = TimeUnit.HOURS)
public class Article {

	@Id private String id;

	private String headline;
	private String body;
}
