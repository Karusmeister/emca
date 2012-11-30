package com.acmetelecom.test;

import static org.junit.Assert.*;

import java.math.BigDecimal;

import org.junit.Test;

import com.acmetelecom.MoneyFormatter;

public class MoneyFormatterTest {


	@Test
	public void testZeroPenceToPounds() {
		BigDecimal zeroPence = new BigDecimal(0);
		
		String expected = "0.00";
		String ret = MoneyFormatter.penceToPounds(zeroPence);
		assertEquals(expected, ret);
	}
	
	@Test
	public void test50PenceToPounds() {
		BigDecimal pence = new BigDecimal(50);
		
		String expected = "0.50";
		String ret = MoneyFormatter.penceToPounds(pence);
		assertEquals(expected, ret);
	}
	
	@Test
	public void test500PenceToPounds() {
		BigDecimal pence = new BigDecimal(500);
		
		String expected = "5.00";
		String ret = MoneyFormatter.penceToPounds(pence);
		assertEquals(expected, ret);
	}
	
	@Test
	public void test555PenceToPounds() {
		BigDecimal pence = new BigDecimal(555);
		
		String expected = "5.55";
		String ret = MoneyFormatter.penceToPounds(pence);
		assertEquals(expected, ret);
	}
	
	@Test
	public void testRoundingUpPenceToPounds() {
		BigDecimal pence = new BigDecimal(250.9);
		
		String expected = "2.51";
		String ret = MoneyFormatter.penceToPounds(pence);
		assertEquals(expected, ret);
	}
	
	@Test
	public void testRoundingDownPenceToPounds() {
		BigDecimal pence = new BigDecimal(250.1);
		
		String expected = "2.50";
		String ret = MoneyFormatter.penceToPounds(pence);
		assertEquals(expected, ret);
	}
	
	@Test
	public void testRoundingDownAccuracyPenceToPounds() {
		BigDecimal pence = new BigDecimal(250.49999999);
		
		String expected = "2.50";
		String ret = MoneyFormatter.penceToPounds(pence);
		assertEquals(expected, ret);
	}

	@Test
	public void testRoundingPenceToZero() {
		BigDecimal pence = new BigDecimal(0.9999999999);
		
		String expected = "0.01";
		String ret = MoneyFormatter.penceToPounds(pence);
		assertEquals(expected, ret);
	}
	
}
