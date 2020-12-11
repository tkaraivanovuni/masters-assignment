package uni.fmi.assignment.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import uni.fmi.assignment.bean.UserBean;

@Repository
public interface UserRepo extends JpaRepository<UserBean, Integer>{
	
	UserBean findUserByUsernameAndPassword(String username, String password);
		
	UserBean findUserByUsername(String username);

}
