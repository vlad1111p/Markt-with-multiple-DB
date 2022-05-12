package enitityManager;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


public class Postgress extends DBManager{

    private static Postgress db;
    static EntityManagerFactory emf;

    private Postgress() {
    }
    public static Postgress getInstance() {
        if (db == null) {
            db = new Postgress();
            emf = Persistence.createEntityManagerFactory("superdupermarkt1");
        }
        return db;
    }

    public EntityManager getEntityManager() {
        getInstance();
        return emf.createEntityManager();
    }

    public void close() {
        emf.close();
    }
}
