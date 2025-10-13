package com.everest.deliverysystem;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.everest.deliverysystem.coupons.service.Coupons;
import com.everest.deliverysystem.coupons.service.CouponsManagementService;
import com.everest.deliverysystem.exception.BusinessException;
import com.everest.deliverysystem.parcel.service.ParcelManagementService;
import com.everest.deliverysystem.parcel.service.ParcelPackage;
import com.everest.deliverysystem.utils.ClockUtils;
import com.everest.deliverysystem.utils.ParcelStaus;
import com.everest.deliverysystem.vehicles.service.VehicleManagementService;
import com.everest.deliverysystem.vehicles.service.Vehicles;

@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
public class AssetManamentTests {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AssetManamentTests.class);

	@Autowired
	private VehicleManagementService vehicleManagementService;
	
	@Autowired
	private CouponsManagementService couponsManagementService;
	
	@Autowired
	private ParcelManagementService parcelManagementService;

	@Test
	public void getVehicles() throws BusinessException{
		Vehicles vehicle = new Vehicles();
		vehicle.setId(null);
		vehicle.setDriverName("Jayanth");
		vehicle.setMaxWtLimit(100d);
		vehicle.setSpeed(50d);
		vehicle.setVehicleName("TS-15-FK-1234");
		vehicle.setAvailableTime(null);
		vehicleManagementService.save(vehicle);
		List<Vehicles> vehicleList = vehicleManagementService.findAll();
		LOGGER.info("vehicleList :"+vehicleList);
		assertEquals(vehicleList.size()>1, true);
	}
	
	@Test
	public void getCoupounsDiscountByOfferCodeDistanceWeight() throws BusinessException{
		//OFFER-1(+ve)
		String offerCode = "OFR001";
		Double distance = 170d;
		Double weight = 80d;
		Double discount = couponsManagementService.getCoupounsDiscountBy(offerCode, distance, weight);
		assertEquals(10d, discount);
		//OFFER-1(-ve)
		offerCode = "OFR001";
		distance = 210d;
		weight = 80d;
		discount = couponsManagementService.getCoupounsDiscountBy(offerCode, distance, weight);
		assertEquals(0d, discount);
		//OFFER-1(-ve)
		offerCode = "OFR001";
		distance = 200d;
		weight = 20d;
		discount = couponsManagementService.getCoupounsDiscountBy(offerCode, distance, weight);
		assertEquals(0d, discount);
		
		//OFFER-2(+ve)
		offerCode = "OFR002";
		distance = 60d;
		weight = 110d;
		discount = couponsManagementService.getCoupounsDiscountBy(offerCode, distance, weight);
		assertEquals(7d, discount);
		//OFFER-2(-ve)
		offerCode = "OFR002";
		distance = 60d;
		weight = 410d;
		discount = couponsManagementService.getCoupounsDiscountBy(offerCode, distance, weight);
		assertEquals(0d, discount);
		
		//OFFER-3(+ve)
		offerCode = "OFR003";
		distance = 50d;
		weight = 20d;
		discount = couponsManagementService.getCoupounsDiscountBy(offerCode, distance, weight);
		assertEquals(5d, discount);
		
		Coupons coupoun = new Coupons();
		coupoun.setId(null);
		coupoun.setCoupounName("OFR001");
		coupoun.setDiscount(10d);
		coupoun.setMaxDistance(10d);
		coupoun.setMaxWeight(10d);
		coupoun.setMinDistance(10d);
		coupoun.setMinWeight(20d);
		LOGGER.info("coupoun: "+coupoun);
		couponsManagementService.save(coupoun);
	}
	
	@Test
	public void testCalculateParcelEssentials() throws BusinessException{
		ParcelPackage parcel1 = new ParcelPackage();
		parcel1.setId(null);
		parcel1.setPackageName("PKG1");
		parcel1.setWeight(5d);
		parcel1.setDistance(5d);
		parcel1.setCopounCode("OFR001");
		parcel1.setMappedVehicle("KA-41EF-8771");
		parcelManagementService.calculateParcelEssentials(parcel1);
		assertNotNull(parcel1);
		LOGGER.info("parcel1: "+parcel1);
		assertEquals(parcel1.getDiscountAmount(), 0d);
		assertEquals(parcel1.getNoDiscoutPackageCost() - parcel1.getDiscountAmount(), 175d);
		parcel1.setStatus(ParcelStaus.READY.name());
		parcel1.setStartDeliveryTime(ClockUtils.getCurrentTime());
		parcel1.setEndDeliveryTime(ClockUtils.getCurrentTimeOffset(1*24*60));
		parcel1.setDeliveryMinutes(2);
		parcelManagementService.saveParcel(parcel1);
		
		ParcelPackage parcel2 = new ParcelPackage();
		parcel2.setPackageName("PKG2");
		parcel2.setWeight(15d);
		parcel2.setDistance(5d);
		parcel2.setCopounCode("OFR002");
		parcel2.setMappedVehicle("KA-41EF-8771");
		parcelManagementService.calculateParcelEssentials(parcel2);
		assertNotNull(parcel2);
		LOGGER.info("parcel2: "+parcel2);
		assertEquals(parcel2.getDiscountAmount(), 0d);
		assertEquals(parcel2.getNoDiscoutPackageCost() - parcel2.getDiscountAmount(), 275d);
		parcel2.setStartDeliveryTime(ClockUtils.getCurrentTime());
		parcel2.setEndDeliveryTime(ClockUtils.getCurrentTimeOffset(1*24*60));
		parcel2.setStatus(ParcelStaus.READY.name());
		parcel2.setDeliveryMinutes(2);
		parcelManagementService.saveParcel(parcel2);
		
		ParcelPackage parcel3 = new ParcelPackage();
		parcel3.setPackageName("PKG3");
		parcel3.setWeight(10d);
		parcel3.setDistance(100d);
		parcel3.setCopounCode("OFR003");
		parcel3.setMappedVehicle("KA-41EF-8771");
		parcelManagementService.calculateParcelEssentials(parcel3);
		assertNotNull(parcel3);
		LOGGER.info("parcel3: "+parcel3);
		assertEquals(parcel3.getDiscountAmount(), 35d);
		assertEquals(parcel3.getNoDiscoutPackageCost() - parcel3.getDiscountAmount(), 665d);
		parcel3.setStartDeliveryTime(ClockUtils.getCurrentTime());
		parcel3.setEndDeliveryTime(ClockUtils.getCurrentTimeOffset(1*24*60));
		parcel3.setStatus(ParcelStaus.READY.name());
		parcel3.setDeliveryMinutes(2);
		parcelManagementService.saveParcel(parcel3);
	}

}
