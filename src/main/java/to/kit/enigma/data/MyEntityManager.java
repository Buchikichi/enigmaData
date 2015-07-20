package to.kit.enigma.data;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.springframework.stereotype.Component;

@Component
public final class MyEntityManager {
	private static final String PERSISTENCE_UNIT_NAME = "enigmaData";

	private EntityManager manager;

	public EntityManager getEntityManager() {
		if (this.manager == null) {
			EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);

			this.manager = emf.createEntityManager();
		}
		return this.manager;
	}
}
