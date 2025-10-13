package com.everest.deliverysystem;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.everest.deliverysystem.exception.BusinessException;
import com.everest.deliverysystem.parcel.service.ParcelManagementService;
import com.everest.deliverysystem.parcel.service.ParcelPackage;
import com.everest.deliverysystem.utils.ClockUtils;
import com.everest.deliverysystem.utils.ParcelStaus;
import com.everest.deliverysystem.utils.ParcelUtils;
import com.everest.deliverysystem.utils.SimulationUtils;
import com.everest.deliverysystem.utils.VehicleStatus;
import com.everest.deliverysystem.vehicleparcel.service.VehicleParcelMapperService;
import com.everest.deliverysystem.vehicles.service.Vehicles;

import static com.everest.deliverysystem.utils.SimulationUtils.*;
import static com.everest.deliverysystem.utils.ParcelUtils.*;

@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
public class VehicleParcelMapperTests {
	
	@Autowired
	private VehicleParcelMapperService vehicleParcelMapperService;
	
	@Autowired
	private ParcelManagementService parcelManagementService;
	
	@Test
	public void testMapVehicleToParcle() throws BusinessException{
		vehicleParcelMapperService.mapAllParcelsToVehicles();
	}
	
	@Test
	public void testZGenerateParcelWithCoupouns() throws BusinessException{
		for(int i=0;i<10;i++)
			vehicleParcelMapperService.generateParcelWithCoupouns();
		List<ParcelPackage> parcelList = parcelManagementService.findAll();
		assertNotNull(parcelList);
		List<ParcelPackage> parcelReadyList = parcelManagementService.findByStatusOrderByWeightDescDistanceDesc(ParcelStaus.READY.name()).get();
		assertNotNull(parcelReadyList);
	}
	
	@Test
	public void testSimulationUtils() {
		//this is Just for Code Coverage.
		new ClockUtils();
		new SimulationUtils();
		new ParcelUtils();
		
		for(int i=0;i<10;i++) {
			assertNotNull(generatePackageSerial());
			assertNotNull(getStandardPackageWeights());
			assertNotNull(getRandomCouponCode());
		}
		Vehicles vehicle = new Vehicles();
			vehicle.setId(1);
			vehicle.setDriverName("Jayanth");
			vehicle.setMaxWtLimit(100d);
			vehicle.setSpeed(50d);
			vehicle.setVehicleName("TS-15-FK-1234");
			vehicle.setAvailableTime(null);
		ParcelPackage parcel = new ParcelPackage();
			parcel.setId(null);
			parcel.setPackageName("PKG1");
			parcel.setWeight(5d);
			parcel.setDistance(5d);
			parcel.setCopounCode("OFR001");
			parcel.setMappedVehicle("KA-41EF-8771");
		mapVehicleToParcel(vehicle, parcel);
		assertNotNull(parcel.getMappedVehicle());
		assertNotNull(parcel.getStartDeliveryTime());
		assertNotNull(parcel.getEndDeliveryTime());
		assertNotNull(parcel.getDeliveryMinutes());
	}
	
	@Test
	public void testMapVehicleListToParcelList() throws BusinessException{
		Vehicles vehicle = new Vehicles();
		vehicle.setId(1);
		vehicle.setDriverName("Jayanth");
		vehicle.setMaxWtLimit(200d);
		vehicle.setSpeed(70d);
		vehicle.setVehicleName("TS-15-FK-1234");
		vehicle.setAvailableTime(null);
		
		ParcelPackage parcel = new ParcelPackage();
		parcel.setId(null);
		parcel.setPackageName("PKG1");
		parcel.setWeight(5d);
		parcel.setDistance(5d);
		parcel.setCopounCode("OFR001");
		parcel.setMappedVehicle("KA-41EF-8771");
		List<ParcelPackage> pacList = Arrays.asList(parcel);
		
		vehicleParcelMapperService.useEachVehicleToParcel(vehicle, pacList);
	}
	
	@Test
	public void testMapVehicleListToParcelList2() throws BusinessException{
		Vehicles vehicle = new Vehicles();
		vehicle.setId(1);
		vehicle.setDriverName("Jayanth");
		vehicle.setMaxWtLimit(200d);
		vehicle.setSpeed(70d);
		vehicle.setStatus(VehicleStatus.AVAILABLE.name());
		vehicle.setVehicleName("TS-15-FK-1234");
		vehicle.setAvailableTime(null);
		
		ParcelPackage parcel = new ParcelPackage();
		parcel.setId(null);
		parcel.setPackageName("PKG1");
		parcel.setWeight(5d);
		parcel.setDistance(5d);
		parcel.setCopounCode("OFR001");
		parcel.setMappedVehicle("KA-41EF-8771");
		List<ParcelPackage> pacList = Arrays.asList(parcel);
		
		vehicleParcelMapperService.useEachVehicleToParcel(vehicle, pacList);
	}
}
