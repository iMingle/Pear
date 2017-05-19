package org.mingle.pear.config;

import org.apache.catalina.startup.Tomcat;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 容器配置
 *
 * @author mingle
 */
@Configuration
public class ContainerConfig {
    @Bean
    public EmbeddedServletContainerFactory servletContainer() {
        return new TomcatEmbeddedServletContainerFactory() {
            @Override
            protected TomcatEmbeddedServletContainer getTomcatEmbeddedServletContainer(Tomcat tomcat) {
                tomcat.addUser("admin", "admin");
                tomcat.addRole("admin", "manager-gui");

                return super.getTomcatEmbeddedServletContainer(tomcat);
            }
        };
    }
}
