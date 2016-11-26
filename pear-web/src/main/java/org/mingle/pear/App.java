package org.mingle.pear;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;

/**
 * Boot
 */
@Controller
@EnableAutoConfiguration
@ComponentScan(basePackages = "org.mingle.pear", excludeFilters = {
        @ComponentScan.Filter(value = Controller.class)
})
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}
