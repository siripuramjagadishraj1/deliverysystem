package com.everest.deliverysystem.parcel.service;

import java.sql.Timestamp;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity(name = "PARCEL_PACKAGE")
public class ParcelPackage {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String packageName;
	private Double weight;
	private Double distance;
	private String copounCode;
	
	private Double discountPercent;
	private Double discountAmount;
	private Double noDiscoutPackageCost;
	
	private Timestamp startDeliveryTime;
	private Integer deliveryMinutes;
	private Timestamp endDeliveryTime;
	private String status;
	private String mappedVehicle;
	
	//From, To
	//Start and End of Delivery(By speed of vehicle)
	//Current Status.
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getPackageName() {
		return packageName;
	}
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
	public Double getWeight() {
		return weight;
	}
	public void setWeight(Double weight) {
		this.weight = weight;
	}
	public Double getDistance() {
		return distance;
	}
	public void setDistance(Double distance) {
		this.distance = distance;
	}
	public String getCopounCode() {
		return copounCode;
	}
	public void setCopounCode(String copounCode) {
		this.copounCode = copounCode;
	}
	public Double getDiscountPercent() {
		return discountPercent;
	}
	public void setDiscountPercent(Double discountPercent) {
		this.discountPercent = discountPercent;
	}
	public Double getNoDiscoutPackageCost() {
		return noDiscoutPackageCost;
	}
	public void setNoDiscoutPackageCost(Double noDiscoutPackageCost) {
		this.noDiscoutPackageCost = noDiscoutPackageCost;
	}
	public Double getDiscountAmount() {
		return discountAmount;
	}
	public void setDiscountAmount(Double discountAmount) {
		this.discountAmount = discountAmount;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Timestamp getStartDeliveryTime() {
		return startDeliveryTime;
	}
	public void setStartDeliveryTime(Timestamp startDeliveryTime) {
		this.startDeliveryTime = startDeliveryTime;
	}
	public Timestamp getEndDeliveryTime() {
		return endDeliveryTime;
	}
	public void setEndDeliveryTime(Timestamp endDeliveryTime) {
		this.endDeliveryTime = endDeliveryTime;
	}
	public String getMappedVehicle() {
		return mappedVehicle;
	}
	public void setMappedVehicle(String mappedVehicle) {
		this.mappedVehicle = mappedVehicle;
	}
	public Integer getDeliveryMinutes() {
		return deliveryMinutes;
	}
	public void setDeliveryMinutes(Integer deliveryMinutes) {
		this.deliveryMinutes = deliveryMinutes;
	}
	@Override
	public String toString() {
		return "["+this.getId()+"-"+this.getPackageName()+"-"+this.getWeight()+"-"+this.getDistance()+
				 "-"+this.getCopounCode()+"-"+this.getDiscountPercent()+"-"+this.getNoDiscoutPackageCost()+
				 "-"+this.getMappedVehicle()+"-"+this.getEndDeliveryTime()+"-"+this.getStartDeliveryTime()+"-"+this.getStatus()+
				 "-"+this.getDeliveryMinutes()+
				 "-"+this.getDiscountAmount()+"]";
	}
}
