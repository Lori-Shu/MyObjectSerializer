package com.ggl.entity;

import java.util.ArrayList;

import com.ggl.gglenum.ContainerType;

public class GglObject {
    private short nameLength;
    private byte[] name;
    private short fieldLength;
    private ArrayList<GglField> fields;
    private short arraysLength;
    private ArrayList<GglArray> arrays;
    private short objectsLength;
    private ArrayList<GglObject> objects;
    public GglObject(String oName){
        byte[] nameBytes=oName.getBytes();
        nameLength=(short)nameBytes.length;
        name=nameBytes;
        fieldLength=0;
        fields=new ArrayList<>();
        arraysLength=0;
        arrays=new ArrayList<>();
        objectsLength=0;
        objects=new ArrayList<>();
    }
    public void addField(GglField fd){
        ++fieldLength;
        fields.add(fd);
    }
    
    public void addArray(GglArray ay) {
        ++arraysLength;
        arrays.add(ay);
    }
    
    public void addObject(GglObject o) {
        ++objectsLength;
        objects.add(o);
    }
    
    public short getNameLength() {
        return nameLength;
    }
    
    public byte[] getName() {
        return name;
    }
    
    public short getFieldLength() {
        return fieldLength;
    }
    
    public ArrayList<GglField> getFields() {
        return fields;
    }
    
    public short getArraysLength() {
        return arraysLength;
    }
    
    public ArrayList<GglArray> getArrays() {
        return arrays;
    }
    
    public short getObjectsLength() {
        return objectsLength;
    }

    public ArrayList<GglObject> getObjects() {
        return objects;
    }
}
