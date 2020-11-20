/**
* -*- coding : utf-8 -*-
* @FileName  : Operation.java
* @Author    : Ruixiang JIANG (Songrise)
* @Time      : 2020-11-19
* @Github    ：https://github.com/songrise
* @Descriptions: Class for Operation to be used in criterion for better OOP design, 
referring to strategy pattern https://en.wikipedia.org/wiki/Strategy_pattern and simple factory methods.
**/

package hk.edu.polyu.comp.comp2021.cvfs.model.operation;

import java.io.Serializable;

public class ConcreteOperation implements Operation, Serializable {
    private static final long serialVersionUID = 2021L;
    private final String name;

    ConcreteOperation(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public boolean eval(Object operand1, Object operand2) {
        return false;
    }

}
