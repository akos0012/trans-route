package hu.cubix.transroute;

import hu.cubix.transroute.service.db.InitDbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TransRouteApplication implements CommandLineRunner {

    @Autowired
    InitDbService initDbService;

    public static void main(String[] args) {
        SpringApplication.run(TransRouteApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        initDbService.clearDB();
        initDbService.insertTestData();
    }
}
