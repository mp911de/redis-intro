package biz.paluch.redis.intro.ogm;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * @author Mark Paluch
 */
public class NewspaperTest {

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
	public void createAndStore() throws Exception {

		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();

		Newspaper newspaper = new Newspaper();
		newspaper.setName("Redis Tribune");

		Article article = new Article();
		article.setId("article 1");
		article.setHeadline("Redis dominates everything");
		article.setBody("Here be dragons");
		entityManager.persist(article);

		newspaper.getArticles().add(article);

		entityManager.persist(newspaper);

		entityManager.getTransaction().commit();
		entityManager.close();
	}
}
