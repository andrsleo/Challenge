package com.service.general.country.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class ResultInfoDTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String ip;
	private Date actualDate;
	private	String country;
	private String isoCode;
	private List<String> languages;
	private String hours;
	private String distanceBa;
	private int distance;
	private List<String> money;
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public Date getActualDate() {
		return actualDate;
	}
	public void setActualDate(Date actualDate) {
		this.actualDate = actualDate;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getIsoCode() {
		return isoCode;
	}
	public void setIsoCode(String isoCode) {
		this.isoCode = isoCode;
	}
	public List<String> getLanguages() {
		return languages;
	}
	public void setLanguages(List<String> languages) {
		this.languages = languages;
	}
	public String getHours() {
		return hours;
	}
	public void setHours(String hours) {
		this.hours = hours;
	}
	public String getDistanceBa() {
		return distanceBa;
	}
	public void setDistanceBa(String distanceBa) {
		this.distanceBa = distanceBa;
	}
	public List<String> getMoney() {
		return money;
	}
	public void setMoney(List<String> money) {
		this.money = money;
	}
	public int getDistance() {
		return distance;
	}
	public void setDistance(int distance) {
		this.distance = distance;
	}

}
