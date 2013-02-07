package org.jpa.sql.injection.tester.service;

import java.util.List;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.jpa.sql.injection.tester.model.User;

@Stateless
public class UserManager {
	@Inject
	private EntityManager em;
	@Inject
	private Logger log;

	public User getUserByName(String name) {
		TypedQuery<User> query = em.createQuery(
				"SELECT u FROM User u WHERE u.name='" + name + "'", User.class);
		List<User> resultList = query.getResultList();
		log.info("ResultSize: " + resultList.size());
		if (!resultList.isEmpty()) {
			return resultList.get(0);
		}

		return null;
	}
	
	public User getUserByNameWithNamedQuery(String name) {
		TypedQuery<User> query = em.createNamedQuery("User.getByName", User.class);
		query.setParameter("name", name);
		List<User> resultList = query.getResultList();
		log.info("ResultSize: " + resultList.size());
		if (!resultList.isEmpty()) {
			return resultList.get(0);
		}

		return null;
	}

	public void saveUser(String name) {
		User u = new User();
		u.setName(name);
		em.persist(u);
	}
	
	public void deleteUserByName(String name) {
		em.remove(getUserByNameWithNamedQuery(name));
	}

}
