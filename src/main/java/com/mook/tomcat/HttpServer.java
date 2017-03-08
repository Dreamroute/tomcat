package com.mook.tomcat;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class HttpServer {

    public static void main(String[] args) {
        new HttpServer().await();
    }
    
    public void await() {
        
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(8080, 1, InetAddress.getByName("127.0.0.1"));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
            Runtime.getRuntime().exit(1);
        }
        
        while(true) {
            Socket socket = null;
            InputStream in = null;
            OutputStream out = null;
            try {
                socket = serverSocket.accept();
                in = socket.getInputStream();
                out = socket.getOutputStream();
                
                // create request object
                Request request = new Request(in);
                request.parse();
                
                // create response object
                Response response = new Response(out);
                response.setRequest(request);
                
                // invoke
                String uri = request.getUri();
                if(uri.startsWith("/servlet")) {
                    ServletProcess servletProcess = new ServletProcess();
                    servletProcess.process(request, response);
                } else {
                    StaticProcess staticProcess = new StaticProcess();
                    staticProcess.process(request, response);
                }
                
                socket.close();
                
            } catch (IOException e) {
                e.printStackTrace();
                try {
                    serverSocket.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }
    
}
