package com.ggl.entity;

import com.ggl.gglenum.ContainerType;
import com.ggl.gglenum.GglType;

public class GglField<T> {
    private short nameLength;
    private byte[] name;
    private byte type;
    private T data;
    public GglField(String fName,GglType gType,T mData){
        byte[] bytes= fName.getBytes();
        nameLength=(short) bytes.length;
        name=bytes;
        type=gType.getTypeNum();
        data=mData;
    }

    public short getNameLength(){
        return nameLength;
    }
    public byte[] getName(){
        return name;
    }
    public byte getType(){
        return type;
    }
    public T getData(){
        return data;
    }

}
