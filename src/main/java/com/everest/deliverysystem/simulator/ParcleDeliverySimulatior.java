package com.everest.deliverysystem.simulator;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.everest.deliverysystem.env.service.EnvConstantsService;
import com.everest.deliverysystem.exception.BusinessException;
import com.everest.deliverysystem.parcel.service.ParcelManagementRepository;
import com.everest.deliverysystem.parcel.service.ParcelManagementService;
import com.everest.deliverysystem.parcel.service.ParcelPackage;
import com.everest.deliverysystem.utils.ParcelStaus;
import com.everest.deliverysystem.utils.VehicleStatus;
import com.everest.deliverysystem.vehicleparcel.service.VehicleParcelMapperService;
import com.everest.deliverysystem.vehicles.service.VehicleManagementRepository;
import com.everest.deliverysystem.vehicles.service.VehicleManagementService;
import com.everest.deliverysystem.vehicles.service.Vehicles;

@Component
public class ParcleDeliverySimulatior {
	
	private static final String PARCEL_BASE_FARE = "PARCEL_BASE_FARE";

	private static final Logger LOGGER = LoggerFactory.getLogger(ParcleDeliverySimulatior.class);
	
	@Autowired
	private ParcelManagementService parcelManagementService;
	
	@Autowired
	private VehicleManagementService vehicleManagementService;
	
	@Autowired
	private VehicleParcelMapperService vehicleParcelMapperService;
	
	@Autowired
	private VehicleManagementRepository vehicleManagementRepository;
	
	@Autowired
	private ParcelManagementRepository parcelManagementRepository;
	
	@Autowired
	private EnvConstantsService envConstantsService;
	
	private Scanner scanner = new Scanner(System.in);
	
	public void estimateParcelCost() {
		try {
			System.out.println("====1. ESTIMATE TOTAL DELIVERY COST OF PARCEL TEST====");
			 String base_delivery_cost  = envConstantsService.findActiveEnvProperties().stream().filter(e-> PARCEL_BASE_FARE.equalsIgnoreCase(e.getEnvKey()) ).findAny().get().getEnvValue();
			 System.out.println("base_delivery_cost(100): "+base_delivery_cost);
			 
			 System.out.print("no_of_packges(3): ");
			 int no_of_packges = scanner.nextInt();
			 scanner.nextLine();
			 
			String[] packages = new String[no_of_packges];
			parcleWtDistanceInfo(packages);
			for(int i=0;i< packages.length;i++) {
				System.out.print("Package-"+i+": (pkg_id1, pkg_weight1_in_kg,distance1_in_km,offer_code1)-->"+packages[i]+":");
				packages[i] = scanner.nextLine();
			}
			parcelManagementRepository.deleteAll();
			registerParcelsInDB(packages);
			System.out.println("====OUTPUT====");
			List<ParcelPackage> packagesList = parcelManagementService.findAll();
			for(int i=0;i<packagesList.size();i++) {
				ParcelPackage each = packagesList.get(i);
				System.out.println("Package["+i+"]==> id: "+ each.getPackageName()+", discountAmt: "+each.getDiscountAmount()+", TotalCost: "+ (each.getNoDiscoutPackageCost()-each.getDiscountAmount()) );
			}
		} catch (BusinessException e) {
			LOGGER.error("Error: {}",e.getMessage());
		}
	}

	public void estimateParcelDeliveryTime() {
		try {
			System.out.println("====2. ESTIMATE PARCEL DELIVERY TIMES TEST====");
			parcelManagementRepository.deleteAll();
			vehicleManagementRepository.deleteAll();
			
			String base_delivery_cost  = envConstantsService.findActiveEnvProperties().stream().filter(e-> PARCEL_BASE_FARE.equalsIgnoreCase(e.getEnvKey()) ).findAny().get().getEnvValue();
			System.out.println("base_delivery_cost(100): "+base_delivery_cost);
			 
			System.out.print("no_of_packges(5): ");
			int no_of_packges = scanner.nextInt();
			scanner.nextLine();
			
			String[] packages = new String[no_of_packges];
			parcleWtDistanceInfoForDeliveryEstimate(packages);
			for(int i=0;i<packages.length;i++) {
				System.out.print("Package-"+i+": (pkg_id1, pkg_weight1_in_kg,distance1_in_km,offer_code1)"+packages[i]+":");
				packages[i] = scanner.nextLine();
			}
			registerParcelsInDB(packages);
			
			System.out.print("no_of_vehicles(2): ");
			int no_of_vehicles = scanner.nextInt();
			scanner.nextLine();
			
			System.out.print("max_speed(70): ");
			double max_speed = scanner.nextDouble();
			scanner.nextLine();
			
			System.out.print("max_carriable_weight(200): ");
			double max_carriable_weight = scanner.nextDouble();
			scanner.nextLine();
			registerRandomVehiclesInDB(no_of_vehicles, max_speed, max_carriable_weight);
			List<ParcelPackage> packagesList = new ArrayList<>();
			int k=1;
			do {
				System.out.println("===="+(k++)+". ITERATION====");
				vehicleManagementRepository.updateStatus(VehicleStatus.AVAILABLE.name());
				
				vehicleParcelMapperService.mapAllParcelsToVehicles();
				packagesList = parcelManagementService.findByStatusOrderByWeightDescDistanceDesc(ParcelStaus.PICKED_UP.name()).get();
				packagesList = packagesList.stream().sorted( (a,b)-> a.getStartDeliveryTime().toString().compareTo(b.getStartDeliveryTime().toString()) ).collect(Collectors.toList());
				for(int i=0;i<packagesList.size();i++) {
					ParcelPackage each = packagesList.get(i);
					System.out.println(
							"Package["+i+"]==> "+each.getId()+" - "
									+  "id: "+ each.getPackageName()
									//+", discountAmt: "+each.getDiscountAmount()
									+", TotCost: "+ (each.getNoDiscoutPackageCost()-each.getDiscountAmount())
									+", assignedVehicle: "+ each.getMappedVehicle()
									+", weight: "+ each.getWeight() 
									+", deliverTimeHrs: "+ ((double)each.getDeliveryMinutes()/60d)
									+", startTIme: "+ each.getStartDeliveryTime() 
									+", endDeliveryTime: "+ each.getEndDeliveryTime());
					
				}
				List<Vehicles> vehicles = vehicleManagementService.findAll();
				vehicles.forEach(e->{
					System.out.println("Vehicle ==>"+"["+e.getId()+":"
														+e.getAvailableTime()+":"
														+e.getStatus()+":"
														+"]");
				});
				
			}while(packagesList.size()<no_of_packges);
		} catch (BusinessException e) {
			LOGGER.error("Error: {}",e.getMessage());
		}
	}
	
