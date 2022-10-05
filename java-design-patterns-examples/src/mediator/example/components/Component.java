package mediator.example.components;

import mediator.example.mediator.Mediator;

/**
 * Common component interface.
 */
public interface Component {
	
	 void setMediator(Mediator mediator);
	    
	 String getName();

}
