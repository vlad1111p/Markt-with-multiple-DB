package schedule;

import model.Produkt;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import rules.KaseVerarbeitungsregeln;
import service.DBManagerToRepository;
import service.ProduktRepository;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;

public class KaseJob implements Job {
    @Transactional
    @Override
    public synchronized void  execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        KaseVerarbeitungsregeln kaseVerarbeitungsregeln = new KaseVerarbeitungsregeln();
        DBManagerToRepository dbManagerToRepository = null;
        try {
            dbManagerToRepository = new DBManagerToRepository();
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<ProduktRepository> dbRepositories = dbManagerToRepository.getAllRepositories();
        for (ProduktRepository externalInstance : dbRepositories) {
        List<Produkt> kaseList = externalInstance.getList("Kase");
        for (Produkt prod : kaseList) {
            prod.setQualitat(prod.getQualitat() - 1);
            if (kaseVerarbeitungsregeln.constraintCkeck(prod)) {
                externalInstance.save(prod);
            } else {
                try {
                    externalInstance.deleteByObject(prod);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        }
    }

}
//    ProduktRepository externalInstance =
//            (ProduktRepository) schedulerContext.get("ProduktRepository");