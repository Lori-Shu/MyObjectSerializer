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
    private byte[] footer;
    // 最大buffer设置为1M
    private byte[] target;

    public GglSerializer() {
        index = 0;
        validateHeader = "gglobj".getBytes();
        version = new byte[] { (byte) 0b1, (byte) 0b1 };
        footer="gglobj".getBytes();
        target=new byte[1024*1024];
    }

    public void resetCurrentIndex() {
        index = 0;
    }

    public void setCurrentIndex(int i) {
        index = i;
    }

    public void writeToFile(Path path){
        assert(path.isAbsolute()); 
        try(FileOutputStream fos = new FileOutputStream(path.toFile())) {
            fos.write(ContainerType.HEADER.getByteNum());
            fos.write(validateHeader);
            fos.write(version);
            fos.write(target,0,index);
            System.out.println(index);
            fos.write(ContainerType.FOOTER.getByteNum());
            fos.write(footer);
        } catch (Exception e) {
            throw new RuntimeException("将gglobject写入文件失败");
        }
    }


    /**
     * 一系列向bytebuffer写入数据的方法，只支持正数
     */
    public void writeByte(byte num) {
        if(target.length==index){
            throw new RuntimeException("已写入到buffer结尾,序列化对象总大小大于支持的最大大小！");
        }
        target[index] = num;
        ++index;
    }

    public void writeByte(byte[] data) {
        int index = 0;
        for (;;) {
            if (index == data.length) {
                break;
            }
            writeByte(data[index]);
            ++index;
        }
    }

    public void writeByte(short num) {
        target[index] = (byte) (num >>> 8);
        ++index;
        target[index] = (byte) (num >>> 0);
        ++index;
    }

    public void writeByte(int num) {
        target[index] = (byte) (num >>> 24);
        ++index;
        target[index] = (byte) (num >>> 16);
        ++index;
        target[index] = (byte) (num >>> 8);
        ++index;
        target[index] = (byte) (num >>> 0);
        ++index;
    }

    public void writeByte(long num) {
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

    public void writeByte(float num) {
        int floatToIntBits = Float.floatToIntBits(num);
        writeByte(floatToIntBits);
    }

    public void writeByte(double num) {
        long doubleToLongBits = Double.doubleToLongBits(num);
        System.out.println("longbit :" + doubleToLongBits);
        writeByte(doubleToLongBits);

    }

    public void writeByte(boolean flag) {
        target[index] = (byte) (flag ? 0b1 : 0b0);
        ++index;
    }

    public void writeByte(String str) {
        byte[] strBytes = str.getBytes();
        writeByte((short) strBytes.length);
        int i = 0;
        for (;;) {
            if (i == strBytes.length) {
                break;
            }
            writeByte(strBytes[i]);
            ++index;
        }
    }


    /**
     * 向buffer写入gglfield
     */
    public <T> void writeByte(GglField<T> fd) {
        writeByte(ContainerType.FIELD.getByteNum());
        writeByte(fd.getNameLength());
        writeByte(fd.getName());
        writeByte(fd.getType());
        GglType t = GglType.getFromByte(fd.getType());
        switch (t) {
            case BYTE:
                writeByte((byte) fd.getData());
                break;
            case SHORT:
                writeByte((short) fd.getData());
                break;
            case INT:
                writeByte( (int) fd.getData());
                break;
            case LONG:
                writeByte((long) fd.getData());
                break;
            case FLOAT:
                writeByte((float) fd.getData());
                break;
            case DOUBLE:
                writeByte((double) fd.getData());
                break;
            case BOOLEAN:
                writeByte((boolean) fd.getData());
                break;
            default:
                assert(false);
                break;
        }
    }
    
    /**
     * 向buffer写入gglArray
     */
    public <T> void writeByte(GglArray<T> ay) {
        writeByte(ContainerType.ARRAY.getByteNum());
        writeByte(ay.getNameLength());
        writeByte(ay.getName());
        writeByte(ay.getType());
        writeByte(ay.getCount());
        T[] data = ay.getData();
        GglType t = GglType.getFromByte(ay.getType());
        int tempIndex = 0;
        switch (t) {
            case BYTE:
            for(;;){
                if(tempIndex==data.length){
                    break;
                }
                writeByte((byte)data[tempIndex]);
                ++tempIndex;
            }
                break;
            case SHORT:
                for (;;) {
                    if (tempIndex == data.length) {
                        break;
                    }
                    writeByte( (short) data[tempIndex]);
                    ++tempIndex;
                }
                break;
            case INT:
                for (;;) {
                    if (tempIndex == data.length) {
                        break;
                    }
                    writeByte((int) data[tempIndex]);
                    ++tempIndex;
                }
                break;
            case LONG:
                for (;;) {
                    if (tempIndex == data.length) {
                        break;
                    }
                    writeByte((long) data[tempIndex]);
                    ++tempIndex;
                }
                break;
            case FLOAT:
                for (;;) {
                    if (tempIndex == data.length) {
                        break;
                    }
                    writeByte((float) data[tempIndex]);
                    ++tempIndex;
                }
                break;
            case DOUBLE:
                for (;;) {
                    if (tempIndex == data.length) {
                        break;
                    }
                    writeByte((double) data[tempIndex]);
                    ++tempIndex;
                }
                break;
            case BOOLEAN:
                for (;;) {
                    if (tempIndex == data.length) {
                        break;
                    }
                    writeByte((boolean) data[tempIndex]);
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
    public void writeByte(GglObject obj){
        writeByte(ContainerType.OBJECT.getByteNum());
        writeByte(obj.getNameLength());
        writeByte(obj.getName());
        writeByte(obj.getFieldLength());
        for(GglField fd:obj.getFields()){
            writeByte(fd);
        }
        writeByte(obj.getArraysLength());
        for (GglArray ay : obj.getArrays()) {
            writeByte(ay);
        }
        writeByte(obj.getObjectsLength());
        for (GglObject o : obj.getObjects()) {
            writeByte(o);
        }
    }

    

}
