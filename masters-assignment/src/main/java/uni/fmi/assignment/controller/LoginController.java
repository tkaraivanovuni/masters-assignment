package uni.fmi.assignment.controller;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import uni.fmi.assignment.WebSecurityConfiguration;
import uni.fmi.assignment.bean.UserBean;
import uni.fmi.assignment.repo.UserRepo;

@RestController
public class LoginController {
	
	private UserRepo userRepo;
	private WebSecurityConfiguration webSecurityConfiguration;
	
	public LoginController(UserRepo userRepo, WebSecurityConfiguration webSecurityConfiguration) {
		this.userRepo = userRepo;
		this.webSecurityConfiguration = webSecurityConfiguration;
	}
	
	@PostMapping(path = "/register")
	public UserBean registerNewUser (@RequestParam(value = "username")String username, @RequestParam(value = "email")String email, @RequestParam(value = "password")String password, @RequestParam(value = "repeatPassword")String repeatPassword) {
		
		if (password.equals(repeatPassword)) {
			
			UserBean user = new UserBean(username, hashPassword(password), email);
			
			return userRepo.saveAndFlush(user);
			
		}else {
			return null;
		}
	}
	
	
	@PostMapping(path = "/login")
	public String login(@RequestParam(value = "username") String username, @RequestParam(value = "password")String password, HttpSession session) {
		UserBean user = userRepo.findUserByUsernameAndPassword(username, hashPassword(password));
		
		if(user != null) {
			session.setAttribute("user", user);
			
			try {
				UserDetails userDetails = webSecurityConfiguration.userDetailsServiceBean().loadUserByUsername(user.getUsername());
				
				if(userDetails != null) {
					
					Authentication auth = new UsernamePasswordAuthenticationToken(userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities());
					
					SecurityContextHolder.getContext().setAuthentication(auth);
					
					ServletRequestAttributes attr = (ServletRequestAttributes)RequestContextHolder.currentRequestAttributes();
					
					HttpSession http = attr.getRequest().getSession(true);
					
					http.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());
					
				}
				
				return "profile.html";
				
			} catch (UsernameNotFoundException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
			
		return "error.html";
		
	}
	
	@GetMapping(path = "/authorize")
	public ResponseEntity<Integer> authorizeMe(HttpSession session) {
		
		UserBean user = (UserBean) session.getAttribute("user");
		
		if (user != null) {
			return new ResponseEntity<>(user.getId(), HttpStatus.OK);
		}else {
			return new ResponseEntity<>(0, HttpStatus.UNAUTHORIZED);
		}
		
	}
	
	@PostMapping(path = "/logUserOut")
	public ResponseEntity<Boolean> logout(HttpSession session){
		
		UserBean user = (UserBean) session.getAttribute("user");
		
		if(user != null) {
			session.invalidate();
			return new ResponseEntity<>(true, HttpStatus.OK);
		}
		
		return new ResponseEntity<>(false, HttpStatus.UNAUTHORIZED);
		
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
