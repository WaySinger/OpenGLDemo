package com.lenovo.way.opengldemo.utils;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;

/**
 * @author way
 * @data 2017/5/9
 * @description 工具类————Buffer的创建方式.
 *
 * OpenGL所使用的缓冲区存储结构是和我们的Java 程序中不相同的。
 * Java 是大端字节序(BigEdian)，而 OpenGL 所需要的数据是小端字节序(LittleEdian)。
 * 所以，我们在将 Java 的缓冲区转化为 OpenGL 可用的缓冲区时需要作一些工作。
 * 否则在android1.6以下会报java.lang.IllegalArgumentException: Must use a native order direct Buffer。
 */

public class BufferUtil {

    public static FloatBuffer floatBuffer;
    public static IntBuffer intBuffer;
    public static ShortBuffer shortBuffer;

    /**
     * float is 4 bytes, therefore we multiply the number if vertices with 4.
     * @param arr
     * @return  FloatBuffer
     */
    public static FloatBuffer bufferUtil(float[] arr)
    {
        // 先初始化buffer,数组的长度*4,因为一个float占4个字节
        ByteBuffer fb = ByteBuffer.allocateDirect(arr.length*4);
//        ByteBuffer fb = ByteBuffer.allocateDirect(arr.length * 6 );
        // 数组排列用nativeOrder
        fb.order(ByteOrder.nativeOrder());
        floatBuffer = fb.asFloatBuffer();
        floatBuffer.put(arr);
        floatBuffer.position(0);
        return floatBuffer;
    }


    /**
     * short is 2 bytes, therefore we multiply the number if vertices with 2.
     * @param arr
     * @return  ShortBuffer
     */
    public static ShortBuffer bufferUtil(short []arr){

        ByteBuffer qbb = ByteBuffer.allocateDirect(arr.length * 2);
        qbb.order(ByteOrder.nativeOrder());

        shortBuffer = qbb.asShortBuffer();
        shortBuffer.put(arr);
        shortBuffer.position(0);

        return shortBuffer;
    }

    public static IntBuffer bufferUtil(int []arr){

        ByteBuffer ib = ByteBuffer.allocateDirect(arr.length * 4);
//        ByteBuffer ib = ByteBuffer.allocateDirect(arr.length * 6);
        ib.order(ByteOrder.nativeOrder());

        intBuffer = ib.asIntBuffer();
        intBuffer.put(arr);
        intBuffer.position(0);

        return intBuffer;
    }
}
