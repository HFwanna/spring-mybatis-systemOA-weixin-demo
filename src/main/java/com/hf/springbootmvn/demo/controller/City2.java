package com.hf.springbootmvn.demo.controller;

import java.io.IOException;
import java.net.Socket;

public class City2 {
    private Integer id2;
    private String name4;

    public Integer getId() {
        return id2;
    }

    public void setId(Integer id) {
        this.id2 = id;
    }

    public String getName() throws IOException {
        Socket s = new Socket("192.168.1.107", 20000);
        s.getOutputStream().flush();
        return name4;
    }

    public void setName(String name) {
        this.name4 = name;
    }
}
