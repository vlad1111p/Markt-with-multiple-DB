package enitityManager;

import javax.persistence.EntityManager;

public abstract class  DBManager {
    public abstract EntityManager getEntityManager();
    public abstract void close();


}
