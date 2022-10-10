package factory_method.example.factories;

import factory_method.example.buttons.Button;
import factory_method.example.buttons.WindowsButton;
import factory_method.example.checkboxes.Checkbox;
import factory_method.example.checkboxes.WindowsCheckbox;

/**
 * Each concrete factory extends basic factory and responsible for creating
 * products of a single variety.
 */
public class WindowsFactory implements GUIFactory {

    @Override
    public Button createButton() {
        return new WindowsButton();
    }

    @Override
    public Checkbox createCheckbox() {
        return new WindowsCheckbox();
    }
}