package com.mook.tomcat;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Locale;

import javax.servlet.ServletOutputStream;
import javax.servlet.ServletResponse;

public class Response implements ServletResponse {

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
        String path = userDir + "/src/main/java/webroot" + uri;
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

    @Override
    public String getCharacterEncoding() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getContentType() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public PrintWriter getWriter() throws IOException {
        return new PrintWriter(out, true);
    }

    @Override
    public void setCharacterEncoding(String charset) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void setContentLength(int len) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void setContentType(String type) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void setBufferSize(int size) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public int getBufferSize() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void flushBuffer() throws IOException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void resetBuffer() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public boolean isCommitted() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void reset() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void setLocale(Locale loc) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public Locale getLocale() {
        // TODO Auto-generated method stub
        return null;
    }
    
}
