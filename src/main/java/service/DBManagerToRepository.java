package service;

import enitityManager.DBManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class DBManagerToRepository {

    enitityManager.MYSQL MYSQL =
            enitityManager.MYSQL.getInstance();
//    enitityManager.Postgress Postgress =
//            enitityManager.Postgress.getInstance();

    List<DBManager> dBManagerList = new ArrayList<>();

    public DBManagerToRepository() throws IOException {

        dBManagerList.add(MYSQL);
//       dBManagerList.add(Postgress);


    }

//    public ProduktRepository getPostgressRepository() {
//        return new ProduktRepository(Postgress);
//    }

    public ProduktRepository getMYSQLRepository() {
        return new ProduktRepository(MYSQL);
    }

    public List<ProduktRepository> getAllRepositories() {
        List<ProduktRepository> repos = new ArrayList<ProduktRepository>();
        for (DBManager db : dBManagerList) {
            repos.add(new ProduktRepository(db));
        }
        return repos;
    }

    public List<DBManager> getAllDB() {
        return dBManagerList;
    }

    public void closeAllConnections() {
        dBManagerList.forEach(e -> e.close());
    }

}
