import java.io.IOException;
import java.net.*;

public class WB_server {
	  public static void main(String[] args) throws IOException {

			ServerSocket WBServerSocket = null;
		    boolean listening = true;
		    String WBServerName = "WBServer";
		    int WBServerNumber = 4553;
		    
		    double SharedVariable1 = 1000; //creating starting balance of 1000 units across all 3 accounts 
		    double SharedVariable2 = 1000;
		    double SharedVariable3 = 1000;
		    //Create the shared object in the global scope...
		    
		    WB_shared_state1 ourSharedWBStateObject1 = new WB_shared_state1(SharedVariable1);
		    WB_shared_state2 ourSharedWBStateObject2 = new WB_shared_state2(SharedVariable2);
		    WB_shared_state3 ourSharedWBStateObject3 = new WB_shared_state3(SharedVariable3);
		    // Make the server socket

		    try {
		      WBServerSocket = new ServerSocket(WBServerNumber);
		    } catch (IOException e) {
		      System.err.println("Could not start " + WBServerName + " specified port.");
		      System.exit(-1);
		    }
		    System.out.println(WBServerName + " started");

		    //Got to do this in the correct order with only four clients!  Can automate this...
		    
		    while (listening){
		      new WB_server_Threading(WBServerSocket.accept(), "WB_server_Threading1", ourSharedWBStateObject1, ourSharedWBStateObject2, ourSharedWBStateObject3).start();
		      new WB_server_Threading(WBServerSocket.accept(), "WB_server_Threading2", ourSharedWBStateObject1, ourSharedWBStateObject2, ourSharedWBStateObject3).start();
		      new WB_server_Threading(WBServerSocket.accept(), "WB_server_Threading3", ourSharedWBStateObject1, ourSharedWBStateObject2, ourSharedWBStateObject3).start();
		      System.out.println("New " + WBServerName + " thread started.");
		    }
		    WBServerSocket.close();
}
}