package enamel.simulator;



import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.*;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JButton;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;

public class SoundPlayer 
{
	private Voice voice;
	private VoiceManager vm;
	private static Scanner fileScanner = null;
	private int cellNum;
	private int buttonNum;
	private Simulator sim;
	private boolean repeat;
	private ArrayList<String> repeatedText;
	private boolean userInput;

	
	
	//TODO: ALLOW MULTIPLE VOICES TO BE PLAYED
	//TODO: LOG ERRORS INTO AN ERROR FILE
	
	public SoundPlayer() 
	{
		vm = VoiceManager.getInstance(); 
		// Will have to change to allow multiple options of voices
		voice = vm.getVoice("kevin16");
		repeatedText = new ArrayList<String> ();
		userInput = false;
		voice.allocate(); 
	}
	
	private void exit ()
	{
		System.exit(0);
	}
	
	private void removeListener (JButton button)
	{
		ActionListener [] aList = button.getActionListeners();
		if (aList.length > 0)
		{
			for (int x = 0; x < aList.length; x ++)
			{
				button.removeActionListener(button.getActionListeners ()[x]);
			}
		}
	}
	
	private void resetButtons ()
	{
		for (int i = 0; i < sim.jButtonNumber; i ++)
		{
			for (JButton j : sim.buttonList)
			{
				removeListener (j);
			}
		}
	}
	
	private void skip (String indicator)
	{
		while (fileScanner.hasNextLine ())
		{
			if (fileScanner.nextLine().equals("/~" + indicator))
			{
				break;
			}
		}
		if (!fileScanner.hasNextLine ())
		{
			fileScanner.close();
			exit ();
		}
	}
	
	
	private void performAction (String fileLine)
	{		
		if (repeat)
		{
			if (fileLine.length () >= 11 && fileLine.substring(0, 11).equals("/~endrepeat"))
			{
				repeat = false;
			}
			else
			{
				repeatedText.add (fileLine);
				speak(fileLine);
			}
		}
		else
		{
			if (fileLine.length () >= 8 && fileLine.substring(0, 8).equals("/~sound:"))
			{
				playSound (fileLine.substring(8));
			}
			else if (fileLine.length () >= 7 && fileLine.substring(0, 7).equals("/~skip:"))
			{
				skip (fileLine.substring(7));
			}
			else if (fileLine.length () >= 8 && fileLine.substring(0, 8).equals("/~pause:"))
			{
				pause (fileLine.substring(8));
				
			}
			else if (fileLine.length () >= 16 && fileLine.substring(0, 16).equals("/~repeat-button:"))
			{
				repeatButton (fileLine.substring(16));
//				paramIndex = Integer.parseInt(fileLine.substring(16));
//				sim.getButton(paramIndex).addActionListener(new ActionListener() {
//
//					@Override
//						public void actionPerformed(ActionEvent arg0) 
//						{
//							if (userInput)
//							{
//								repeatText ();
//							}
//						}
//
//				});
			}
			else if (fileLine.length () >= 8 && fileLine.substring(0, 8).equals("/~repeat"))
			{
				repeatedText.clear ();
				repeat = true;
			}
			else if (fileLine.length () >= 15 && fileLine.substring(0, 15).equals("/~reset-buttons"))
			{
				resetButtons ();
			}
			else if (fileLine.length () >= 14 && fileLine.substring(0, 14).equals("/~skip-button:"))
			{
//				parameters = fileLine.substring(14).split("\\s");
//				paramIndex = Integer.parseInt(parameters[0]);
//				sim.getButton(paramIndex).addActionListener(new ActionListener() {
//
//						@Override
//						public void actionPerformed(ActionEvent arg0) 
//						{
//							if (userInput)
//							{
//								skip (parameters[1]);
//								userInput = false;
//							}
//						}
//
//				});
				skipButton (fileLine.substring(14));
			}
			else if (fileLine.length () >= 15 && fileLine.substring(0, 15).equals("/~disp-clearAll"))
			{
				sim.clearAllCells();
			}
			else if (fileLine.length () >= 17 && fileLine.substring(0, 17).equals("/~disp-cell-pins:"))
			{
			//	parameters = fileLine.substring(17).split("\\s");
			//	paramIndex = Integer.parseInt(parameters[0]);
			//	sim.getCell(paramIndex).setPins(parameters[1]);
				dispCellPins(fileLine.substring(17));
			}
			else if (fileLine.length () >= 14 && fileLine.substring(0, 14).equals("/~disp-string:"))
			{
				sim.displayString(fileLine.substring(14));
			}
			else if (fileLine.length () >= 17 && fileLine.substring(0, 17).equals("/~disp-cell-char:"))
			{
		//		parameters = fileLine.substring(17).split("\\s");
		//		paramIndex = Integer.parseInt(parameters[0]);
		//		sim.getCell(paramIndex).displayCharacter(parameters[1].charAt(0));
				dispCellChar (fileLine.substring(17));
			}
			else if (fileLine.length () >= 18 && fileLine.substring(0, 18).equals("/~disp-cell-raise:"))
			{
			//	parameters = fileLine.substring(18).split("\\s");
			//	paramIndex = Integer.parseInt(parameters[0]);
			//	System.out.println (paramIndex + "    ss");
				dispCellRaise (fileLine.substring(18));
			//	sim.getCell(paramIndex).raiseOnePin(Integer.parseInt(parameters[1]));
			}
			else if (fileLine.length () >= 18 && fileLine.substring(0, 18).equals("/~disp-cell-lower:"))
			{
			//	parameters = fileLine.substring(18).split("\\s");
			//	paramIndex = Integer.parseInt(parameters[0]);
				dispCellLower(fileLine.substring(18));
		//		sim.getCell(paramIndex).lowerOnePin(Integer.parseInt(parameters[1]));
			}
			else if (fileLine.length () >= 18 && fileLine.substring(0, 18).equals("/~disp-cell-clear:"))
			{
			//	parameters = fileLine.substring(18).split("\\s");
			//	paramIndex = Integer.parseInt(parameters[0]);

			//	sim.getCell(paramIndex).clear();
				dispCellClear (Integer.parseInt(fileLine.substring(18)));
			}
			else if (fileLine.length () >= 12 && fileLine.substring(0, 12).equals("/~user-input"))
			{
				userInput = true;
			}
			else
			{		
				speak(fileLine);
			}
		}
	}
	
