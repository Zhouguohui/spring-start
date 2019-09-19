package com.spring.start.zookeeper.zklock.enums;

/**
 * Created by 50935 on 2019/9/19.
 */
public enum LockPath {

    test("1","/test_lock");

    private String name;
    private String value;

    LockPath(String name,String value){
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
