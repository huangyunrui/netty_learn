package com.hyr.rpc.protocol;



import java.io.Serializable;
import java.util.Arrays;


public class InvokerProtocol implements Serializable {
    private String className;
    private String methodName;
    private Class<?>[] parames;
    private Object[] values;


    public Class<?>[] getParames() {
        return parames;
    }

    public Object[] getValues() {
        return values;
    }

    public String getClassName() {
        return className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public void setParames(Class<?>[] parames) {
        this.parames = parames;
    }

    public void setValues(Object[] values) {
        this.values = values;
    }

    @Override
    public String toString() {
        return "InvokerProtocol{" +
                "className='" + className + '\'' +
                ", methodName='" + methodName + '\'' +
                ", parames=" + Arrays.toString(parames) +
                ", values=" + Arrays.toString(values) +
                '}';
    }
}
