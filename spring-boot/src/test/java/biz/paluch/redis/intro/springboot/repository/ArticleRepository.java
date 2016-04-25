package biz.paluch.redis.intro.springboot.repository;

import org.springframework.data.repository.CrudRepository;

/**
 * @author Mark Paluch
 */
public interface ArticleRepository extends CrudRepository<Article, String> {

	Article findByHeadline(String headline);
}
