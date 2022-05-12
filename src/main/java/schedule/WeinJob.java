package schedule;

import model.Produkt;
import model.Wein;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import rules.WeinVerarbeitungsregeln;
import service.DBManagerToRepository;
import service.ProduktRepository;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;

public class WeinJob implements Job {


    @Transactional
    @Override
    public synchronized void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        WeinVerarbeitungsregeln weinVerarbeitungsregeln = new WeinVerarbeitungsregeln();

        DBManagerToRepository dbManagerToRepository = null;
        try {
            dbManagerToRepository = new DBManagerToRepository();
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<ProduktRepository> dbRepositories = dbManagerToRepository.getAllRepositories();
        for (ProduktRepository externalInstance : dbRepositories) {

            List<Produkt> weinList = externalInstance.getList("Wein");
            for (Produkt prod : weinList) {
                Wein wein = (Wein) prod;
                if (!wein.isRegal()) {
                    if (prod.getQualitat() < 50) {
                        prod.setQualitat(prod.getQualitat() + 1);
                    } else {
                        prod.setQualitat(50);
                    }
                    if (weinVerarbeitungsregeln.constraintCkeck(prod)) {
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
}
//    ProduktRepository externalInstance =
//            (ProduktRepository) schedulerContext.get("ProduktRepository");