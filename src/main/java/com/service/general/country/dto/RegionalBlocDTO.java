package com.service.general.country.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class RegionalBlocDTO {

    private String acronym;
    private String name;
    private List<String> otherAcronyms;
    private List<String> otherNames;
    
	public String getAcronym() {
		return acronym;
	}
	public void setAcronym(String acronym) {
		this.acronym = acronym;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<String> getOtherAcronyms() {
		return otherAcronyms;
	}
	public void setOtherAcronyms(List<String> otherAcronyms) {
		this.otherAcronyms = otherAcronyms;
	}
	public List<String> getOtherNames() {
		return otherNames;
	}
	public void setOtherNames(List<String> otherNames) {
		this.otherNames = otherNames;
	}
	
}
