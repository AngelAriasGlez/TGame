/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package angel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server {
    private int mMaxPlayers;
    private Protocol mProtocol;
    public Server(int maxPlayers, Protocol protocol){
        mMaxPlayers = maxPlayers;
        mProtocol = protocol;
    }

    public void start() {
        
        final ExecutorService clientProcessingPool = Executors.newFixedThreadPool(mMaxPlayers);

        Runnable serverTask = new Runnable() {
            @Override
            public void run() {
                try {
                    ServerSocket serverSocket = new ServerSocket(8000);
                    System.out.println("Waiting for clients to connect...");
                    while (true) {
                        Socket clientSocket = serverSocket.accept();
                        PlayerTask pt = new PlayerTask(clientSocket, mProtocol);
                        clientProcessingPool.submit(pt);
                    }
                } catch (IOException e) {
                    System.err.println("Unable to process client request");
                }
            }
        };
        Thread serverThread = new Thread(serverTask);
        serverThread.start();

    }

    private class PlayerTask implements Runnable {
        private final Socket mClientSocket;
        private Protocol mProtocol;

        private PlayerTask(Socket clientSocket, Protocol protocol) {
            mClientSocket = clientSocket;
            mProtocol = protocol;
        }

        @Override
        public void run() {
            System.out.println("Got a client !");

            try {
                BufferedReader in = new BufferedReader(new InputStreamReader(mClientSocket.getInputStream()), 2048);
                OutputStream out = mClientSocket.getOutputStream();
                while(!mClientSocket.isClosed()){
                   String result = mProtocol.process(in.readLine(), mClientSocket.getInetAddress());
                   if(result != null){
                        out.write(result.getBytes());
                        out.
                    }
                    
                }
            } catch (IOException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }

            try {
                mClientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}