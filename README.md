### Delivery Cost Estimation with Offers
##### <u>Software Stack:</u>
	java-version: java 21
	database: uses in-memory H2 DB  
	framework: spring-boot(Version=3.5.6) 
	OS: BatScripts for Windows are given.
##### <u>Build:</u>
	cmd>git clone https://github.com/siripuramjagadishraj1/deliverysystem.git
	cmd>mvn clean install
	Check ---> deliverysystem/target/site/jacoco/index.html (92% code coverage)
##### <u>Run :</u>
	Mode-1: You are required to build a command line application to estimate the total delivery cost of each package with an offer code 
	cmd>start 1.PARCEL_COST_TEST.bat
	
	Mode-2: Delivery Time Estimation of parcels.
	cmd>start 2.PARCEL_DELIVERY_TEST.bat
	
	Mode-3: Simulation via Producer(Parcel generation) consumer(Vehicles).
	cmd>start 3.PARCEL_DELIVERY_SIMULATION.bat
##### <u>View Simulation :</u>
	http://localhost:8001/h2-console/
	jdbc-URL: jdbc:h2:mem:testdb
	click: connect
	
	run below queries(all) >
	SELECT id,package_name,weight,distance,start_delivery_time,delivery_minutes,end_delivery_time,status FROM PARCEL_PACKAGE where status = 'READY' order by weight desc,distance desc;
	SELECT id,package_name,weight,distance,start_delivery_time,delivery_minutes,end_delivery_time,status  FROM PARCEL_PACKAGE where status = 'PICKED_UP' order by weight desc,distance desc;
	SELECT id,package_name,weight,distance,start_delivery_time,delivery_minutes,end_delivery_time,status  FROM PARCEL_PACKAGE where status = 'DELIVERED' order by end_delivery_time desc,mapped_vehicle  desc;
	select * from vehicles;
	
	Note: 
	1. Vehicle status changes from AVAILABLE-NOT_AVILABLE and vis-a-vis.
	2. Parcel status changes from REDY-PICKED_UP-DELIVERED
	3. Also look at the strategy they are being picked.
	Only 100 parcels are generated.

