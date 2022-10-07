package state.example;

import state.example.ui.Player;
import state.example.ui.UI;

/**
 * Demo class. Everything comes together here.
 */
public class Demo {

	public static void main(String[] args) {
		Player player = new Player();
		UI ui = new UI(player);
		ui.init();
	}

}
