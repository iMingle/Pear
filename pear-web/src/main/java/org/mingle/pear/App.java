package org.mingle.pear;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Boot
 */
@SpringBootApplication
@ComponentScan(basePackages = "org.mingle.pear")
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}
