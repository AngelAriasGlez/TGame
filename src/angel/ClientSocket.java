/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package angel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Angel
 */
public class ClientSocket {
    private BufferedReader mInput;
    private OutputStream mOutput;  
    private Socket mSocket; 
    
    public ClientSocket() {

    }
    
    public void connect(String address) throws IOException{
            mSocket = new Socket(address, 8000);
            mInput  = new BufferedReader(new InputStreamReader(mSocket.getInputStream()));
            mOutput = mSocket.getOutputStream();
    
    }
    

    public String read() throws IOException{
        if(mInput != null){
            return mInput.readLine();
        }
        return null;
    }
    public void send(String out) throws IOException{
        if(mOutput != null){
            out += "\n";
            mOutput.write(out.getBytes());
            mOutput.flush();
        }
 
    }
    public void send(String cmd, String data) throws IOException{
        send(cmd + " " + data);
    }
    
}
