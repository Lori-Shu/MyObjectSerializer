package com.ggl.util;

import com.ggl.entity.GglArray;
import com.ggl.entity.GglField;
import com.ggl.entity.GglObject;
import com.ggl.gglenum.ContainerType;
import com.ggl.gglenum.GglType;

public class GglDeSerializer {
    private int index;
    public GglDeSerializer(){
        index=0;
    }
    
    /**
     * 一系列从二进制buffer读取基本数据的方法
     */
    public byte readByte(byte[] target) {
        byte res = target[index];
        ++index;
        return res;
    }

    public byte[] readByteArray(byte[] target, int length) {
        byte[] ay = new byte[length];
        int i = 0;
        for (;;) {
            if (i == length) {
                break;
            }
            ay[i] = readByte(target);
            ++i;
        }
        return ay;
    }

    public short readShort(byte[] target) {
        byte b1 = readByte(target);
        byte b2 = readByte(target);
        return (short) ((b1 << 8 & 0xff00) | (b2 << 0 & 0xff));
    }

    public int readInt(byte[] target) {
        short s1 = readShort(target);
        short s2 = readShort(target);
        return ((s1 << 16 & 0xffff0000) | (s2 << 0 & 0xffff));
    }

    public long readLong(byte[] target) {
        int i1 = readInt(target);
        int i2 = readInt(target);
        long l1 = i1;
        long l2 = i2;
        System.out.println(l1);
        System.out.println(l2);
        return ((l1 << 32 & 0xffffffff00000000l) | (l2 << 0 & 0xffffffffl));
    }

    public float readFloat(byte[] target) {
        int i1 = readInt(target);
        return Float.intBitsToFloat(i1);
    }

    public double readDouble(byte[] target) {
        long l1 = readLong(target);
        return Double.longBitsToDouble(l1);
    }

    public boolean readBoolean(byte[] target) {
        byte b1 = readByte(target);
        return b1 != 0;
    }

   
    
    /**
     * 读取下一个ContainerType
     * 
     * @param target
     * @param fd
     */
    public ContainerType readNextContainerType(byte[] target) {
        byte b = readByte(target);
        return ContainerType.getFromByte(b);
    }

    /**
     * 从buffer读取field
     * 
     * @param target
     * @param fd
     */
    public GglField readField(byte[] target) {
        short nameLength = readShort(target);
        byte[] name = readByteArray(target, nameLength);
        String str = new String(name);
        byte type = readByte(target);
        final GglType t = GglType.getFromByte(type);
        switch (t) {
            case BYTE:
                byte bData = readByte(target);
                return new GglField<Byte>(str, t, bData);
            case SHORT:
                short sData = readShort(target);
                return new GglField<Short>(str, t, sData);
            case INT:
                int iData = readInt(target);
                return new GglField<Integer>(str, t, iData);
            case LONG:
                long lData = readLong(target);
                return new GglField<Long>(str, t, lData);
            case FLOAT:
                float fData = readFloat(target);
                return new GglField<Float>(str, t, fData);
            case DOUBLE:
                double dData = readDouble(target);
                return new GglField<Double>(str, t, dData);
            case BOOLEAN:
                boolean boolData = readBoolean(target);
                return new GglField<Boolean>(str, t, boolData);
            default:
                assert (false);
                break;
        }
        assert (false);
        return null;

    }

    /**
     * 读取一个gglarray
     */
    public GglArray readArray(byte[] target) {
        short nameLength = readShort(target);
        byte[] name = readByteArray(target, nameLength);
        String str = new String(name);
        byte type = readByte(target);
        int count = readInt(target);
        final GglType t = GglType.getFromByte(type);
        int tempIndex = 0;
        switch (t) {
            case BYTE:
                Byte[] bArray = new Byte[count];
                for (;;) {
                    if (tempIndex == count) {
                        break;
                    }
                    bArray[tempIndex] = readByte(target);
                    ++tempIndex;
                }
                return new GglArray<Byte>(str, t, count, bArray);
            case SHORT:
                Short[] sArray = new Short[count];
                for (;;) {
                    if (tempIndex == count) {
                        break;
                    }
                    sArray[tempIndex] = readShort(target);
                    ++tempIndex;
                }
                return new GglArray<Short>(str, t, count, sArray);
            case INT:
                Integer[] iArray = new Integer[count];
                for (;;) {
                    if (tempIndex == count) {
                        break;
                    }
                    iArray[tempIndex] = readInt(target);
                    ++tempIndex;
                }
                return new GglArray<Integer>(str, t, count, iArray);
            case LONG:
                Long[] lArray = new Long[count];
                for (;;) {
                    if (tempIndex == count) {
                        break;
                    }
                    lArray[tempIndex] = readLong(target);
                    ++tempIndex;
                }
                return new GglArray<Long>(str, t, count, lArray);
            case FLOAT:
                Float[] fArray = new Float[count];
                for (;;) {
                    if (tempIndex == count) {
                        break;
                    }
                    fArray[tempIndex] = readFloat(target);
                    ++tempIndex;
                }
                return new GglArray<Float>(str, t, count, fArray);
            case DOUBLE:
                Double[] dArray = new Double[count];
                for (;;) {
                    if (tempIndex == count) {
                        break;
                    }
                    dArray[tempIndex] = readDouble(target);
                    ++tempIndex;
                }
                return new GglArray<Double>(str, t, count, dArray);
            case BOOLEAN:
                Boolean[] boolArray = new Boolean[count];
                for (;;) {
                    if (tempIndex == count) {
                        break;
                    }
                    boolArray[tempIndex] = readBoolean(target);
                    ++tempIndex;
                }
                return new GglArray<Boolean>(str, t, count, boolArray);
            default:
                assert (false);
                break;
        }
        assert (false);
        return null;

    }

    /**
     * 从bytebuffer读取gglobject
     */
    public GglObject readObject(byte[] target) {
        short nameLength = readShort(target);
        byte[] name = readByteArray(target, nameLength);
        String str = new String(name);
        GglObject gglObject = new GglObject(str);
        short fieldLength = readShort(target);
        for (int index = 0; index < fieldLength; ++index) {
            ContainerType t = readNextContainerType(target);
            assert (t == ContainerType.FIELD);
            GglField readField = readField(target);
            gglObject.addField(readField);
        }
        short arraysLength = readShort(target);
        for (int index = 0; index < arraysLength; ++index) {
            ContainerType t = readNextContainerType(target);
            assert (t == ContainerType.ARRAY);
            GglArray readArray = readArray(target);
            gglObject.addArray(readArray);
        }
        return gglObject;
    }
}