	public void simulateParcelDelivery() {
		parcelManagementRepository.deleteAll();
		LOGGER.info("===1. SIMULATION OF PARCEL CREATION===");
		new Thread(()->{//100 Parcels
			try {
				int totalParcels = 0;
				int activeParcelsInQueue = 0;
				while(totalParcels <= 100) {
					activeParcelsInQueue  = parcelManagementService.findByStatusOrderByWeightDescDistanceDesc(ParcelStaus.READY.name()).get().size();
					if(activeParcelsInQueue <=10) {
						vehicleParcelMapperService.generateParcelWithCoupouns(); 
						totalParcels = totalParcels+1;
						Thread.sleep(5000); 
					}
				}
			} catch (Exception e) {
				LOGGER.error("Error: {}",e);
			}
		}).start();
		
		new Thread(()->{//Map Parcel to Vehicle
			try {
				while(true) {
					int parcelCount  = parcelManagementService.findByStatusOrderByWeightDescDistanceDesc(ParcelStaus.READY.name()).get().size();
					if(parcelCount> 4) {
						vehicleParcelMapperService.mapAllParcelsToVehicles();
					}
					Thread.sleep(5*5*1000);
				}
			} catch (Exception e) {
				LOGGER.error("Error: {}",e);
			}
		}).start();
		
		new Thread(()->{//Notify ParcleDeliver & VehicleAvailablity
			try {
				while(true) {
					parcelManagementRepository.updateStatusWhereCurrentTimeGreatherThanPackageEndTime(ParcelStaus.DELIVERED.name());
					vehicleManagementRepository.updateStatusWhereCurrentTimeGreatherThanPackageEndTime(VehicleStatus.AVAILABLE.name());
					Thread.sleep(1*60*1000);
				}
			} catch (Exception e) {
				LOGGER.error("Error: {}",e);
			}
		}).start();
	}
	
	private void parcleWtDistanceInfoForDeliveryEstimate(String[] packages) {
		String packageListString = "PKG1,50,30,OFR001@PKG2,75,125,OFFR0008@PKG3,175,100,OFFR003@PKG4,110,60,OFR002@PKG5,155,95,NA";
		String[] packageListSplit= packageListString.split("@");
		int j=0;
		for(int i=0;i<packages.length;i++) {
			packages[i] = packageListSplit[(j++)%packageListSplit.length];
		}
	}
	
	private void parcleWtDistanceInfo(String[] packages) {
		String packageListString = "PKG1,5,5,OFR001@PKG2,15,5,OFR002@PKG3,10,100,OFR003";
		String[] packageListSplit= packageListString.split("@");
		int j=0;
		for(int i=0;i<packages.length;i++) {
			packages[i] = packageListSplit[(j++)%packageListSplit.length];
		}
	}

	private void registerRandomVehiclesInDB(int no_of_vehicles, double max_speed, double max_carriable_weight) throws BusinessException {
		for(int i=0;i<no_of_vehicles;i++) {
			Vehicles vehicle = new Vehicles();
			vehicle.setDriverName("Jack"+ ((int)(Math.random() *100)) );
			vehicle.setVehicleName("KA-41-EF-"+ ((int)(Math.random() * 10000)) );
			vehicle.setMaxWtLimit(max_carriable_weight);
			vehicle.setSpeed(max_speed);
			vehicle.setStatus(VehicleStatus.AVAILABLE.name());
			vehicleManagementService.save(vehicle);
		}
	}

	private void registerParcelsInDB(String[] packages) throws BusinessException {
		for (String eachPackage : packages) {
			String[] parcelDetails = eachPackage.split(",");
			ParcelPackage parcel = new ParcelPackage();
			parcel.setId(null);
			parcel.setPackageName(parcelDetails[0].trim());
			parcel.setWeight(Double.parseDouble(parcelDetails[1].trim()));
			parcel.setDistance(Double.parseDouble(parcelDetails[2].trim()));
			parcel.setCopounCode(parcelDetails[3].trim());
			parcel.setStartDeliveryTime(null);
			parcel.setEndDeliveryTime(null);
			parcel.setStatus(ParcelStaus.READY.name());
			parcelManagementService.saveParcel(parcel);
		}
	}

}
