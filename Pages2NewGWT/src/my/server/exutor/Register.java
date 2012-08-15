package my.server.exutor;

import org.apache.log4j.Logger;

import my.client.rpcs.RPCServiceExeption;
import my.server.RPCServiceImpl;


public class Register {

	public static final Logger LOG=Logger.getLogger(Register.class);
	
	public Register() {
		
	}
	
	public int executeRegister() throws RPCServiceExeption {
		//if (true) 
			//throw new RPCServiceExeption("Eto strashnaya oshibka");
		LOG.info("executeRegister LOG4J");	
		return 4;
	}
	
}
