package main.ElectionManager;


import main.ElectionManager.repository.ElectionStatisticsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Date;

@SpringBootApplication
public class MasterApplication {

    public static void main(String[] args) {
        SpringApplication.run(MasterApplication.class, args);
        System.out.println("!!!!!!!!!!!");
        System.out.println(new Date());
        System.out.println("!!!!!!!!!!!");
    }
}
