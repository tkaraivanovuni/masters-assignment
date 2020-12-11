package uni.fmi.assignment.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import uni.fmi.assignment.bean.CompanyBean;
import uni.fmi.assignment.bean.TransactionBean;
import uni.fmi.assignment.bean.UserBean;
import uni.fmi.assignment.repo.CompanyRepo;
import uni.fmi.assignment.repo.TransactionRepo;

@RestController
public class TransactionController {
	
	TransactionRepo transactionRepo;
	CompanyRepo companyRepo;
	
	public TransactionController(TransactionRepo transactionRepo, CompanyRepo companyRepo) {
		this.transactionRepo = transactionRepo;
		this.companyRepo = companyRepo;
	}

	@PostMapping(path = "/transaction/add")
	public String addTransaction(@RequestParam(value = "ticker") String ticker, @RequestParam(value = "quantity") int quantity, @RequestParam(value = "amount") double amount, HttpSession session){
		
		UserBean user = (UserBean)session.getAttribute("user");
		List<CompanyBean> companyList = companyRepo.findAll();
		CompanyBean company = new CompanyBean();
		
		for(int i = 0; i < companyList.size(); i++) {
			if(ticker.equals(companyList.get(i).getTicker())) {
				company = companyList.get(i);
			}
		}
		
		if(user != null && company != null) {
			
			TransactionBean transactionBean = new TransactionBean();
			transactionBean.setBuyer(user);
			transactionBean.setCompany(company);
			transactionBean.setQuantity(quantity);
			transactionBean.setAmount(amount);
			
			transactionBean = transactionRepo.saveAndFlush(transactionBean);
			
			if(transactionBean != null){
				return String.valueOf(transactionBean.getId());
			}else {
				return "Error: transaction failed";
			}
		}else {
			return "Error: not logged in";
		}
		
	}
	
	@GetMapping(path = "/transaction/all")
	public List<TransactionBean> getAllTransactions(){
		return transactionRepo.findAll();
	}
	
	@GetMapping(path = "/transaction/search")
	public List<TransactionBean> searchTransactions(@RequestParam(value = "username")String username){
		List<TransactionBean> searchResults = transactionRepo.findAll();
		List<TransactionBean> toBeRemoved = new ArrayList<>();
		
		if(username.equals("")){
			return searchResults;
		}else {
			for(int i = 0; i<searchResults.size();i++) {
				if((searchResults.get(i).getBuyer().getUsername()).contains(username)) {
					continue;
				}else {
					toBeRemoved.add(searchResults.get(i));
				}
			}
			searchResults.removeAll(toBeRemoved);
			return searchResults;
		}
			
	}
	
	@DeleteMapping(path = "/transaction/delete")
	@Secured("ROLE_ARBITER")
	public ResponseEntity<Boolean> deleteTransaction(@RequestParam(value = "id")int id, HttpSession session){
		
		UserBean user = (UserBean)session.getAttribute("user");
		
		if(user == null) {
			return new ResponseEntity<>(false, HttpStatus.UNAUTHORIZED);
		}
		
		Optional<TransactionBean> optionalTransaction = transactionRepo.findById(id);
		
		if(optionalTransaction.isPresent()) {
			TransactionBean transaction = optionalTransaction.get();
			
			transactionRepo.delete(transaction);
			return new ResponseEntity<>(true, HttpStatus.OK);
			
		}else {
			return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
		}
		
	}
	
}
