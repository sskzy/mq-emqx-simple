package com.example.demo.readServer.listener;

import com.example.demo.readServer.annotation.A;
import com.example.demo.readServer.annotation.B;
import org.springframework.stereotype.Component;

/**
 * @author : songtc
 * @since : 2023/12/10 20:53
 */
@A("a")
@Component
public class TcListener {

    @B("b")
    public void sayHello(String obj) {

    }

}
