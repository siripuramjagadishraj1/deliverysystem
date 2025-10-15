package com.everest.deliverysystem;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import com.everest.deliverysystem.exception.BusinessException;
import com.everest.deliverysystem.simulator.ParcleDeliverySimulatior;

@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
public class ParcelCostDeliveryTests {
	
	@Autowired
	private ParcleDeliverySimulatior parcleDeliverySimulatior;
	
	@Disabled
	@Test
	public void testEstimateParcelCostWithUserInputs() throws BusinessException{
		parcleDeliverySimulatior.estimateParcelCost();
	}
	
//	@Disabled
	@Test
	public void testEstimateParcelDeliveryTimeWithUserInputs() throws BusinessException{
		parcleDeliverySimulatior.estimateParcelDeliveryTime();
	}
	
}
