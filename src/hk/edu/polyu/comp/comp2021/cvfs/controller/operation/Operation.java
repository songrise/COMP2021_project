/**
* -*- coding : utf-8 -*-
* @FileName  : Operation.java
* @Author    : Ruixiang JIANG (Songrise)
* @Time      : 2020-11-19
* @Github    ：https://github.com/songrise
* @Descriptions: Class for Operation to be used in criterion for better OOP design, 
referring to strategy pattern https://en.wikipedia.org/wiki/Strategy_pattern
**/

package hk.edu.polyu.comp.comp2021.cvfs.controller.operation;

public class Operation {
    private final String name;

    Operation(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public boolean eval(Object operand1, Object operand2) {
        return false;
    }

}
