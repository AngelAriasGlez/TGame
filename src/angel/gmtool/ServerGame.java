package angel.gmtool;





/**
 *
 * @author aariasgonzalez
 */
public class ServerGame extends Game{
    Server mServer;
    ServerProtocol mProtocol;
    public ServerGame(ServerProtocol protocol){
        mProtocol = protocol;
        mServer =  new Server(4, this);
        mServer.start();
   
    }
  
    public ServerProtocol getProtocol(){
        return mProtocol;
    }
    
    public void broadcast(String out){
        mServer.broadToTasks(out);
    
    }
 
    
    
    
    
}

