package com.everest.deliverysystem.vehicles.service;

import java.util.List;

import com.everest.deliverysystem.exception.BusinessException;

public interface VehicleManagementService {
	public void save(Vehicles vehicle) throws BusinessException;
	public List<Vehicles> findAll() throws BusinessException;
}
