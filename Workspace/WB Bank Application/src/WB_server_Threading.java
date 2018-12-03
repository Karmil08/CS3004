import java.net.*;
import java.io.*;

public class WB_server_Threading extends Thread {

	private Socket WBSocket = null;
	  private WB_shared_state1 mySharedWBStateObject1;

	  private WB_shared_state2 mySharedWBStateObject2;

	  private WB_shared_state3 mySharedWBStateObject3;
	  private String myWBServerThreadName;
	  private double mySharedVariable;
	   
	  //Setup the thread
	  	public WB_server_Threading(Socket WBSocket, String WBServerThreadName, WB_shared_state1 ourSharedWBStateObject1,WB_shared_state2 ourSharedWBStateObject2,WB_shared_state3 ourSharedWBStateObject3) {
		
//		  super(WBServerThreadName);
		  this.WBSocket = WBSocket;
		  mySharedWBStateObject1 = ourSharedWBStateObject1;
		  mySharedWBStateObject2 = ourSharedWBStateObject2;
		  mySharedWBStateObject3 = ourSharedWBStateObject3;
		  myWBServerThreadName = WBServerThreadName;
		}

	  public void run() {
	    try {
	      System.out.println(myWBServerThreadName + "initialising.");
	      PrintWriter out = new PrintWriter(WBSocket.getOutputStream(), true);
	      BufferedReader in = new BufferedReader(new InputStreamReader(WBSocket.getInputStream()));
	      String inputLine, outputLine;

	      while ((inputLine = in.readLine()) != null) {
	    	  // Get a lock first
	    	  try { 
	    		  mySharedWBStateObject1.acquireLock();  
	    		  outputLine = mySharedWBStateObject1.processInput(myWBServerThreadName, inputLine);
	    		  out.println(outputLine);
	    		  mySharedWBStateObject1.releaseLock();  
	    		  mySharedWBStateObject2.acquireLock(); 
	    		  
	    		  
	    		  outputLine = mySharedWBStateObject2.processInput(myWBServerThreadName, inputLine);
	    		  out.println(outputLine);
	    		  mySharedWBStateObject2.releaseLock(); 
	    		  
	    		  outputLine = mySharedWBStateObject3.processInput(myWBServerThreadName, inputLine);
	    		  out.println(outputLine);
	    		  mySharedWBStateObject3.releaseLock();  
	    	  } 
	    	  catch(InterruptedException e) {
	    		  System.err.println("Failed to get lock when reading:"+e);
	    	  }
	      }

	       out.close();
	       in.close();
	       WBSocket.close();

	    } catch (IOException e) {
	      e.printStackTrace();
	    }
	  }
}
