package com.lujia.rpc.demo.domain;

import java.io.Serializable;

/**
 * @author :lujia
 * @date :2018/7/24  17:15
 */
public class RpcRequest implements Serializable {


    private static final long serialVersionUID = -41353252736343605L;
    private String methodName;

    private String className;

    private String version;

    private Object[] parameters;


    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Object[] getParameters() {
        return parameters;
    }

    public void setParameters(Object[] parameters) {
        this.parameters = parameters;
    }
}
