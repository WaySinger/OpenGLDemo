package com.lenovo.way.opengldemo.openes.util;

import android.opengl.Matrix;

/**
 * @author way
 * @data 2017/10/12
 * @description .
 */

public class MatrixState {

    private static float[] mProjMatrix = new float[16]; // 4*4矩阵 存储投影矩形
    private static float[] mVMatrix = new float[16]; // 摄像机位置朝向9参数矩阵


    // 设置摄像机
    public static void setCamera(float cx, // 摄像机位置x
                                 float cy, // 摄像机位置y
                                 float cz, // 摄像机位置z
                                 float tx, // 摄像机目标点x
                                 float ty, // 摄像机目标点y
                                 float tz, // 摄像机目标点z
                                 float upx, // 摄像机UP向量X分量
                                 float upy, // 摄像机UP向量Y分量
                                 float upz // 摄像机UP向量Z分量
     ){
        Matrix.setLookAtM(mVMatrix, 0, cx, cy, cz, tx, ty, tz, upx, upy, upz);
    }

    // 设置透视投影参数
    public static void setProjectFrustum(float left, // near面的left
                                         float right, // near面的right
                                         float bottom, // near面的bottom
                                         float top, // near面的top
                                         float near, // near面距离
                                         float far // far面距离
    ){
        Matrix.frustumM(mProjMatrix, 0, left, right, bottom, top, near, far);
    }

    // 获取具体物体的总变换矩阵
    static float[] mMVPMatrix = new float[16];

    public static float[] getFinalMatrix() {
        Matrix.multiplyMM(mMVPMatrix, 0, mProjMatrix, 0, mVMatrix, 0);
        return mMVPMatrix;
    }

}
