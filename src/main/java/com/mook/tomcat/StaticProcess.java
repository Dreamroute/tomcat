package com.mook.tomcat;

public class StaticProcess {

    public void process(Request request, Response response) {
        response.sendStaticResource();
    }

}
