package factory_method.example;

import factory_method.example.app.Application;
import factory_method.example.factories.GUIFactory;
import factory_method.example.factories.LinuxFactory;
import factory_method.example.factories.MacOSFactory;
import factory_method.example.factories.WindowsFactory;

/**
 * Demo class. Everything comes together here.
 */
public class Demo {

	/**
	 * Application picks the factory type and creates it in run time (usually at
	 * initialization stage), depending on the configuration or environment
	 * variables.
	 */
	private static Application configureApplication() {
		Application app;
		GUIFactory factory;
		String osName = System.getProperty("os.name").toLowerCase();
		if (osName.contains("mac")) {
			factory = new MacOSFactory();
		} else if (osName.contains("linux")) {
			factory = new LinuxFactory();
		} else {
			factory = new WindowsFactory();
		}
		app = new Application(factory);
		return app;
	}

	public static void main(String[] args) {
		Application app = configureApplication();
		app.paint();
	}
}
