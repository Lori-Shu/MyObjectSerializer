package com.ggl;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

import javax.print.attribute.standard.PageRanges;

import org.junit.Test;

import com.ggl.entity.GglArray;
import com.ggl.entity.GglField;
import com.ggl.entity.GglObject;
import com.ggl.gglenum.GglType;
import com.ggl.util.GglDeSerializer;
import com.ggl.util.GglSerializer;

/**
 * Unit test for simple App.
 */
public class AppTest {
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue() {
        // boolean num = false;
        GglSerializer gglSerializer = new GglSerializer();
        GglField<Integer> fd=new GglField<Integer>("age", GglType.INT, 22);
        Integer[] rAy=new Integer[]{33,44,55};
        GglArray<Integer> ay=new GglArray<>("sizeArray", GglType.INT, 3, rAy);
        GglObject innerO = new GglObject("test");
        innerO.addField(new GglField<Integer>("tall", GglType.INT, 175));
        GglObject gglObject = new GglObject("myobj");
        gglObject.addField(fd);
        gglObject.addArray(ay);
        gglObject.addObject(innerO);    
        gglSerializer.writeByte(gglObject);
        gglSerializer.writeToFile(Path.of("C://Users/24120/Desktop/myjavaobj.gglo"));
        GglDeSerializer gglDeSerializer = new GglDeSerializer(Path.of("C://Users/24120/Desktop/myjavaobj.gglo"));
        ArrayList<GglObject> result = gglDeSerializer.getResult();
        for(GglObject o:result){
        System.out.println(o);
        }
    }

    private void printLongHex(double d) {
        long l = Double.doubleToLongBits(d);
        int index0 = 0;
        for (;;) {
            if (index0 == 8) {
                break;
            }

            System.out.printf("%x===", (l >> 56));
            ++index0;
            l = l << 8;
        }
    }

    private void printAsHex(byte[] buffer) {
        int index = 0;
        for (;;) {
            if (index == buffer.length) {
                break;
            }
            System.out.printf("===0x%x===", buffer[index]);
            if ((index + 1) % 8 == 0) {
                System.out.println();
            }
            ++index;

        }
        System.out.println();
    }
}
