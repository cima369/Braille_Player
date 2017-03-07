package enamel.simulator;

import static org.junit.Assert.*;

import org.junit.Test;

public class SoundPlayerTest {

	@Test
	public void testRaisePin() {
		// Raise pin
		SoundPlayer test1 = new SoundPlayer();
		test1.sim = new Simulator(4, 3);
		test1.performAction("/~disp-cell-raise:1 4");
		assertTrue(test1.sim.getCell(1).radio2x2.isSelected());

		test1.performAction("/~disp-cell-raise:2 2");
		assertTrue(test1.sim.getCell(2).radio1x2.isSelected());

		test1.performAction("/~disp-cell-raise:2 4");
		assertFalse(test1.sim.getCell(3).radio3x1.isSelected());

		test1.performAction("/~disp-cell-raise:3 5");
		assertFalse(test1.sim.getCell(1).radio3x1.isSelected());
	}

	public void testLowerPin() {
		// lower pins
		SoundPlayer test2 = new SoundPlayer();
		test2.sim = new Simulator(6, 3);
		test2.performAction("/~disp-cell-raise:1 4");
		test2.performAction("/~disp-cell-lower:1 4");
		assertFalse(test2.sim.getCell(1).radio2x2.isSelected());

		test2.performAction("/~disp-cell-raise:2 5");
		test2.performAction("/~disp-cell-lower:2 5");
		assertFalse(test2.sim.getCell(2).radio3x1.isSelected());

		// if the wrong one is lowered then its still raised
		test2.performAction("/~disp-cell-raise:3 4");
		test2.performAction("/~disp-cell-raise:2 4");
		test2.performAction("/~disp-cell-lower:2 4");
		assertTrue(test2.sim.getCell(3).radio2x2.isSelected());
		assertFalse(test2.sim.getCell(2).radio2x2.isSelected());

		// if not its lowered then its still raised
		test2.performAction("/~disp-cell-raise:4 7");
		;
		assertTrue(test2.sim.getCell(4).radio4x1.isSelected());
	}

	public void testdisplaycellPin() {
		// disp cell pins
		SoundPlayer test3 = new SoundPlayer();
		test3.sim = new Simulator(5, 4);
		test3.performAction("/~disp-cell-pins:1 10010010");
		assertTrue(test3.sim.getCell(1).radio2x2.isSelected());

		test3.performAction("/~disp-cell-pins:3 10010010");
		assertTrue(test3.sim.getCell(3).radio2x2.isSelected());

		test3.performAction("/~disp-cell-pins:3 10001010");
		assertFalse(test3.sim.getCell(1).radio2x1.isSelected());

		test3.performAction("/~disp-cell-pins:4 10001010");
		assertFalse(test3.sim.getCell(3).radio2x1.isSelected());

	}

	public void testClearall() {
		// Clear all
		SoundPlayer test4 = new SoundPlayer();
		test4.sim = new Simulator(4, 4);
		test4.performAction("/~disp-cell-raise:1 2");
		test4.performAction("/~disp-clearAll");
		assertFalse(test4.sim.getCell(1).radio2x2.isSelected());

		test4.performAction("/~disp-cell-raise:2 3");
		test4.performAction("/~disp-clearAll");
		assertFalse(test4.sim.getCell(2).radio2x2.isSelected());
		// stays raised if its not cleared.
		test4.performAction("/~disp-cell-raise:3 2");
		assertTrue(test4.sim.getCell(3).radio1x2.isSelected());
		test4.performAction("/~disp-cell-raise:1 3");
		assertTrue(test4.sim.getCell(1).radio2x1.isSelected());
	}

	public void testCellClear() {
		// Cell clear
		SoundPlayer test5 = new SoundPlayer();
		test5.sim = new Simulator(6, 4);
		test5.performAction("/~disp-cell-raise:1 2");
		test5.performAction("/~disp-cell-clear:1");
		assertFalse(test5.sim.getCell(1).radio2x2.isSelected());

		test5.performAction("/~disp-cell-raise:2 3");
		test5.performAction("/~disp-cell-clear:2");
		assertFalse(test5.sim.getCell(2).radio2x1.isSelected());
		// clear the wrong one
		test5.performAction("/~disp-cell-raise:2 3");
		test5.performAction("/~disp-cell-clear:3");
		assertTrue(test5.sim.getCell(2).radio2x1.isSelected());
		// if never cleared
		test5.performAction("/~disp-cell-raise:2 3");
		assertFalse(test5.sim.getCell(1).radio2x2.isSelected());
	}

