package yhj.fs.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import yhj.fs.service.LoginService;





@Controller
public class LoginController {
	
	private final LoginService LOGIN_SERVICE;
	
	public LoginController(LoginService LS) {
		this.LOGIN_SERVICE = LS;
	}

	
	@GetMapping("/")
	public String LandingPage(HttpSession session) {

		if(session.getAttribute("admin") != null) {
			return "redirect:/product";
		}
		
		return "login/login";
	}
	
	@PostMapping("/")
	public String mainPage(@RequestParam String userId, @RequestParam String userPassword, 
			Model model) {
		
		return LOGIN_SERVICE.login(userId, userPassword, model);
	}
	
	@GetMapping("/logout")
	public String logout (HttpSession session) {
		session.invalidate ();
		return "redirect:/";
	}
	
}
