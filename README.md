2OOP3: Project: Tomáš Rohrbacher (R15425)
=====================================

Projekt řešen jako jednoduchý backoffice pro zadávání produktů do prodejního systému.

Řešeno pomocí frameworku [spring-boot][1] za pomocí templatovacího engine [thymeleaf][2].


Zadání
------
Vypracujte webovou aplikaci, která vede evidenci položek (vyberte si libovolné téma), přičemž musí být splněny následující podmínky:

- aplikace je rozdělena na nejméně dva projekty, jeden pro DB operace (tj. operace nad databází) a jeden pro webové rozhraní (tj. práce s HTML atd.)
- u položek lze provádět operace
    - přidání
    - editace
    - smazání
    - výpis s možností řazení podle vybraného parametru
    - filtrování



Spuštění
--------
Spustit běh aplikace ve vývojovém prostředí lze pomocí příkazu

	./gradlew bootRun

Produkční běh je pomocí uber-jar.

	./gradlew build && java -jar build/libs/shopbackoffice-0.0.1-SNAPSHOT.jar


Integrace s DB a JPA
--------------------
Pro tento úkol využíváme paměťovou DB **H2** a JPA integraci pomocí **spring-data**.
Postupuji dle návodů na Internetu [Database Persistence with Spring Boot][4] a [Using the H2 Database Console in Spring Boot with Spring Security][3].

Integrace do spring-boot projektu je velice jednoduchá:


### IN MEMORY DATABASE
Abychom spustili paměťovou databázi, stačí vložit závislost na knihovně dané paměťové DB do definice _dependencies_ v souboru _build.gradle_.
Spring-boot automaticky rozpozná, že se jedná o testovací DB. (Zde používáme H2, lze také použít další paměťové DB: HSQLDB nebo DERBY).
	
	compile "com.h2database:h2"

Jelikož chci vidět do obsahu databáze, tak přímo do hlavní části aplikace vložím servlet pro správu H2 DB.
(Viz metodu h2servletRegistration() ve třídě KU2Application.java.) Konzole H2 DB je pak dostupná na adrese:

	http://localhost:8080/console/
	

### SPRING-DATA
Při použití projektu _spring-data_ nemusíme specifikovat klasický _persistence.xml_ soubor.
Nastavení na DB se děje pomocí klasických konfiguračních souborů.
Takové soubory mohou být typu _java properties_ _yaml_ a jiných a boot je správně přečte a PersistenceContext nastaví.
My jsme žádné parametry zatím nanastavili, tak se používá default paměťová DB a její default parametry.

Pro přístup k datům stačí vytvořit anotovanou entity třídu (klasická JPA @Entity) a pro ni rozhraní
vzoru Repository. Nemusíme psát implementaci, používat EntityManager, ap.: stačí podědit _spring-data_ CrudRepository:

	public interface StyledTextRepository extends CrudRepository<StyledTextJpa, Integer> {}

Implementaci takové třídy poskytne spring-data, nám stačí si tuto třídu nechat nainjektovat pomocí IoC a volat metody pro persistenci či zjištění dat.


Reference
---------
[1]: https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/	Spring Boot Reference
[2]: http://www.thymeleaf.org/doc/tutorials/3.0/usingthymeleaf.html		Thymeleaf Usage
[3]: https://dzone.com/articles/using-the-h2-database-console-in-spring-boot-with   Using the H2 Database Console in Spring Boot with Spring Security
[4]: https://springframework.guru/spring-boot-web-application-part-3-spring-data-jpa/ Database Persistence with Spring Boot

