package com.service.general.country.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.service.general.country.dto.ResultFinalStatisticsDTO;
import com.service.general.country.dto.ResultInfoDTO;
import com.service.general.country.service.ChallengeService;

@RestController
public class ChallengeController {
	
	private static final String GET_COUNTRY_BY_IP = "/country/findCountry";
	private static final String GET_INFORMATION_STATISTICS= "/country/statistics";
	
	@Autowired
	ChallengeService challengeService; 
	
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@GetMapping(GET_COUNTRY_BY_IP)
	public ResponseEntity<ResultInfoDTO> getCountry(@RequestParam String ipLocation){
        return ResponseEntity.ok(challengeService.getCountryService(ipLocation));
		
	}
	
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@GetMapping(GET_INFORMATION_STATISTICS)
	public ResponseEntity<ResultFinalStatisticsDTO> getStatistics(){
        return ResponseEntity.ok(challengeService.getStatisticsService());
        
		
	}
}
