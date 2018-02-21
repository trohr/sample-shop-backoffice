2OOP3: Project: Tomáš Rohrbacher (R15425)
=====================================

Projekt řešen jako jednoduchý backoffice pro zadávání produktů do prodejního systému.

- Řešeno pomocí frameworku [spring-boot \[1\]][1] za pomocí templatovacího engine [thymeleaf \[2\]][2].
- Přístup k DB je řešen pomocí _spring-boot-starter-data-jpa_ balíčku a je použita in-memory databáze [H2 \[3\]][3].
- Databáze je v současné verzi inicializována pomocí automatické konfigurace spring-boot pro paměťovou (testovací) databázi.
- Přístup na ostrou databázi, včetně konfigurace připojení, není zatím vyřešen ani zdokumentován.
- Projekt je rozdělen na dva submoduly (jeden pro jádro, druhý pro webovou aplikaci). Definice buildu je řešena v Gradle (viz build.gradle) jako multi-module build. 


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

	./gradlew build && java -jar backoffice-webapp/build/libs/shop-backoffice-webapp-0.0.2-SNAPSHOT.jar


Integrace s DB a JPA
--------------------
Pro tento úkol využíváme paměťovou DB **H2** a JPA integraci pomocí **spring-data**.
Postupuji dle návodů na Internetu:
- [Database Persistence with Spring Boot \[4\]][4]
- a [Using the H2 Database Console in Spring Boot with Spring Security \[3\]][3].

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

	public interface ProductDefinitionRepository extends PagingAndSortingRepository<ProductDefinitionJpa, Long>
	{
		List<ProductDefinitionJpa> findProductByName(String name);
		List<ProductDefinitionJpa> findProductByName(String name, Sort sort);
	}

Implementaci takové třídy poskytne spring-data, nám stačí si tuto třídu nechat nainjektovat pomocí IoC a volat metody pro persistenci či zjištění dat.


Modularizace zdrojového kódu (Gradle)
-------------------------------------
Rozdělení na projekty: jeden pro práci s DB (_shop-backoffice-products-core_) a jeden pro prezentaci na web (_shop-backoffice-webapp_).

Definice buildu je řešena v Gradle jako multi-module build. (Viz _settings.gradle_ a _build.gradle_)

	include 'shop-backoffice-products-core'
	include 'shop-backoffice-webapp'

Plný název podmodulů odpovídá 'artifactId' (tedy ve tvaru '`shop-backoffice-<submodule>`').

Zdrojové soubory k podprojektům jsou uvnitř adresářové struktury odkazovány zkráceným názvem
a jsou tedy umístěny v podadresářích  '`./<submodule>`'.


Instrukce ke konfiguraci Gradle multi-modulových projektů lze najít v [návodu pro Gradle \[5\]][5].

Další reference:
+ <https://docs.gradle.org/current/userguide/intro_multi_project_builds.html>
+ <https://docs.gradle.org/current/userguide/multi_project_builds.html>
 

### Deklarované závislosti
- shop-backoffice-products-core:

		// Spring-boot DATA + JPA + JDBC + AOP support
		compile "org.springframework.boot:spring-boot-starter-data-jpa"


- shop-backoffice-webapp:

		// ZAVISLOST NA VNITRNI KNIHOVNE _CORE_
		compile project(':shop-backoffice-products-core') 
		
		// Spring boot WEB
		compile "org.springframework.boot:spring-boot-starter-web"
		// TEMPLATING ENGINE
		compile "org.springframework.boot:spring-boot-starter-thymeleaf"
		
		// SPRING-DATA + JPA support
		compile "org.springframework.boot:spring-boot-starter-data-jpa"
		
		// Concrete DB support
		// INFO: Toto by nikdy neměla být závislost reálného projektu, ale nyní to použijme:
		compile "com.h2database:h2"

#### Definice verzí knihoven
Nechceme specifikovat verze vsech pouzitych a zavislych knihoven sami,
tedy použijeme techniku známou z maven, tzv. import BOM (Bill of Material) balíčku.

Tato technika nám dovolí specifikovat verze použitých knihoven pro balík knihoven najednou,
tedy se spolehnout na provázanost knihoven, která je odladěna, a je ověřeno, že poskytují 
požadovanou a dokumentovanou funkcionalitu.

		// Dependency Management which allows to import the Maven BOM dependencies
		// https://github.com/spring-gradle-plugins/dependency-management-plugin
		apply plugin: 'io.spring.dependency-management'

My v našem projektu budeme používat závislosti, které jsou implicitní pro 
Spring-Boot verze 1.5.7.RELEASE, naimportujeme tedy knihovnu 
`org.springframework.boot:spring-boot-dependencies:1.5.7.RELEASE`.

		// Inklucujeme verze zavislosti, ktere pouziva spring-boot
		// (nemusime je pak explicitne specifikovat)
		// Funkcionalitu dodava gradle plugin 'io.spring.dependency-management'
		dependencyManagement {
			imports {
				mavenBom "org.springframework.boot:spring-boot-dependencies:${springBootVersion}"
			}
		}

### Vize Microservices Architektury
Pokud bychom chtěli mít projekt pro správu definic Produktů jako součást Micro-service architektury, poskytli bychom k DB veřejné API a vystavili jej pomocí REST.

Webová aplikace tak bude implementovat uživatelské rozhraní (UI&UX) pro celý Backend našeho virtuálního šopu (gateway); samotné akce bude provádět pomocí dílčích REST API typu Microservice. Tam právě bude umístěna business logika.

Tímto docílíme nezávislosti dílčích podsystémů systémů a tím např. toho, že výpadek jednoho neohrozí funkci ostatních. Antifragilita aplikace je zde náš cíl.


Reference
---------
[1]: https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/	"Spring Boot Reference"
[2]: http://www.thymeleaf.org/doc/tutorials/3.0/usingthymeleaf.html		"Thymeleaf Usage"
[3]: https://dzone.com/articles/using-the-h2-database-console-in-spring-boot-with   "Using the H2 Database Console in Spring Boot with Spring Security"
[4]: https://springframework.guru/spring-boot-web-application-part-3-spring-data-jpa/ "Database Persistence with Spring Boot"
[5]: https://guides.gradle.org/creating-multi-project-builds/ "Gradle Guides - Creating Multi-project Builds"


- <a name="ref1"></a>\[1\] - Spring Boot Reference :: https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/
- <a name="ref2"></a>\[2\] - Thymeleaf Usage :: http://www.thymeleaf.org/doc/tutorials/3.0/usingthymeleaf.html	
- <a name="ref3"></a>\[3\] - Using the H2 Database Console in Spring Boot with Spring Security :: https://dzone.com/articles/using-the-h2-database-console-in-spring-boot-with  
- <a name="ref4"></a>\[4\] - Database Persistence with Spring Boot :: https://springframework.guru/spring-boot-web-application-part-3-spring-data-jpa/
- <a name="ref5"></a>\[5\] - Gradle Guides - Creating Multi-project Builds :: https://guides.gradle.org/creating-multi-project-builds/
