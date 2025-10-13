package com.everest.deliverysystem.vehicleparcel.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.everest.deliverysystem.exception.BusinessException;
import com.everest.deliverysystem.parcel.service.ParcelManagementRepository;
import com.everest.deliverysystem.parcel.service.ParcelManagementService;
import com.everest.deliverysystem.parcel.service.ParcelPackage;
import com.everest.deliverysystem.utils.ClockUtils;
import com.everest.deliverysystem.utils.ParcelStaus;
import com.everest.deliverysystem.utils.VehicleStatus;
import com.everest.deliverysystem.vehicles.service.VehicleManagementRepository;
import com.everest.deliverysystem.vehicles.service.Vehicles;
import static com.everest.deliverysystem.utils.SimulationUtils.*;
import static com.everest.deliverysystem.utils.ParcelUtils.*;

@Service
public class VehicleParcelMapperServiceImpl implements VehicleParcelMapperService{
	
	@Autowired
	private ParcelManagementService parcelManagementService;
	
	@Autowired
	private VehicleManagementRepository vehicleManagementRepository;
	
	@Autowired
	private ParcelManagementRepository  parcelManagementRepository;

	@Override
	public void generateParcelWithCoupouns() throws BusinessException {
		try {
			ParcelPackage parcel = new ParcelPackage();
			parcel.setId(null);
			parcel.setPackageName("PKG"+generatePackageSerial());
			parcel.setCopounCode(getRandomCouponCode());
			parcel.setDistance(Double.valueOf(1 + Math.round(Math.random()*5)));
			parcel.setWeight(Double.valueOf(getStandardPackageWeights()));
			parcel.setStartDeliveryTime(null);
			parcel.setEndDeliveryTime(null);
			parcel.setStatus(ParcelStaus.READY.name());
			parcelManagementService.saveParcel(parcel);
		} catch (Exception e) {
			throw new BusinessException(e);
		}
	}
	
	@Override
	public void mapAllParcelsToVehicles() throws BusinessException {
		try {
			List<Vehicles> vehicles = vehicleManagementRepository.findByStatusOrderByAvailableTimeAsc(VehicleStatus.AVAILABLE.name()).get();
			for(int i=0;i<vehicles.size();i++) {
				List<ParcelPackage> parclePackage = parcelManagementRepository.findByStatusOrderByWeightDescDistanceDesc(ParcelStaus.READY.name()).get();
				useEachVehicleToParcel(vehicles.get(i), parclePackage);
			}
		} catch (Exception e) {
			throw new BusinessException(e);
		}
	}
	
	@Override
	public void useEachVehicleToParcel(Vehicles vehEntity,List<ParcelPackage> parclePackage) throws BusinessException {
		try {
			final Double containerSize = vehEntity.getMaxWtLimit();
			
			List<ParcelPackage> optimalParcels = new ArrayList<>();
			int optimalParcleSize = 0;
			
			for(int i=0;i<parclePackage.size();i++) {
				Double remainingContainerTempWt = containerSize;
				List<ParcelPackage> tempParcelForContainer = new ArrayList<>();
				int noOfParcelsInContainer = 0;
				for(int j=i;j<parclePackage.size();j++) {
					if(remainingContainerTempWt>=parclePackage.get(j).getWeight()) {
						remainingContainerTempWt = remainingContainerTempWt - parclePackage.get(j).getWeight();
						tempParcelForContainer.add(parclePackage.get(j));
						noOfParcelsInContainer = tempParcelForContainer.size();
					}
				}
				if(optimalParcleSize<noOfParcelsInContainer) {
					optimalParcels = tempParcelForContainer;
					optimalParcleSize = noOfParcelsInContainer;
				}
			}
			if(optimalParcels.size()==0)
				return;
			optimalParcels.forEach(e->{mapVehicleToParcel(vehEntity, e);});
			Integer maxDeliverMinutes = optimalParcels.stream().map(e->e.getDeliveryMinutes()).max( (a,b)->a.compareTo(b)).get();
			parcelManagementRepository.saveAll(optimalParcels);
			
			vehEntity.setStatus(VehicleStatus.NOT_AVAILABLE.name());
			Timestamp vehicleAvailableTs = 
					vehEntity.getAvailableTime() == null? ClockUtils.getCurrentTime(): vehEntity.getAvailableTime();
			vehEntity.setAvailableTime(ClockUtils.getTimeOffSet(vehicleAvailableTs, maxDeliverMinutes*2) );
			vehicleManagementRepository.save(vehEntity);
			Thread.sleep(2000);
		} catch (Exception e) {
			throw new BusinessException(e);
		}
	}

}
