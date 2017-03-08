package enamel.simulator;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.junit.Test;

public class ErrorLogTest {
	
	SoundPlayer PlayerFive = new SoundPlayer();
	SoundPlayer PlayerSix = new SoundPlayer();
	SoundPlayer PlayerSeven = new SoundPlayer();
	

	@Test
	public void TestErrorLogFive() throws IOException, FileNotFoundException{
		PlayerFive.startFile("Scenario_Five.txt");	//Delete this line to check if buffered reader is implemented properly
		@SuppressWarnings("resource")
		BufferedReader Check = new BufferedReader(new FileReader(System.getProperty("user.dir") + "\\" + "ERROR_LOG.txt"));
		assertTrue(Check.readLine() != null);
	}
	
	@Test
	public void TestErrorLogSix() throws IOException, FileNotFoundException{
		PlayerSix.startFile("Scenario_Six.txt");	//Delete this line to check if buffered reader is implemented properly
		@SuppressWarnings("resource")
		BufferedReader Check = new BufferedReader(new FileReader(System.getProperty("user.dir") + "\\" + "ERROR_LOG.txt"));
		assertTrue(Check.readLine() != null);
	}
	
	@Test
	public void TestErrorLogSeven() throws IOException, FileNotFoundException{
		PlayerSeven.startFile("Scenario_Seven.txt");	//Delete this line to check if buffered reader is implemented properly
		@SuppressWarnings("resource")
		BufferedReader Check = new BufferedReader(new FileReader(System.getProperty("user.dir") + "\\" + "ERROR_LOG.txt"));
		assertTrue(Check.readLine() != null);
	}
	
}
