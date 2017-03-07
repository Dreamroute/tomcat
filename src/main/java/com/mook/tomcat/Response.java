package com.mook.tomcat;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;

public class Response {

    private OutputStream out;
    private Request request;
    
    public Response(OutputStream out) {
        this.out = out;
    }

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    public void sendStaticResource() {
        String userDir = System.getProperty("user.dir");
        String uri = request.getUri();
        String path = userDir + "/src/main/resources" + uri;
        File staticFile = new File(path);
        if(staticFile.exists()) {
            FileInputStream is = null;
            try {
                is = new FileInputStream(new File(path));
            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            }
            byte[] buffer = new byte[1024];
            try {
                int len = is.read(buffer, 0, buffer.length);
                while(len != -1) {
                    out.write(buffer, 0, len);
                    len = is.read(buffer, 0, buffer.length);
                }
                out.flush();
                out.close();
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            String errorMessage = "HTTP/1.1 404 File Not Found\r\n" +
                    "Content-Type: text/html\r\n" +
                    "Content-Length: 23\r\n" +
                    "\r\n" +
                    "<h1>File Not Found</h1>";
                  try {
                    out.write(errorMessage.getBytes());
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
    }
    
}
