package cz.osu.oop3.proj;

import javax.sql.DataSource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

@SpringBootApplication
public class ShopBackofficeApplication {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(ShopBackofficeApplication.class, args);
	}


	// TESTING SETUP
	// V realne aplikaci by bylo oddeleno na urovni zavislosti src/main na potrebnych knihovnach
	// -- tedy by nebylo zavisle na zadne konkretni implementaci, pouze JDBC-API (a jinych api)
	
//	@Bean
//	public DataSource dataSource() {
//
//		// no need shutdown, EmbeddedDatabaseFactoryBean will take care of this
//		EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
//		EmbeddedDatabase db = builder
//			.setType(EmbeddedDatabaseType.H2) //.HSQLDB .H2 or .DERBY
//			.addDefaultScripts()
////			.addScript("db/sql/create-db.sql")
////			.addScript("db/sql/insert-data.sql")
//			.build();
//		return db;
//	}
//	
	
	/**
	 * Vytvori konzoli pro in-memory H2 databazi a napoji ji na adresu '/console/'
	 *  https://dzone.com/articles/using-the-h2-database-console-in-spring-boot-with
	 */
    @Bean
    ServletRegistrationBean h2servletRegistration(){
        ServletRegistrationBean registrationBean = new ServletRegistrationBean(
        		new org.h2.server.web.WebServlet());
        registrationBean.addUrlMappings("/console/*");
        return registrationBean;
    }

}
