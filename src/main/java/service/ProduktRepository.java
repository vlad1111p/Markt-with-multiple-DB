package service;


import enitityManager.DBManager;
import model.Produkt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.List;


@Transactional
public class ProduktRepository {

    private final static Logger logger = LoggerFactory.getLogger(ProduktRepository.class);
    @PersistenceContext(type = PersistenceContextType.EXTENDED)
    private EntityManager entityManager;
    private DBManager dbManager;
    public ProduktRepository(DBManager dbManager) {
        this.entityManager = dbManager.getEntityManager();
        this.dbManager=dbManager;
    }

    public synchronized void save(Produkt produkt) {

        EntityTransaction transaction = entityManager.getTransaction();
        try {
            if (!transaction.isActive())
                transaction.begin();
            Produkt merge = entityManager.merge(produkt);
            entityManager.lock(merge, LockModeType.PESSIMISTIC_WRITE);
            transaction.commit();
        } catch (Exception e) {
//            e.printStackTrace();
            transaction.rollback();
        } finally {
        }
    }

    public synchronized void deleteByObject(Produkt produkt) throws InterruptedException {
        Produkt produkt1 = entityManager.find(produkt.getClass(), produkt.getId());
        EntityTransaction transaction = entityManager.getTransaction();
        if (!transaction.isActive())
            transaction.begin();
        entityManager.remove(produkt1);
        transaction.commit();
    }

    public synchronized void printAllFromTable(String entityName) {

        EntityTransaction transaction = entityManager.getTransaction();
        try {
            if (!transaction.isActive())
                transaction.begin();
            Query q = entityManager.createQuery("select s from " + entityName + " s");
            List<Produkt> resultList = q.getResultList();
            System.out.println("num of : " + entityName + " " + resultList.size());
            for (Produkt next : resultList) {
                System.out.println(next);
            }
        } catch (Exception e) {
//            e.printStackTrace();
           transaction.rollback();
        } finally {
        }
    }

    public synchronized List<Produkt> getList(String entityName) {

        EntityTransaction transaction = entityManager.getTransaction();
        try {
            if (!transaction.isActive())
                transaction.begin();
            Query q = entityManager.createQuery("select s from " + entityName + " s");
            List resultList = q.getResultList();
            return resultList;
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
        } finally {
        }
        return null;
    }

    public synchronized Produkt getById(String entityName, long id) {

        EntityTransaction transaction = entityManager.getTransaction();
        try {
            Query q = entityManager.createQuery("select s from " + entityName + " s where s.id=" + id);

            return (Produkt) q.getResultList().get(0);
        } catch (Exception e) {
//            e.printStackTrace();
            transaction.rollback();
        } finally {
        }
        return null;
    }

    public void close() {
        entityManager.close();
    }

    public String getDBClassName(){
        return this.dbManager.getClass().getSimpleName();
    }
}
