/**
 * 
 */
package com.acmeair.faultTolerance;

/**
 * @author jagraj
 *
 */
public class ConnectException extends Exception{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2263099077380260488L;

	public ConnectException(String message){
		super(message);
	}
}