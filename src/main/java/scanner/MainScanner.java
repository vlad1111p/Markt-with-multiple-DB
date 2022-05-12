package scanner;

import model.BilligesFleisch;
import model.Kase;
import model.Produkt;
import model.Wein;
import org.quartz.SchedulerException;
import rules.BilligesFleischVerarbeitungsregeln;
import rules.KaseVerarbeitungsregeln;
import rules.WeinVerarbeitungsregeln;
import schedule.BilligesFleischScheduler;
import schedule.KaseScheduler;
import schedule.WeinScheduler;
import service.DBManagerToRepository;
import service.ProduktRepository;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static java.util.Locale.GERMANY;


public class MainScanner {

    private Scanner scanner;

    public MainScanner() {
        this.scanner = new Scanner(System.in);

        scanner.useLocale(GERMANY);

    }

    public void mainMenu() throws SchedulerException, IOException {

        while (true) {
            System.out.println("---------------------------------------------------------------------------");
            System.out.println("Press pd to print all entries of a type by day");
            System.out.println("Press c or create to create a product");
            System.out.println("Press p or printall to print all entries of a type of product");
            System.out.println("Press a to activate schedulers");
            System.out.println("Press q to quit");
            System.out.println("---------------------------------------------------------------------------");
            String s = scanner.next();
            System.out.println(s);
            if (s.equals("q") || s.equals("quit")) break;
            if (s.equals("c") || s.equals("create")) createProduct(scanner);
            if (s.equals("p") || s.equals("printall")) printProducts(scanner);
            if (s.equals("pd")) printProductsByDay(scanner);
            if (s.equals("a")) activateSchedule();


        }
        scanner.close();
    }

    private void activateSchedule() throws SchedulerException {
        KaseScheduler kaseScheduler = new KaseScheduler();
        kaseScheduler.dailyChange();
        BilligesFleischScheduler billigesFleischScheduler = new BilligesFleischScheduler();
        billigesFleischScheduler.dailyChange();
        WeinScheduler weinScheduler = new WeinScheduler();
        weinScheduler.dailyChange();
    }

    private void printProductsByDay(Scanner scanner) throws IOException {
        WeinVerarbeitungsregeln wvr = new WeinVerarbeitungsregeln();
        KaseVerarbeitungsregeln kvr = new KaseVerarbeitungsregeln();
        BilligesFleischVerarbeitungsregeln bfvr = new BilligesFleischVerarbeitungsregeln();
        List<String> listofString = new ArrayList<>();
        theProductsToBeQueried(listofString);
        while (true) {
            System.out.println("---------------------------------------------------------------------------");
            System.out.println("press b to go back");
            System.out.println("Write a number. The daily report from x days from now");
            String s = scanner.next();
            if (s.equals("q") || s.equals("quit") || s.equals("b")) break;
            int days = Integer.parseInt(s);
            for (String e : listofString) {
                DBManagerToRepository dbManagerToRepository = new DBManagerToRepository();
                List<ProduktRepository> dbRepositories = dbManagerToRepository.getAllRepositories();
                for (ProduktRepository pr : dbRepositories) {
                    System.out.println();
                    System.out.println("All the products from database" + pr.getDBClassName());
                    List<Produkt> prodList = pr.getList(e);
                    List<Long> needToBeChanged = new ArrayList<>();
                    productCheck(wvr, kvr, bfvr, days, e, prodList, needToBeChanged);
                    System.out.println();
                    System.out.println("All the products by ID that need attention");
                    System.out.println();
                    for (Long x: needToBeChanged){
                        System.out.print(" " + x + " ");
                    }
                    System.out.println();
                }
                System.out.println();
            }
            System.out.println("---------------------------------------------------------------------------");

        }
    }

    private void theProductsToBeQueried(List<String> listofString) {
        listofString.add("Wein");
        listofString.add("Kase");
        listofString.add("BilligesFleisch");
        //add the product that needs to be queried
    }

