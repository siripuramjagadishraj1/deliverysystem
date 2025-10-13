package com.everest.deliverysystem.env.service;

import com.everest.deliverysystem.utils.EnvPropertyState;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity(name = "ENV_PROPERTIES")
public class EnvProperties {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String envKey;
	private String envValue;
	private String description;
	private EnvPropertyState state;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getEnvKey() {
		return envKey;
	}
	public void setEnvKey(String envKey) {
		this.envKey = envKey;
	}
	public String getEnvValue() {
		return envValue;
	}
	public void setEnvValue(String envValue) {
		this.envValue = envValue;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public EnvPropertyState getState() {
		return state;
	}
	public void setState(EnvPropertyState state) {
		this.state = state;
	}
	@Override
	public String toString() {
		return this.getId()+"-"+this.getEnvKey()+"-"+this.getEnvValue()+"-"+this.getDescription()+"-"+this.getState();
	}
}
