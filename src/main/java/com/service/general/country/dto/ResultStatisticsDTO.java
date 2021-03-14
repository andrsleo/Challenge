package com.service.general.country.dto;

public class ResultStatisticsDTO {
	
	private String nameCountry;
	private String distance;
	private int invocation;
	
	public ResultStatisticsDTO(String nameCountry, String distance, int invocation) {
		this.nameCountry = nameCountry;
		this.distance = distance;
		this.invocation = invocation;
	}
	
	
	public String getNameCountry() {
		return nameCountry;
	}
	public void setNameCountry(String nameCountry) {
		this.nameCountry = nameCountry;
	}
	public String getDistance() {
		return distance;
	}
	public void setDistance(String distance) {
		this.distance = distance;
	}
	public int getInvocation() {
		return invocation;
	}
	public void setInvocation(int invocation) {
		this.invocation = invocation;
	}

}
