package com.everest.deliverysystem;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.everest.deliverysystem.env.service.EnvConstantsService;
import com.everest.deliverysystem.env.service.EnvProperties;
import com.everest.deliverysystem.exception.BusinessException;
import com.everest.deliverysystem.utils.EnvPropertyState;

@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
public class EnvConstantsServiceTest {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(EnvConstantsServiceTest.class);
	
	@Autowired
	private EnvConstantsService envConstantsService;
	
	@Test
	public void testGetEnvConstansts() throws BusinessException{
		List<EnvProperties> envProps = envConstantsService.findActiveEnvProperties();
		LOGGER.info("envProps : "+envProps);
		assertEquals(envProps.size()>1, true);
	}
	
	@Test
	public void testSetEnvConstansts() throws BusinessException{
		EnvProperties envProps = new EnvProperties();
		envProps.setId(null);
		envProps.setEnvKey("JUNIT_TEST");
		envProps.setEnvValue("1000");
		envProps.setDescription("Basic Fare to Process A JUNIT");
		envProps.setState(EnvPropertyState.ACTIVE);
		envConstantsService.save(envProps);
	}

}
