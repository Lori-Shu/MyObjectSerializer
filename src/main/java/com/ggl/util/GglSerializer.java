package com.ggl.util;

import java.io.FileOutputStream;
import java.nio.file.Path;

import javax.management.RuntimeErrorException;

import com.ggl.entity.GglArray;
import com.ggl.entity.GglField;
import com.ggl.entity.GglObject;
import com.ggl.gglenum.ContainerType;
import com.ggl.gglenum.GglType;

/*
 * 一个文件对应一个序列化对象
 */
public class GglSerializer {
    private int index;
    private byte[] validateHeader;
    private byte[] version;

    public GglSerializer() {
        index = 0;
        validateHeader = "gglobj".getBytes();
        version = new byte[] { (byte) 0b1, (byte) 0b1 };
    }

    public void resetCurrentIndex() {
        index = 0;
    }

    public void setCurrentIndex(int i) {
        index = i;
    }

    public void writeHeader(byte[] target) {
        int index = 0;
        for (;;) {
            if (index == validateHeader.length) {
                break;
            }
            writeByte(target, validateHeader[index]);
            ++index;
        }
        index = 0;
        for (;;) {
            if (index == version.length) {
                break;
            }
            writeByte(target, version[index]);
            ++index;
        }
    }
    public void writeToFile(byte[] target,Path path){
        assert(path.isAbsolute());
        
        try(FileOutputStream fos = new FileOutputStream(path.toFile())) {
            fos.write(target);
        } catch (Exception e) {
            throw new RuntimeException("将gglobject写入文件失败");
        }
    }


    /**
     * 一系列向bytebuffer写入数据的方法，只支持正数
     */
    public void writeByte(byte[] target, byte num) {
        if(target.length==index){
            throw new RuntimeException("已写入到buffer结尾,序列化对象总大小大于支持的最大大小！");
        }
        target[index] = num;
        ++index;
    }

    public void writeByte(byte[] target, byte[] data) {
        int index = 0;
        for (;;) {
            if (index == data.length) {
                break;
            }
            writeByte(target, data[index]);
            ++index;
        }
    }

    public void writeByte(byte[] target, short num) {
        target[index] = (byte) (num >>> 8);
        ++index;
        target[index] = (byte) (num >>> 0);
        ++index;
    }

    public void writeByte(byte[] target, int num) {
        target[index] = (byte) (num >>> 24);
        ++index;
        target[index] = (byte) (num >>> 16);
        ++index;
        target[index] = (byte) (num >>> 8);
        ++index;
        target[index] = (byte) (num >>> 0);
        ++index;
    }

    public void writeByte(byte[] target, long num) {
        target[index] = (byte) (num >>> 56);
        ++index;
        target[index] = (byte) (num >>> 48);
        ++index;
        target[index] = (byte) (num >>> 40);
        ++index;
        target[index] = (byte) (num >>> 32);
        ++index;
        target[index] = (byte) (num >>> 24);
        ++index;
        target[index] = (byte) (num >>> 16);
        ++index;
        target[index] = (byte) (num >>> 8);
        ++index;
        target[index] = (byte) (num >>> 0);
        ++index;
    }

    public void writeByte(byte[] target, float num) {
        int floatToIntBits = Float.floatToIntBits(num);
        writeByte(target, floatToIntBits);
    }

    public void writeByte(byte[] target, double num) {
        long doubleToLongBits = Double.doubleToLongBits(num);
        System.out.println("longbit :" + doubleToLongBits);
        writeByte(target, doubleToLongBits);

    }

    public void writeByte(byte[] target, boolean flag) {
        target[index] = (byte) (flag ? 0b1 : 0b0);
        ++index;
    }

    public void writeByte(byte[] target, String str) {
        byte[] strBytes = str.getBytes();
        writeByte(target, (short) strBytes.length);
        int i = 0;
        for (;;) {
            if (i == strBytes.length) {
                break;
            }
            writeByte(target, strBytes[i]);
            ++index;
        }
    }


    /**
     * 向buffer写入gglfield
     */
    public <T> void writeByte(byte[] target, GglField<T> fd) {
        writeByte(target, fd.getContainerType());
        writeByte(target, fd.getNameLength());
        writeByte(target, fd.getName());
        writeByte(target, fd.getType());
        GglType t = GglType.getFromByte(fd.getType());
        switch (t) {
            case BYTE:
                writeByte(target, (byte) fd.getData());
                break;
            case SHORT:
                writeByte(target, (short) fd.getData());
                break;
            case INT:
                writeByte(target, (int) fd.getData());
                break;
            case LONG:
                writeByte(target, (long) fd.getData());
                break;
            case FLOAT:
                writeByte(target, (float) fd.getData());
                break;
            case DOUBLE:
                writeByte(target, (double) fd.getData());
                break;
            case BOOLEAN:
                writeByte(target, (boolean) fd.getData());
                break;
            default:
                assert(false);
                break;
        }
    }
    
    /**
     * 向buffer写入gglArray
     */
    public <T> void writeByte(byte[] target, GglArray<T> ay) {
        writeByte(target, ay.getContainerType());
        writeByte(target, ay.getNameLength());
        writeByte(target, ay.getName());
        writeByte(target, ay.getType());
        writeByte(target, ay.getCount());
        T[] data = ay.getData();
        GglType t = GglType.getFromByte(ay.getType());
        int tempIndex = 0;
        switch (t) {
            case BYTE:
            for(;;){
                if(tempIndex==data.length){
                    break;
                }
                writeByte(target, (byte)data[tempIndex]);
                ++tempIndex;
            }
                break;
            case SHORT:
                for (;;) {
                    if (tempIndex == data.length) {
                        break;
                    }
                    writeByte(target, (short) data[tempIndex]);
                    ++tempIndex;
                }
                break;
            case INT:
                for (;;) {
                    if (tempIndex == data.length) {
                        break;
                    }
                    writeByte(target, (int) data[tempIndex]);
                    ++tempIndex;
                }
                break;
            case LONG:
                for (;;) {
                    if (tempIndex == data.length) {
                        break;
                    }
                    writeByte(target, (long) data[tempIndex]);
                    ++tempIndex;
                }
                break;
            case FLOAT:
                for (;;) {
                    if (tempIndex == data.length) {
                        break;
                    }
                    writeByte(target, (float) data[tempIndex]);
                    ++tempIndex;
                }
                break;
            case DOUBLE:
                for (;;) {
                    if (tempIndex == data.length) {
                        break;
                    }
                    writeByte(target, (double) data[tempIndex]);
                    ++tempIndex;
                }
                break;
            case BOOLEAN:
                for (;;) {
                    if (tempIndex == data.length) {
                        break;
                    }
                    writeByte(target, (boolean) data[tempIndex]);
                    ++tempIndex;
                }
                break;
            default:
                assert (false);
                break;
        }
        
    }
    /**
     * 向buffer写入gglobject
     */
    public void writeByte(byte[] target,GglObject obj){
        writeByte(target, obj.getContainerType());
        writeByte(target, obj.getNameLength());
        writeByte(target, obj.getName());
        writeByte(target, obj.getFieldLength());
        for(GglField fd:obj.getFields()){
            writeByte(target, fd);
        }
        writeByte(target, obj.getArraysLength());
        for (GglArray ay : obj.getArrays()) {
            writeByte(target, ay);
        }
    }

    

}
