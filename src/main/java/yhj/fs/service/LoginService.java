package yhj.fs.service;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import jakarta.servlet.http.HttpSession;

@Service
public class LoginService {

	private final HttpSession HTTP_SESSION;
	
	public LoginService(HttpSession HS) {
		this.HTTP_SESSION = HS;
	}
	
	public String login (String userId, String password, Model model) {
		
		if (userId.equals("admin") && password.equals("1357") ) {
			
			HTTP_SESSION.setAttribute("admin", true);
			return "redirect:/product";
		} else {
			model.addAttribute ("message", "아이디 또는 비밀번호가 잘못 되었습니다. 아이디와 비밀번호를 정확히 입력해 주세요.");
			model.addAttribute ("url", "/");
			return "errorMessage";
		}
	}
}
