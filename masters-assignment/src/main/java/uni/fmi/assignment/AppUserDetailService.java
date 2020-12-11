package uni.fmi.assignment;

import java.util.Set;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import uni.fmi.assignment.bean.RoleBean;
import uni.fmi.assignment.bean.UserBean;
import uni.fmi.assignment.repo.UserRepo;

@Service
public class AppUserDetailService implements UserDetailsService{
	
	private UserRepo userRepo;

	public AppUserDetailService(UserRepo userRepo) {
		this.userRepo = userRepo;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		UserBean user = userRepo.findUserByUsername(username);
		
		if(user == null) {
			throw new UsernameNotFoundException(username);
		}
		
		Set<RoleBean> userRoles = user.getRoles();
		
		return new UserPrincipal(user, userRoles);
	}

}
