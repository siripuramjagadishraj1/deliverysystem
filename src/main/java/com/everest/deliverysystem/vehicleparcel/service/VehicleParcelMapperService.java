package com.everest.deliverysystem.vehicleparcel.service;

import java.util.List;

import com.everest.deliverysystem.exception.BusinessException;
import com.everest.deliverysystem.parcel.service.ParcelPackage;
import com.everest.deliverysystem.vehicles.service.Vehicles;

public interface VehicleParcelMapperService {
	public void generateParcelWithCoupouns() throws BusinessException;
	public void mapAllParcelsToVehicles() throws BusinessException;
	public void useEachVehicleToParcel(Vehicles vehicleEntity,List<ParcelPackage> parclePackage) throws BusinessException;
}
