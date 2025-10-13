package com.everest.deliverysystem;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.everest.deliverysystem.exception.BusinessException;

@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
public class BusinessExceptionTests {
	
	@Test
	public void testBusinessException() throws BusinessException{
		Assertions.assertThrows( BusinessException.class,()->{throw new BusinessException();} );
		Assertions.assertThrows( BusinessException.class,()->{throw new BusinessException("Message Error");} );
		Assertions.assertThrows( BusinessException.class,()->{throw new BusinessException("Message Error",new RuntimeException());} );
		Assertions.assertThrows( BusinessException.class,()->{throw new BusinessException(new RuntimeException());} );
	}

}
