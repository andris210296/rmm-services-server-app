package com.rmm;

import static org.junit.Assert.assertEquals;

import java.util.Map;

import org.junit.jupiter.api.Test;

import com.rmm.testutils.RmmTestHelper;
import com.rmm.utils.RmmUtils;

public class RmmUtilsTest extends RmmTestHelper{
	
	@Test
	public void returnMapQuantityOperatingSystemTest() {
		Map<String, Integer> response = RmmUtils.returnMapQuantityOperatingSystem(generateListDevices());
		
		int a = response.get(WINDOWS);
		
		assertEquals(1, a);

	}

}
