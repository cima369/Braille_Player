package enamel.player;

import enamel.simulator.SoundPlayer;

/**
 * This class is included as an example of how to use the classes in the simulator package.
 * 
 */

public class ToyPlayer {

	public static void main(String[] args) {

		SoundPlayer player = new SoundPlayer ();
		player.startFile("Scenario_One.txt");
	}

}
