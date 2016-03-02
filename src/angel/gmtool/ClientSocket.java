/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package angel.gmtool;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author Angel
 */
interface IClientSocketListener{
    public void onDataReceived(String in);
    public void onDisconnect();
    public void onErrorToConnect();
}

public class ClientSocket extends Thread{
    private IClientSocketListener mListener = null;
    
    private SocketChannel mChannel;
    private Selector mSelector;

    private BlockingQueue<String> mOutQueue = new ArrayBlockingQueue(128);
    
    public ClientSocket() {

    }
    
    public void setListener(IClientSocketListener listener){
        mListener = listener;
    }

    
    public void connect(String addr){
        

        try {
            InetAddress serverIPAddress = InetAddress.getByName(addr);
            int port = 8000;
            InetSocketAddress serverAddress = new InetSocketAddress(serverIPAddress, port);
            mSelector = Selector.open();
            mChannel = SocketChannel.open();
            mChannel.configureBlocking(false);
            mChannel.connect(serverAddress);
            int operations = SelectionKey.OP_CONNECT | SelectionKey.OP_READ | SelectionKey.OP_WRITE;
            mChannel.register(mSelector, operations);
            start();
        
        } catch (IOException ex ) {
            Logger.getLogger(ClientSocket.class.getName()).log(Level.SEVERE, null, ex);
        }

    
    }
    
    
    public boolean processReadySet(Set readySet) throws Exception {
      Iterator iterator = readySet.iterator();
      while (iterator.hasNext()) {
        SelectionKey key = (SelectionKey)iterator.next();
        iterator.remove();
        if (key.isConnectable()) {
          boolean connected = processConnect(key);
          if (!connected) {
            return true; // Exit
          }
        }
        if (key.isReadable()) {
          String msg = processRead(key);
          if(mListener != null){
              String[] lines = msg.split(System.lineSeparator());
              for(String l : lines){
                mListener.onDataReceived(l);
              }
          }
          System.out.println("[Server]: " + msg);
        }
        if (key.isWritable() && mOutQueue.size() > 0) {
          String msg = mOutQueue.take();
          System.out.println("[Client]: " + msg);

          SocketChannel sChannel = (SocketChannel) key.channel();
          ByteBuffer buffer = ByteBuffer.wrap(msg.getBytes());
          sChannel.write(buffer);
        }
      }
      return false; // Not done yet
    }
    public static boolean processConnect(SelectionKey key) throws Exception{
        SocketChannel channel = (SocketChannel) key.channel();
        while (channel.isConnectionPending()) {
          channel.finishConnect();
        }
        return true;
    }
    public static String processRead(SelectionKey key) throws Exception {
        SocketChannel sChannel = (SocketChannel) key.channel();
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        sChannel.read(buffer);
        buffer.flip();
        Charset charset = Charset.forName("UTF-8");
        CharsetDecoder decoder = charset.newDecoder();
        CharBuffer charBuffer = decoder.decode(buffer);
        String msg = charBuffer.toString();
        return msg;
    }

    public void send(String out) throws IOException{
        mOutQueue.add(out + System.lineSeparator());
    }
    public void send(String cmd, String data) throws IOException{
        send(cmd + " " + data);
    }

    @Override
    public void run(){
        try {
            while (true) {
                    if (mSelector.select() > 0) {
                        boolean doneStatus = processReadySet(mSelector.selectedKeys());
                        if (doneStatus) {
                            break;
                        }
                    } 
            }
             mChannel.close();
        } catch (IOException ex) {
            

        } catch (Exception ex) {

        }       

    }
    
}
