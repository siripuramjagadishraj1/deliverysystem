package com.everest.deliverysystem.vehicles.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.everest.deliverysystem.exception.BusinessException;

@Service
public class VehicleManagementServiceImpl implements VehicleManagementService {

	@Autowired
	private VehicleManagementRepository vehicleManagementRepository;

	@Override
	public List<Vehicles> findAll() throws BusinessException {
		try {
			return vehicleManagementRepository.findAll();
		} catch (Exception e) {
			throw new BusinessException(e);
		}
	}
	
	@Override
	public void save(Vehicles vehicle) throws BusinessException {
		try {
			vehicleManagementRepository.save(vehicle);
		} catch (Exception e) {
			throw new BusinessException(e);
		}
	}
	
}
