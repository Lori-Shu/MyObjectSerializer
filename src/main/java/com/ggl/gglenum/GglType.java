package com.ggl.gglenum;

public enum GglType {
    ERROR(0),BYTE(1),SHORT(2),INT(3),LONG(4),FLOAT(5),DOUBLE(6),BOOLEAN(7);
    private byte typeNum;

    private GglType(int num){
        typeNum=(byte)num;
    }
    public byte getTypeNum(){
        return typeNum;
    }
    public static GglType getFromByte(byte b){
        final byte fb=b;
        switch (fb) {
            case 0:
                assert(false);
                break;
            case 1:
            return BYTE;
            case 2:
            return SHORT;
            case 3:
            return INT;
            case 4:
            return LONG;
            case 5:
            return FLOAT;
            case 6:
            return DOUBLE;
            case 7:
            return BOOLEAN; 
            default:
            assert(false);
            break;
        }
        assert(false);
        return null;
    }
}
