package uni.fmi.assignment.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import uni.fmi.assignment.bean.TransactionBean;

@Repository
public interface TransactionRepo extends JpaRepository<TransactionBean, Integer>{

}
