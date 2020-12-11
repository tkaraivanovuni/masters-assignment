package uni.fmi.assignment.controller;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import uni.fmi.assignment.WebSecurityConfiguration;
import uni.fmi.assignment.bean.UserBean;
import uni.fmi.assignment.repo.UserRepo;

@RestController
public class UserController {
	
	private UserRepo userRepo;
	private WebSecurityConfiguration webSecurityConfiguration;
	
	public UserController(UserRepo userRepo, WebSecurityConfiguration webSecurityConfiguration) {
		this.userRepo = userRepo;
		this.webSecurityConfiguration = webSecurityConfiguration;
	}
	
	@GetMapping(path = "/profile/view")
	public UserBean getUserInfo(HttpSession session) {
		
		UserBean user = (UserBean) session.getAttribute("user");
		
		return user;
	}
	
	@PostMapping(path = "/profile/update")
	public ResponseEntity<Boolean> updateUserInfo(@RequestParam(value = "username")String username, @RequestParam(value = "email")String email, @RequestParam(value = "password")String password, @RequestParam(value = "repeatPassword")String repeatPassword, HttpSession session) {
		
		UserBean user = (UserBean) session.getAttribute("user");
		
		if(user != null) {
			if(password.equals(repeatPassword)) {
				user.setUsername(username);
				user.setEmail(email);
				user.setPassword(hashPassword(password));
				userRepo.saveAndFlush(user);
				return new ResponseEntity<>(true, HttpStatus.OK);
			}else {
				return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
			}
		}else {
			return new ResponseEntity<>(false, HttpStatus.UNAUTHORIZED);
		}
		
	}
	
	private String hashPassword(String password) {
		
		StringBuilder result = new StringBuilder();
		
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			
			md.update(password.getBytes());
			
			byte[] bytes = md.digest();
			
			for(int i = 0; i < bytes.length; i++) {
				result.append((char)bytes[i]);
			}			
			
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}		
	
		return result.toString();
	}
	
}
