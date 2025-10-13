package com.everest.deliverysystem.vehicles.service;

import java.sql.Timestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity(name = "VEHICLES")
public class Vehicles {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String vehicleName;
	private String driverName;
	private Double speed;
	private Double maxWtLimit;
	private String status;
	private Timestamp availableTime;

	public int getId() {
		return id;
	}
	public String getVehicleName() {
		return vehicleName;
	}
	public void setVehicleName(String vehicleName) {
		this.vehicleName = vehicleName;
	}
	public String getDriverName() {
		return driverName;
	}
	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}
	public Double getSpeed() {
		return speed;
	}
	public void setSpeed(Double speed) {
		this.speed = speed;
	}
	public Double getMaxWtLimit() {
		return maxWtLimit;
	}
	public void setMaxWtLimit(Double maxWtLimit) {
		this.maxWtLimit = maxWtLimit;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

	public Timestamp getAvailableTime() {
		return availableTime;
	}
	public void setAvailableTime(Timestamp availableTime) {
		this.availableTime = availableTime;
	}
	@Override
	public String toString() {
		return this.getId()+"-"+this.getVehicleName()+"-"+this.getDriverName()+"-"+this.getSpeed()+"-"+this.getMaxWtLimit()+"-"+this.getStatus()+"-"+this.getAvailableTime();
	}
}
