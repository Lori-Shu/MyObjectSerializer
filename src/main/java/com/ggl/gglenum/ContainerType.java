package com.ggl.gglenum;

public enum ContainerType {
    ERROR(0),ARRAY(1),OBJECT(2), FIELD(3),HEADER(4),FOOTER(5);

    private byte byteNum;
    private ContainerType(int num){
        byteNum=(byte)num;
    }

    public byte getByteNum(){
        return byteNum;
    }
    public static ContainerType getFromByte(byte b){
        final byte fb=b;
        switch(fb){
            case 0:
            assert(false);
            case 1:
            return ARRAY;
            case 2:
            return OBJECT;
            case 3:
            return FIELD;
            case 4:
            return HEADER;
            case 5:
            return FOOTER;
            default:
            assert(false);
        }
        assert(false);
        return null;
    }
}
