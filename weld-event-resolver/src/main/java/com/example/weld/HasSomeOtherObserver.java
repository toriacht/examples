package com.example.weld;

import javax.enterprise.event.Observes;
/**
 * 
 * @author marko
 *
 */
public class HasSomeOtherObserver {
	public void secondSimpleObserver(@Observes Integer event){}
}
