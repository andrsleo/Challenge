package com.service.general.country.service;

import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;

import com.service.general.country.dto.ExchangeRateDTO;
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
		
		// Primero se procede a consumir el servicio para Geolocalización de IPs
		RestTemplate restTemplate = new RestTemplate();
		BasicCountriesDTO summaryCountry = restTemplate.getForObject("https://api.ip2country.info/ip?"+ipLocation, BasicCountriesDTO.class);
		
		// Procedo a llamar el método del consumo del servicio para la información del pais de la IP consultada
		GeneralCountryDTO[] infoCountry = getInformationCountryByNameCountry(summaryCountry);

		// Aqui llamo al método del consumo del servicio para la información de la ciudad de Buenos Aires
		GeneralCountryDTO[] infoCountryBa = getInformationCountryByNameCity("Buenos Aires");

		// Aqui llamo al método del consumo del servicio para informacion sobre la cotización actual en dólares
		ExchangeRateDTO infoRates = getInformationExchangeRateData();
		System.out.println(infoRates);
		
		res = resultInfoFactory.newResultInfo(infoCountry, ipLocation, infoCountryBa, infoRates);

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

	public ExchangeRateDTO getInformationExchangeRateData(){
		RestTemplate restTemplate = new RestTemplate();
		return	restTemplate.getForObject("http://data.fixer.io/api/latest?access_key=a31395ebc8954bffa897c795d0bd064e", ExchangeRateDTO.class);
	}
	

}
