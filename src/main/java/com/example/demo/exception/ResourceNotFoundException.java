package com.example.demo.exception;

public class ResourceNotFoundException extends RuntimeException {
    private String resourceName;
    private String fieldName;
    private Object value;

    public ResourceNotFoundException(String message){
        super(message);
    }



    public ResourceNotFoundException(String resourceName,String fieldName,Object value){
        super(String.format("resources not found",resourceName,value,fieldName));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.value = value;
    }




    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
