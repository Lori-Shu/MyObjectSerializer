package com.ggl.entity;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;

import com.ggl.gglenum.ContainerType;
import com.ggl.gglenum.GglType;

public class GglArray<T> {
    private static final byte CONTAINER_TYPE = ContainerType.ARRAY.getByteNum();
    private short nameLength;
    private byte[] name;
    private byte type;
    private int count;
    private T[] data;
    public GglArray(String aName,GglType t,int aCount,T[] aData){
        byte[] strBytes = aName.getBytes();
        nameLength=(short)strBytes.length;
        name=strBytes;
        type=t.getTypeNum();
        count=aCount;
        data=aData;        
    }
    public byte getContainerType(){
        return CONTAINER_TYPE;
    }
    
    public short getNameLength() {
        return nameLength;
    }
    
    public byte[] getName() {
        return name;
    }
    
    public byte getType() {
        return type;
    }
    
    public int getCount() {
        return count;
    }
    
    public T[] getData() {
        return data;
    }

}
