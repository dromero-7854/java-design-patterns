package factory_method.example.factories;

import factory_method.example.buttons.Button;
import factory_method.example.checkboxes.Checkbox;

/**
 * Abstract factory knows about all (abstract) product types.
 */
public interface GUIFactory {
    Button createButton();
    Checkbox createCheckbox();
}
