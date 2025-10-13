package com.everest.deliverysystem;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Calendar;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.everest.deliverysystem.utils.ClockUtils;

@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
public class ClockUtilsTest {
	
	private static final String GENERAL_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
	
	@Test
	public void testClockUtils() {
		assertNotNull(ClockUtils.getFormattedTime(ClockUtils.getCurrentTime(), GENERAL_DATE_FORMAT));
		assertNotNull(ClockUtils.getCurrentTimeOffset(1*24*60 + 1*60 + 15), GENERAL_DATE_FORMAT);
		
		assertNotNull(ClockUtils.getCurrentTimeString());
		assertNotNull(ClockUtils.getCurrentTimeOffsetString(1*24*60 + 1*60 -15));
		
		assertNotNull(ClockUtils.getCurrentTime());
		assertNotNull(ClockUtils.getCurrentTimeOffset(1*24*60 + 1*60 -15));
		
		assertNotNull(ClockUtils.getTimeOffSet(Calendar.getInstance(), 10));
	}

}
