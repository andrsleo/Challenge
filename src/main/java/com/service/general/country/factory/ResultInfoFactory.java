package com.service.general.country.factory;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.service.general.country.dto.CurrencyDTO;
import com.service.general.country.dto.ExchangeRateDTO;
import com.service.general.country.dto.GeneralCountryDTO;
import com.service.general.country.dto.ResultInfoDTO;

@Service
public class ResultInfoFactory {
	
	public ResultInfoDTO newResultInfo(GeneralCountryDTO[] generalCountryDTO, String ipLocation, GeneralCountryDTO[] generalCountryBa, ExchangeRateDTO infoRates) {
		ResultInfoDTO res = new ResultInfoDTO();
		
		res.setIp(ipLocation);
		res.setActualDate(new Date());
		res.setCountry(generalCountryDTO[0].getName());
		res.setIsoCode(generalCountryDTO[0].getAlpha2Code());
		List<String> languages = new ArrayList<String>();
		generalCountryDTO[0].getLanguages().stream().forEach(lang -> languages.add(lang.getNativeName() + "(" + lang.getIso639_1() + ")"));
		res.setLanguages(languages);
		String totalTime = new String();
		ZonedDateTime now = ZonedDateTime.now();
		for (String time : generalCountryDTO[0].getTimezones()) {
			totalTime = totalTime + now.withZoneSameInstant(ZoneId.of(time)).toLocalTime() + "(" + time + ") ";
		}
		res.setHours(totalTime);
		Double distance = distanceKm(generalCountryBa[0].getLatlng().get(0), generalCountryDTO[0].getLatlng().get(0), generalCountryBa[0].getLatlng().get(1), generalCountryDTO[0].getLatlng().get(1));
		res.setDistanceBa(distance.intValue() + " kms ("+ generalCountryBa[0].getLatlng().get(0) + ", " + generalCountryBa[0].getLatlng().get(1) + ") a (" + generalCountryDTO[0].getLatlng().get(0) + ", " + generalCountryDTO[0].getLatlng().get(1) + ")");
		res.setDistance(distance.intValue());

		ObjectMapper oMapper = new ObjectMapper();
		Map<String, Object> map = oMapper.convertValue(infoRates.getRates(), Map.class);
		// System.out.println(map);
		List<String> totalCurrencies = new ArrayList<String>();
		for (CurrencyDTO currencyObject : generalCountryDTO[0].getCurrencies()) {
			//System.out.println(map.get(currencyObject.getCode()));
			totalCurrencies.add(currencyObject.getCode() + "(1 EUR = " + map.get(currencyObject.getCode()) + " " +  currencyObject.getSymbol() + ")");
		}

		res.setMoney(totalCurrencies);
		//System.out.println(totalCurrencies);
		return res;
	}

	public double distanceKm(double lat1, double lat2, double lng1, double lng2) {
		
		double radioTierra = 6371;
        double dLat = Math.toRadians(lat2 - lat1);  
        double dLng = Math.toRadians(lng2 - lng1);  
        double sindLat = Math.sin(dLat / 2);  
        double sindLng = Math.sin(dLng / 2);  
        double va1 = Math.pow(sindLat, 2) + Math.pow(sindLng, 2)  
                * Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2));  
        double va2 = 2 * Math.atan2(Math.sqrt(va1), Math.sqrt(1 - va1));  
        double distancia = radioTierra * va2;  
   
        return distancia;  
		
	}
	
}
