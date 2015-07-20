package to.kit.enigma.data.dao;

import org.springframework.stereotype.Component;

import to.kit.enigma.data.model.Category;

@Component
public final class CategoryDao extends AbstractDao<Category> {
//	public void select(String s, CategoryDao d) {
//		EntityManager em = getEntityManager();
//		String sqlString = "select * from category c";
//		List<Category> list = em.createNativeQuery(sqlString).getResultList();
//
//		System.out.println(list.size());
//	}
}
