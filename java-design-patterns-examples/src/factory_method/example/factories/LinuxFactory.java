package factory_method.example.factories;

import factory_method.example.buttons.Button;
import factory_method.example.buttons.LinuxButton;
import factory_method.example.checkboxes.Checkbox;
import factory_method.example.checkboxes.LinuxCheckbox;

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
