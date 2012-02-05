package com.example.weld;

import javax.enterprise.event.Observes;
/**
 * 
 * @author marko
 *
 */
public class HasAnObserver {
	public void simpleObserver(@Observes SimpleClass event){
		
	}
}
