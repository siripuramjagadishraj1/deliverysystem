package com.everest.deliverysystem.utils;

import java.sql.Timestamp;
import com.everest.deliverysystem.parcel.service.ParcelPackage;
import com.everest.deliverysystem.vehicles.service.Vehicles;

public class ParcelUtils {
	
	public static void mapVehicleToParcel(Vehicles vehEntity, ParcelPackage eachParcelPackage) {
		Integer vehicleId = vehEntity.getId();
		Double vehicleSpeed = vehEntity.getSpeed();
		Timestamp vehicleAvailableTs = vehEntity.getAvailableTime()==null? ClockUtils.getCurrentTime(): vehEntity.getAvailableTime();
		eachParcelPackage.setStatus(ParcelStaus.PICKED_UP.name());
		eachParcelPackage.setMappedVehicle(vehicleId.toString());
		eachParcelPackage.setStartDeliveryTime(vehicleAvailableTs);
		int timeInMinutes = (int) (eachParcelPackage.getDistance()/vehicleSpeed  * 60);
		timeInMinutes = timeInMinutes==0? 1: timeInMinutes;
		eachParcelPackage.setDeliveryMinutes(timeInMinutes);
		eachParcelPackage.setEndDeliveryTime( ClockUtils.getTimeOffSet(vehicleAvailableTs, timeInMinutes) );
	}

}
