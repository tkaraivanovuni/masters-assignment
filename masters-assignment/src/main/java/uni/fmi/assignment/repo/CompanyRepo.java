package uni.fmi.assignment.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import uni.fmi.assignment.bean.CompanyBean;

public interface CompanyRepo extends JpaRepository<CompanyBean, String>{

}
