package com.service.general.country.service;

import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.service.general.country.dto.BasicCountriesDTO;
import com.service.general.country.dto.GeneralCountryDTO;
import com.service.general.country.dto.ResultInfoDTO;
import com.service.general.country.factory.ResultInfoFactory;

@Service
public class ChallengeService {
	
	@Autowired
	private ResultInfoFactory resultInfoFactory;
	
	public ResultInfoDTO getCountryService(String ipLocation) {
		
		ResultInfoDTO res = new ResultInfoDTO();
		
		//Primero se procede a consumir el servicio para Geolocalización de IPs
		RestTemplate restTemplate = new RestTemplate();
		BasicCountriesDTO summaryCountry = restTemplate.getForObject("https://api.ip2country.info/ip?"+ipLocation, BasicCountriesDTO.class);
		
		//Procedemos a llamar el método del consumo de servicio para la información del pais de la IP consultada
		GeneralCountryDTO[] infoCountry = getInformationCountryByNameCountry(summaryCountry);
		GeneralCountryDTO[] infoCountryBa = getInformationCountryByNameCity("Buenos Aires");
		
		res = resultInfoFactory.newResultInfo(infoCountry, ipLocation, infoCountryBa);
		
		
		
        
        return res;
        
        
	}
	
	public GeneralCountryDTO[] getInformationCountryByNameCountry(BasicCountriesDTO summaryCountry){
		RestTemplate restTemplate = new RestTemplate();
		return restTemplate.getForObject("https://restcountries.eu/rest/v2/name/"+summaryCountry.getCountryName(), GeneralCountryDTO[].class);
	}
	
	
	public GeneralCountryDTO[] getInformationCountryByNameCity(String nameBa){
		RestTemplate restTemplate = new RestTemplate();
		return restTemplate.getForObject("https://restcountries.eu/rest/v2/capital/"+nameBa, GeneralCountryDTO[].class);
	}
	

}
