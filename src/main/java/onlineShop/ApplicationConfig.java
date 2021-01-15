package onlineShop;

import java.util.Properties;
import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc //tell SpringMVC to create MVC related default class
public class ApplicationConfig {
//在configure的file里就是你要用的什么其他的library里的东西，通过bean来创建
    @Bean(name = "sessionFactory") //这个session是external class，他是ApplicationConfig的dependency，我们需要在config文件里new出来，初始化，我们要通过@Bean创建
    public LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource());
        sessionFactory.setPackagesToScan("onlineShop.entity");
        sessionFactory.setHibernateProperties(hibernateProperties());
        return sessionFactory;
    }

    @Bean(name = "dataSource")
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        // 只需要修改红色部分, 保留其他内容
        dataSource.setUrl("jdbc:mysql://laiproject-instance.ci2prg9lognb.us-west-2.rds.amazonaws.com:3306/ecommerce1?createDatabaseIfNotExist=true&serverTimezone=UTC");
        dataSource.setUsername("admin");
        dataSource.setPassword("laiofferproject");

        return dataSource;
    }


    private final Properties hibernateProperties() {
        Properties hibernateProperties = new Properties();
        hibernateProperties.setProperty("hibernate.hbm2ddl.auto", "update");
        hibernateProperties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5InnoDBDialect");
        hibernateProperties.setProperty("hibernate.show_sql", "true");
        return hibernateProperties;
    }
}
