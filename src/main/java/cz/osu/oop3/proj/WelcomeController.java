package cz.osu.oop3.proj;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/")
public class WelcomeController {

	// 1) Welcome page
	@GetMapping
	public String getWelcomePage ()
	{
		return "welcome";
	}

}
