package com.mook.tomcat;

import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLStreamHandler;

import javax.servlet.Servlet;
import javax.servlet.ServletException;

public class ServletProcess {

    public void process(Request request, Response response) {
        String uri = request.getUri();
        String servletName = uri.substring(uri.lastIndexOf("/") + 1);
        URLClassLoader loader = null;

        URL[] urls = new URL[1];
        URLStreamHandler handler = null;
        try {
            String repository = (new URL("file", null, "")).toString();
            urls[0] = new URL(null, repository, handler);
            loader = new URLClassLoader(urls);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        Class<?> myClass = null;
        try {
            myClass = loader.loadClass("webroot." + servletName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        }
        
        Servlet servlet = null;
        try {
            servlet = (Servlet) myClass.newInstance();
            servlet.service(new RequestFacade(request), new ResponseFacade(response));
        } catch (InstantiationException | IllegalAccessException | ServletException | IOException e) {
            e.printStackTrace();
        }
    }
//    
//    public static void main(String[] args) throws Exception {
//        URL[] urls = new URL[] {new URL("file", null, System.getProperty("user.dir") + File.separator + "webroot" + File.separator)};
//        URLClassLoader ucl = new URLClassLoader(urls);
//        Class<?> c = ucl.loadClass("webroot.PrimitiveServlet");
//        PrimitiveServlet ps = (PrimitiveServlet) c.newInstance();
//        ps.service(null, null);
//        ucl.close();
//    }
    
}



