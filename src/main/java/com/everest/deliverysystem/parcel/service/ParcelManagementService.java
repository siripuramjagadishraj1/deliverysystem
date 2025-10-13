package com.everest.deliverysystem.parcel.service;

import java.util.List;
import java.util.Optional;

import com.everest.deliverysystem.exception.BusinessException;

public interface ParcelManagementService {
	public void calculateParcelEssentials(ParcelPackage parcel) throws BusinessException;
	public void saveParcel(ParcelPackage parcel) throws BusinessException;
	public List<ParcelPackage> findAll() throws BusinessException;
	public Optional<List<ParcelPackage>> findByStatusOrderByWeightDescDistanceDesc(String status) throws BusinessException;
}
