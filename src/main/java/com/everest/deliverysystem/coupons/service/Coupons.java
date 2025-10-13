package com.everest.deliverysystem.coupons.service;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity(name = "COUPONS")
public class Coupons {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String coupounName;
	private Double discount;
	private Double minDistance;
	private Double maxDistance;
	private Double minWeight;
	private Double maxWeight;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCoupounName() {
		return coupounName;
	}
	public void setCoupounName(String coupounName) {
		this.coupounName = coupounName;
	}
	public Double getDiscount() {
		return discount;
	}
	public void setDiscount(Double discount) {
		this.discount = discount;
	}
	public Double getMinDistance() {
		return minDistance;
	}
	public void setMinDistance(Double minDistance) {
		this.minDistance = minDistance;
	}
	public Double getMaxDistance() {
		return maxDistance;
	}
	public void setMaxDistance(Double maxDistance) {
		this.maxDistance = maxDistance;
	}
	public Double getMinWeight() {
		return minWeight;
	}
	public void setMinWeight(Double minWeight) {
		this.minWeight = minWeight;
	}
	public Double getMaxWeight() {
		return maxWeight;
	}
	public void setMaxWeight(Double maxWeight) {
		this.maxWeight = maxWeight;
	}
	@Override
	public String toString() {
		return "["+this.getId()+
				 "-"+this.getCoupounName()+
				 "-"+this.getDiscount()+
				 "-"+this.getMinDistance()+
				 "-"+this.getMaxDistance()+
				 "-"+this.getMinWeight()+
				 "-"+this.getMaxWeight()+
				 "]";
	}
}
