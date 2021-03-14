package com.service.general.country.dto;

import java.util.List;

public class ResultFinalStatisticsDTO {
	
	private List<ResultStatisticsDTO> resultsDistance;
	private String promDistance;
	
	
	public List<ResultStatisticsDTO> getResultsDistance() {
		return resultsDistance;
	}
	public void setResultsDistance(List<ResultStatisticsDTO> resultsDistance) {
		this.resultsDistance = resultsDistance;
	}
	public String getPromDistance() {
		return promDistance;
	}
	public void setPromDistance(String promDistance) {
		this.promDistance = promDistance;
	}
	
	

}
