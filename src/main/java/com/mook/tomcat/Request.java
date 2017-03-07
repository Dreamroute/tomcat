package com.mook.tomcat;

import java.io.IOException;
import java.io.InputStream;

public class Request {

    private InputStream in;
    private String uri;
    
    public String getUri() {
        return uri;
    }

    public Request(InputStream in) {
        this.in = in;
    }

    public void parse() {
        byte[] bs = new byte[2 * 1024];
        int len = -1;
        try {
            len = in.read(bs);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        StringBuffer buffer = new StringBuffer(2 * 1024);
        for(int i=0; i<len; i++) {
            buffer.append((char) bs[i]);
        }
        
        System.err.println(buffer.toString());
        uri = parseUri(buffer.toString());
    }

    private String parseUri(String requestString) {
        int index1, index2;
        index1 = requestString.indexOf(' ');
        if (index1 != -1) {
          index2 = requestString.indexOf(' ', index1 + 1);
          if (index2 > index1)
            return requestString.substring(index1 + 1, index2);
        }
        return null;
    }
    
}
