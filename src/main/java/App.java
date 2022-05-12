import enitityManager.MYSQL;
import org.quartz.SchedulerException;
import scanner.MainScanner;

import java.io.IOException;


public class App {

    public static void main(String[] args) throws SchedulerException, IOException{
        MYSQL mysql=MYSQL.getInstance();
//        Postgress postgress=Postgress.getInstance();
        System.out.println();
        MainScanner mainScanner=new MainScanner();
        mainScanner.mainMenu();

    }
}


