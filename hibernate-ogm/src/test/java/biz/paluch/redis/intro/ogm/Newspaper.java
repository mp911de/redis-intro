package biz.paluch.redis.intro.ogm;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import lombok.Data;

/**
 * @author Mark Paluch
 */
@Entity
@Table(name = "newspaper")
@Data
public class Newspaper {

	@Id @GeneratedValue private Long id;

	private String name;

	@OneToMany private List<Article> articles = new ArrayList<>();
}
