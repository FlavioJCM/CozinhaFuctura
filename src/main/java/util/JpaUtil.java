package util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import exceptions.NaoConectouComOBanco;

public class JpaUtil {
	
	private static EntityManagerFactory factory;
	
	static {
		try {
			factory = Persistence.createEntityManagerFactory("Cozinha");
		} catch (Exception e) {
			factory = null;
		}
	}
	
	public static EntityManager getEntityManager() throws Exception {
		if (factory == null) {
			throw new NaoConectouComOBanco();
		}
		return factory.createEntityManager();
	}
	
	public static void close() {
		factory.close();
	}
}
