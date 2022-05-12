package enitityManager;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


public class MYSQL extends DBManager{

    private static MYSQL db;
    static EntityManagerFactory emf;



    private MYSQL() {
    }
    public static MYSQL getInstance() {
        if (db == null) {
            db = new MYSQL();
            emf = Persistence.createEntityManagerFactory("superdupermarkt");
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
