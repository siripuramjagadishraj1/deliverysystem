package com.everest.deliverysystem.utils;

public class SimulationUtils {
	
	private static int PACKAGE_SERIAL = 3;
	
	public static String generatePackageSerial() {
		return String.format("%03d", PACKAGE_SERIAL++);
	}
	
	public static double getStandardPackageWeights() {
		double standardPackageWt = 0d;
		int randomNumber = ( (int)(Math.random()*100) )%5;
		switch (randomNumber) {
			case 0:  standardPackageWt = 50d ; break;
			case 1:  standardPackageWt = 100d; break;
			case 2:  standardPackageWt = 150d; break;
			case 3:  standardPackageWt = 200d; break;
			default: standardPackageWt = 70+ Math.round(Math.random()*130); break;
		}
		return standardPackageWt;
	}
	
	public static String getRandomCouponCode() {
		String coupounCode = "TEST123";
		int randomNumber = ( (int)(Math.random()*10) )%5;
		switch (randomNumber) {
			case 0:  coupounCode = "OFR001"; break;
			case 1:  coupounCode = "OFR002"; break;
			case 2:  coupounCode = "OFR003"; break;
			default: coupounCode = "TES006"; break;
		}
		return coupounCode;
	}

}
