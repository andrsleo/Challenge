package com.service.general.country.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.service.general.country.dto.BasicCountriesDTO;
import com.service.general.country.dto.ExchangeRateDTO;
import com.service.general.country.dto.GeneralCountryDTO;
import com.service.general.country.dto.ResultFinalStatisticsDTO;
import com.service.general.country.dto.ResultInfoDTO;
import com.service.general.country.dto.ResultStatisticsDTO;
import com.service.general.country.factory.ResultInfoFactory;
import com.service.general.country.repositories.ResultInfoRepository;

@Service
public class ChallengeService {
	
	@Autowired
	private ResultInfoFactory resultInfoFactory;
	
	@Autowired
	ResultInfoRepository resultInfoRepository;
	
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
		
		res = resultInfoFactory.newResultInfo(infoCountry, ipLocation, infoCountryBa, infoRates);
		
		resultInfoRepository.save(res);

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
	
	public ResultFinalStatisticsDTO getStatisticsService() {
		
		ResultFinalStatisticsDTO res = new ResultFinalStatisticsDTO();
		
		List<ResultStatisticsDTO> resultsDistance = new ArrayList<ResultStatisticsDTO>();
		Map<String, ResultInfoDTO> mapResultsRedis = resultInfoRepository.findAll();
		ResultInfoDTO registerFarthest = new ResultInfoDTO();
		ResultInfoDTO registerClosest = new ResultInfoDTO();
		int invocationFarthest = 0;
		int invocationClosest = 0;
		
		for (Entry<String, ResultInfoDTO> resultIterate : mapResultsRedis.entrySet()) {
			ResultInfoDTO resultInfoIterate = resultIterate.getValue();
			//Valida la distancia mas larga desde el destino consultado hacia buenos aires
			if (registerFarthest.getDistance() < resultInfoIterate.getDistance() || registerFarthest.getCountry() == null) {
				registerFarthest = resultInfoIterate;
				invocationFarthest = 0;
			}
			//Valida el tipo de invocaciones del pais con la distancia mas larga
			if (registerFarthest.getCountry().equals(resultInfoIterate.getCountry())) {
				invocationFarthest++;
			}
			
			//Valida la distancia mas corta desde el destino consultado hacia buenos aires
			if(registerClosest.getDistance() > resultInfoIterate.getDistance() || registerClosest.getCountry() == null) {
				registerClosest = resultInfoIterate;
				invocationClosest = 0;
			}
			
			//Valida el tipo de invocaciones del pais con la distancia mas corta
			if (registerClosest.getCountry().equals(resultInfoIterate.getCountry())) {
				invocationClosest++;
			}
			
	    }
		
		resultsDistance.add(new ResultStatisticsDTO(registerFarthest.getCountry(), registerFarthest.getDistance() + " kms", invocationFarthest));
		resultsDistance.add(new ResultStatisticsDTO(registerClosest.getCountry(), registerClosest.getDistance() + " kms", invocationClosest));
		res.setResultsDistance(resultsDistance);
		
		//Realizamos el promedio de los llamados de los servicios de la distancia mas corta y mas larga a buenos aires
		int promDistante = (registerClosest.getDistance() * invocationClosest + registerFarthest.getDistance() * invocationFarthest)/(invocationClosest + invocationFarthest);
		res.setPromDistance(promDistante + " kms");
		
        return res;
        
        
	}
	

}
