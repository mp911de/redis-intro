package biz.paluch.redis.intro.ogm;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 * @author Mark Paluch
 */
public class ArticleTest {

	private static EntityManagerFactory entityManagerFactory;

	@BeforeAll
	public static void beforeAll() {
		entityManagerFactory = Persistence.createEntityManagerFactory("redisPU");
	}

	@AfterAll
	public static void afterAll() {
		entityManagerFactory.close();
	}

	@Test
	@Disabled
	public void createAndStore() throws Exception {

		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();

		Article article = new Article();
		article.setId("breaking-news");
		article.setHeadline("Redis dominates everything");
		article.setBody("Here be dragons");
		entityManager.persist(article);

		entityManager.getTransaction().commit();
		entityManager.close();
	}

	@Test
	public void getArticle() throws Exception {

		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();

		Article article = entityManager.find(Article.class, "breaking-news");

		System.out.println(article);

		entityManager.getTransaction().commit();
		entityManager.close();
	}
}
