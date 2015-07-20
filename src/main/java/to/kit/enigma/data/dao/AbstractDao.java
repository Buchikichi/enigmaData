package to.kit.enigma.data.dao;

import java.lang.reflect.ParameterizedType;
import java.util.Collection;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.Table;

import to.kit.enigma.data.MyEntityManager;

/**
 * AbstractDao.
 * @author Hidetaka Sasai
 * @param <E> Entity
 */
public abstract class AbstractDao<E> {
	@Resource
	private MyEntityManager myEntityManager;

	protected EntityManager getEntityManager() {
		return this.myEntityManager.getEntityManager();
	}

	protected String getTableName() {
		Class<?> clazz = this.getClass();
		ParameterizedType pt = (ParameterizedType) clazz.getGenericSuperclass();
		Class<?> type = (Class<?>) pt.getActualTypeArguments()[0];
		Table table = type.getAnnotation(Table.class);
		String tableName;

		if (table == null) {
			tableName = type.getSimpleName();
		} else {
			tableName = table.name();
		}
		return tableName;
	}

	public void truncate() {
		EntityManager manager = getEntityManager();
		String qlString = "truncate table " + getTableName();
		Query query = manager.createNativeQuery(qlString);

		query.executeUpdate();
	}

	public void insert(E entity) {
		EntityManager manager = getEntityManager();

		manager.persist(entity);
	}
	public void insert(Collection<E> entities) {
		EntityManager manager = getEntityManager();

		for (E entity : entities) {
			manager.persist(entity);
		}
	}
}
