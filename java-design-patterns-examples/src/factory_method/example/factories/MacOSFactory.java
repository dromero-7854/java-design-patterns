package factory_method.example.factories;

import factory_method.example.buttons.Button;
import factory_method.example.buttons.MacOSButton;
import factory_method.example.checkboxes.Checkbox;
import factory_method.example.checkboxes.MacOSCheckbox;

/**
 * Each concrete factory extends basic factory and responsible for creating
 * products of a single variety.
 */
public class MacOSFactory implements GUIFactory {

	@Override
	public Button createButton() {
		return new MacOSButton();
	}

	@Override
	public Checkbox createCheckbox() {
		return new MacOSCheckbox();
	}
}
