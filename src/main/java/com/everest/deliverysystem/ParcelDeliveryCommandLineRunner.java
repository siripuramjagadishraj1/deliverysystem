package com.everest.deliverysystem;

import java.util.Arrays;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.stereotype.Component;

import com.everest.deliverysystem.simulator.ParcleDeliverySimulatior;

@Component
public class ParcelDeliveryCommandLineRunner implements CommandLineRunner{

	private static final String PARCEL_DELIVERY_SIMULATION = "PARCEL_DELIVERY_SIMULATION";

	private static final String PARCEL_DELIVERY_TEST = "PARCEL_DELIVERY_TEST";

	private static final String PARCEL_COST_TEST = "PARCEL_COST_TEST";

	private static final String TEST = "test";

	@Autowired
	private ParcleDeliverySimulatior parcleDeliverySimulatior;
	
	@Autowired
	private Environment environment;
	
	@Override
	public void run(String... args) throws Exception {
		boolean isTestEnv = Arrays.stream(environment.getActiveProfiles()).anyMatch(e->TEST.equals(e));
		if(isTestEnv) {
			return;
		}
		if(args == null || args.length==0) {
			return;
		}
		
		String selection = args[0];
		switch (selection) {
			case PARCEL_COST_TEST: 
				parcleDeliverySimulatior.estimateParcelCost();
				break;
			case PARCEL_DELIVERY_TEST: 
				parcleDeliverySimulatior.estimateParcelDeliveryTime();
				break;
			case PARCEL_DELIVERY_SIMULATION:
				parcleDeliverySimulatior.simulateParcelDelivery();
				System.out.println("DB URL-> "+"+http://localhost:8001/h2-console");
				break;
			default:
				System.out.println("==NO PROPER INPUT WAS GIVEN==");
				break;
		}
	}

    @Bean
    ResourceDatabasePopulator databasePopulator(DataSource dataSource) {
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScript(new ClassPathResource("DATA.sql"));
        populator.execute(dataSource);
        return populator;
    }

}
