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
##### <u>Run (Windows bat-scripts) :</u>
	Mode-1: You are required to build a command line application to estimate the total delivery cost of each package with an offer code 
	cmd>start 1.PARCEL_COST_TEST.bat
	
	Mode-2: Delivery Time Estimation of parcels.
	cmd>start 2.PARCEL_DELIVERY_TEST.bat
	
	Mode-3: Simulation via Producer(Parcel generation) consumer(Vehicles).
	cmd>start 3.PARCEL_DELIVERY_SIMULATION.bat
##### <u>Run (Mac/Linux/Window) :</u>
	Mode-1: You are required to build a command line application to estimate the total delivery cost of each package with an offer code 
	cmd>java -jar target/deliverysystem-0.0.1-SNAPSHOT.jar PARCEL_COST_TEST
	
	Mode-2: Delivery Time Estimation of parcels.
	cmd>java -jar target/deliverysystem-0.0.1-SNAPSHOT.jar PARCEL_DELIVERY_TEST
	
	Mode-3: Simulation via Producer(Parcel generation) consumer(Vehicles).
	cmd>java -jar target/deliverysystem-0.0.1-SNAPSHOT.jar PARCEL_DELIVERY_SIMULATION

