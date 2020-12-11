package uni.fmi.assignment.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import uni.fmi.assignment.bean.CompanyBean;
import uni.fmi.assignment.repo.CompanyRepo;

@RestController
public class CompanyController {
	
	private CompanyRepo companyRepo;

	public CompanyController(CompanyRepo companyRepo) {
		this.companyRepo = companyRepo;
	}
	
	@PostMapping(path = "/company/add")
	public CompanyBean addCompany(@RequestParam(value = "ticker")String ticker, @RequestParam(value = "companyName") String companyName) {
		
		CompanyBean company = new CompanyBean(ticker, companyName);
		
		return companyRepo.saveAndFlush(company);
		
	}

}
