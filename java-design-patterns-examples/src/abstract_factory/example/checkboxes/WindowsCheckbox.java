package abstract_factory.example.checkboxes;

/**
 * All products families have the same varieties (MacOS/Windows/Linux).
 *
 * This is another variant of a checkbox.
 */
public class WindowsCheckbox implements Checkbox {

    @Override
    public void paint() {
        System.out.println("You have created WindowsCheckbox.");
    }
}