    private void productCheck(WeinVerarbeitungsregeln wvr,
                              KaseVerarbeitungsregeln kvr,
                              BilligesFleischVerarbeitungsregeln bfvr,
                              int days,
                              String e,
                              List<Produkt> prodList,
                              List<Long> needToBeChanged)
    {
        for (Produkt prod : prodList) {
            if (e.equals("Wein")) {
                Wein wein = (Wein) prod;
                wein.setQualitat(wein.getQualitat()+ days);

                if (wvr.constraintCkeck(wein)) {
                    System.out.println(wein.toString());
                } else {
                    System.out.println(wein.toString());
                    needToBeChanged.add(wein.getId());
                }
            }
            if (e.equals("Kase")) {
                prod.setQualitat(prod.getQualitat()-days);
                if (kvr.constraintCkeck(prod)) {
                    System.out.println(prod.toString());
                } else {
                    System.out.println(prod.toString());
                    needToBeChanged.add(prod.getId());
                }
            }
            if (e.equals("BilligesFleisch")) {
                prod.setQualitat(prod.getQualitat()-3*days);
                if (bfvr.constraintCkeck(prod)) {
                    System.out.println(prod.toString());
                } else {
                    System.out.println(prod.toString());
                    needToBeChanged.add(prod.getId());
                }
            }
        }
    }

    private void printProducts(Scanner scanner) {
        try {
            while (true) {
                System.out.println("---------------------------------------------------------------------------");
                System.out.println("What type of product do you want to be printed?");
                System.out.println("Wein,Kase or BilligesFleisch");
                System.out.println("press b to go back");
                System.out.println("---------------------------------------------------------------------------");
                String s = scanner.next();
                if (s.equals("q") || s.equals("quit") || s.equals("b")) break;
                DBManagerToRepository dbManagerToRepository = new DBManagerToRepository();
                for (ProduktRepository pr : dbManagerToRepository.getAllRepositories()) {
                    pr.printAllFromTable(s);
                }
            }
        } catch (Exception e) {
            System.out.println("error please write again");
        }
    }

    private void createProduct(Scanner scanner) {
        try {
            while (true) {
                System.out.println("---------------------------------------------------------------------------");
                System.out.println("Which product do you want to create?");
                System.out.println("Wein,Kase or BilligesFleisch");
                System.out.println("press b to go back");
                System.out.println("---------------------------------------------------------------------------");
                String s = scanner.next();
                if (s.equals("q") || s.equals("quit") || s.equals("b")) break;
                System.out.println("Please write the quality");
                int quality = Integer.parseInt(scanner.next());
                System.out.println("Please write the price");
                double price = Double.parseDouble(scanner.next());
                System.out.println("Please write the description");
                String description = scanner.next();
                System.out.println("Please write the verfallsdatum such as 20230223");
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
                LocalDate date1 = LocalDate.parse(scanner.next(), formatter);
                LocalDateTime date = date1.atStartOfDay();
                if (s.contains("Wein") || s.contains("wein")) {
                    System.out.println("Please write yes or no if it is placed in the shelf");
                    String shelf = scanner.next();
                    if (shelf.contains("yes") || shelf.contains("y")) {
                        Wein newWein = new Wein(quality, description, date, price, true);
                        addNewProd(newWein);
                    } else {
                        Wein newWein = new Wein(quality, description, date, price, false);
                        addNewProd(newWein);
                    }
                }
                if (s.contains("Kase") || s.contains("kase")) {
                    Kase kase = new Kase(quality, description, price, date);
                    addNewProd(kase);
                }
                if (s.contains("BilligesFleisch") || s.contains("billigesFleisch")) {
                    BilligesFleisch billigesFleisch = new BilligesFleisch(quality, description, price, date);
                    addNewProd(billigesFleisch);
                }
            }
        } catch (Exception e) {
            System.out.println("error please write again");
        }
    }

    private void addNewProd(Produkt prod) throws IOException {
        DBManagerToRepository dbManagerToRepository = new DBManagerToRepository();
        for (ProduktRepository pr : dbManagerToRepository.getAllRepositories()) {
            pr.save(prod);
        }
    }
}