	private void pause (String paramArgs)
	{
		try
		{
			Thread.sleep((long) (Double.parseDouble(paramArgs) * 1000));
		}
		catch (Exception e)
		{
			
		}
	}
	
	private void repeatButton (String paramArgs)
	{
		int paramIndex = Integer.parseInt(paramArgs);
		sim.getButton(paramIndex).addActionListener(new ActionListener() {

			@Override
				public void actionPerformed(ActionEvent arg0) 
				{
					if (userInput)
					{
						repeatText ();
					}
				}

		});
	}
	
	private void skipButton (String paramArgs)
	{
		String [] param = paramArgs.split("\\s");
		int paramIndex = Integer.parseInt(param[0]);
		sim.getButton(paramIndex).addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) 
				{
					if (userInput)
					{
						skip (param[1]);
						userInput = false;
					}
				}

		});
	}
	
	private void dispCellPins (String paramArgs)
	{
		String [] param = paramArgs.split("\\s");
		int paramIndex = Integer.parseInt(param[0]);
		sim.getCell(paramIndex).setPins(param[1]);
	}
	
	private void dispCellChar (String paramArgs)
	{
		String [] param = paramArgs.split("\\s");
		int paramIndex = Integer.parseInt(param[0]);
		char dispChar = param[1].charAt(0);
		sim.getCell(paramIndex).displayCharacter(dispChar);	
	}
	
	private void dispCellRaise (String paramArgs)
	{
		String [] param = paramArgs.split("\\s");
		int paramIndex = Integer.parseInt(param[0]);
		int pinNum = Integer.parseInt(param[1]);
		sim.getCell(paramIndex).raiseOnePin(pinNum);
	}
	
	private void dispCellClear (int cellIndex)
	{
		
		sim.getCell(cellIndex).clear();
	}
	
	private void dispCellLower (String paramArgs)
	{
		String [] param = paramArgs.split("\\s");
		int paramIndex = Integer.parseInt(param[0]);
		int pinNum = Integer.parseInt(param[1]);
		sim.getCell(paramIndex).lowerOnePin(pinNum);
	}
	
	private void speak (String text)
	{
		try 
		{
			voice.speak(text);
		}
		catch (Exception e)
		{
			
		}
	}
	public void play () throws IllegalStateException
	{
		String fileLine;
		try
		{
			while (fileScanner.hasNextLine())
			{
				while (userInput)
				{
					Thread.sleep(400);
				}
				fileLine = fileScanner.nextLine();
				performAction (fileLine);	
			}
			if (!fileScanner.hasNextLine())
			{
				fileScanner.close ();
				exit ();
			}
		}
		catch (Exception e)
		{
			
		}
	}
	
	private void playSound (String sound)
	{
	    try
	    {
	        Clip clip = AudioSystem.getClip();
	        clip.open(AudioSystem.getAudioInputStream(new File(System.getProperty("user.dir") + "\\" + sound)));
	        clip.start();

	        while (!clip.isRunning())
	            Thread.sleep(10);
	        while (clip.isRunning())
	            Thread.sleep(10); 
	        clip.close();  

	    }
	    catch (Exception exc)
	    {
	        exc.printStackTrace(System.out);
	    }
	}
	
	private void repeatText ()
	{
		for (String i: repeatedText)
		{
			speak(i);
		}
	}
	
	private void setCellAndButton () throws NumberFormatException
	{
		try
		{
			cellNum = Integer.parseInt(fileScanner.nextLine().split("\\s")[1]);
			buttonNum = Integer.parseInt(fileScanner.nextLine().split("\\s")[1]);
			sim = new Simulator (cellNum, buttonNum);
		}
		catch (NumberFormatException e)
		{
			throw new NumberFormatException ("Error! Please enter a number for the cells and/or buttons!");
		}
	}
	
	
	public void startFile (String fileName) throws IllegalArgumentException
	{
		if (fileScanner == null)
		{
			try
			{
				fileScanner = new Scanner (new File (System.getProperty("user.dir") + "\\" + fileName));
				setCellAndButton ();
				play ();
			}
			catch (Exception e)
			{
				speak ("Error! File name is not correct! Please consult a teacher " +
						"or administrator for assistance!");
						throw new IllegalArgumentException ("Error! File name is not correct!");
			}
		}
		else
		{
			throw new IllegalArgumentException ("Error! A scenario file already exists!");
		}
	}
	
	public void startDir (String dirName) throws IllegalArgumentException
	{
		if (fileScanner == null)
		{
			try
			{
				fileScanner = new Scanner (new File (dirName));
				setCellAndButton ();
				play ();
			}
			catch (Exception e)
			{
				speak ("Error! File path directory is not correct! Please consult a teacher " +
				"or administrator for assistance!");
				throw new IllegalArgumentException ("Error! File path directory is not correct!");
			}
		}
		else
		{
			throw new IllegalArgumentException ("Error! A scenario file already exists!");
		}
		
	}
	
	
/*	public static void main (String [] args)
	{
		SoundPlayer soundName = new SoundPlayer ();
//		VoiceManager vm = VoiceManager.getInstance(); 
//		Voice voice = vm.getVoice("kevin16");
//		voice.allocate(); 
		try
		{
			Scanner fileScan = new Scanner (new File(System.getProperty("user.dir") + "\\" + "Play.txt"));
			String temp;
			while (fileScan.hasNextLine ())
			{
				temp = fileScan.nextLine();
				if (temp.length () >= 6 && temp.substring(0, 6).equals("/~Mus:"))
				{
					soundName.playSound (temp.substring(6));
				}
				else
				{
					voice.speak(temp);
				}
			}
			fileScan.close ();
		}
		catch (Exception e)
		{

		}
	} */
}