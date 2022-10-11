package abstract_factory.example.factories;

import abstract_factory.example.buttons.Button;
import abstract_factory.example.buttons.LinuxButton;
import abstract_factory.example.checkboxes.Checkbox;
import abstract_factory.example.checkboxes.LinuxCheckbox;

/**
 * Each concrete factory extends basic factory and responsible for creating
 * products of a single variety.
 */
public class LinuxFactory implements GUIFactory {

	@Override
	public Button createButton() {
		return new LinuxButton();
	}

	@Override
	public Checkbox createCheckbox() {
		return new LinuxCheckbox();
	}
}
