package cz.osu.oop3.proj;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cz.osu.oop3.proj.ShopBackofficeApplication;

/**
 * This test was automatically created by spring boot initializer (http://start.spring.io/)
 * <p>
 * It is the first test that should not fail: correct setup is verified by starting up the spring-context and application.
 * 
 * @author Tomáš Rohrbacher (rohrbacher[at]elvoris.cz)
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ShopBackofficeApplication.class)
public class ApplicationStartsOkTest {

	@Test
	public void contextLoads() {
		// Context loads, ok
	}

}