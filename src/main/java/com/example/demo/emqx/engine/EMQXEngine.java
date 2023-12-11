package com.example.demo.emqx.engine;

import com.example.demo.emqx.annotation.EMQX;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author : songtc
 * @since : 2023/12/11 10:30
 */
public class EMQXEngine {

    @Resource
    ApplicationContext context;

    public void getAnno() {
        Map<String, EMQX> emqxMap = context.getBeansOfType(EMQX.class);
        Map<String, Component> componentMap = context.getBeansOfType(Component.class);
    }

    public static void main(String[] args) {
        new EMQXEngine().getAnno();
    }
}
