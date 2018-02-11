package cz.osu.oop3.proj;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
	Spring Data JPA CRUD Repository
	===============================
	
	The Spring Data JPA CRUD Repository is my favorite feature of Spring Data JPA.
	Similar to coding with a Spring Integration Gateway, you can just define an interface.
	Spring Data JPA uses generics and reflection to generate the concrete implementation 
	of the interface we define.
	
	Defining a repository for our JPA domain class is as simple as defining an interface 
	and extending the CrudRepository interface. You need to declare two classes in the 
	generics for this interface. They are used for the domain class the repository is 
	supporting, and the type of the id declared of the domain class.
	
	For our domain class we can define a Spring Data JPA repository as follows.
 */
@Configuration
@EnableAutoConfiguration
@EntityScan(basePackages = {"cz.osu.oop3.proj.persistence"})
@EnableJpaRepositories(basePackages = {"cz.osu.oop3.proj.persistence"})
@EnableTransactionManagement
public class RepositoryConfiguration
{
	// no explicit configuration, all is spring-boot created
}