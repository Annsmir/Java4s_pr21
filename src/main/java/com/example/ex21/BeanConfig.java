package com.example.ex21;

// import com.zaxxer.hikari.HikariDataSource;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
// import org.springframework.orm.hibernate5.HibernateTransactionManager;
// import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
// import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;

@ComponentScan
@Configuration
@EnableAsync
@EnableAspectJAutoProxy
@EnableJpaRepositories
public class BeanConfig {

}