	public void testDisplayStringPin() {
		// String
		SoundPlayer test6 = new SoundPlayer();
		test6.sim = new Simulator(5, 4);
		test6.performAction("/~disp-string: hew");

		// this was for help to actually see the string disp
		// test6.performAction("/~pause: 2");

		// check two diffrent point on the braille
		assertTrue(test6.sim.getCell(1).radio2x2.isSelected());
		assertTrue(test6.sim.getCell(3).radio2x1.isSelected());

		// check two diffrent that are not displaying the string
		assertFalse(test6.sim.getCell(0).radio2x2.isSelected());
		assertFalse(test6.sim.getCell(2).radio2x1.isSelected());

	}

	public void testDisplayCellChar() {
		// Char
		SoundPlayer test7 = new SoundPlayer();
		test7.sim = new Simulator(6, 2);
		test7.performAction("/~disp-cell-char:0 A");
		test7.performAction("/~pause: 1");
		assertTrue(test7.sim.getCell(0).radio1x1.isSelected());

		test7.performAction("/~disp-cell-char:1 C");
		test7.performAction("/~pause: 1");
		assertTrue(test7.sim.getCell(1).radio1x1.isSelected());

		// check for using wrong cell
		test7.performAction("/~disp-cell-char:3 D");
		test7.performAction("/~pause: 1");
		assertFalse(test7.sim.getCell(2).radio1x1.isSelected());

		test7.performAction("/~disp-cell-char:2 C");
		test7.performAction("/~pause: 1");
		assertFalse(test7.sim.getCell(4).radio1x1.isSelected());

		/*
		 * 
		 * SoundPlayer test8 = new SoundPlayer (); test8.sim = new Simulator (2,
		 * 4); test7.performAction("/~disp-cell-char:0 A"); assertTrue
		 * (test7.sim.getCell(1).radio2x2.isSelected());
		 */
	}

	public void testRepeat() {

		SoundPlayer test9 = new SoundPlayer();
		test9.sim = new Simulator(5, 1);
		test9.performAction("/~disp-string: hew");
		test9.performAction("/~repeat");
		test9.performAction("press button to continue?");
		assertFalse(test9.sim.getButton(0).isSelected());
		test9.performAction("/~endrepeat");

		test9.performAction("/~disp-string:yes");
		test9.performAction("/~set-voice:2");
		test9.performAction("/~repeat");
		test9.performAction("Moving on to the next question. What is the answer to braille two?");
		test9.performAction("The choices are Yes, No");
		assertFalse(test9.sim.getButton(0).isSelected());
		test9.performAction("/~endrepeat");
	}

	public void testPause() {
		// main test is for pause but also added sound into it too
		SoundPlayer test10 = new SoundPlayer();
		test10.sim = new Simulator(5, 3);
		test10.performAction("after a pause for three seconds please a sound will play.");
		test10.performAction("/~pause:3");
		test10.performAction("/~sound:correct.wav");

	}

	public void testResetbutton()

	{
		// test for reset button
		SoundPlayer test11 = new SoundPlayer();
		test11.sim = new Simulator(5, 6);
		test11.performAction("/~skip-button:0 HI");
		test11.performAction("/~skip-button:1 HELLO");
		test11.performAction("/~reset-buttons");
		test11.performAction("/~skip-button:0 HELLO");
		test11.performAction("/~skip-button:1 HI");
		// its fails since its reset
		assertFalse(test11.sim.getButton(0).isDefaultButton());
		assertFalse(test11.sim.getButton(1).isDefaultButton());

		// test to see if it passes if its not reset. not working yet
		SoundPlayer test12 = new SoundPlayer();
		test12.sim = new Simulator(5, 6);
		assertTrue(test12.sim.getButton(0).isDefaultButton());
		assertTrue(test12.sim.getButton(1).isDefaultButton());

	}

}