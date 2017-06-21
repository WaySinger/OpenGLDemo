package com.lenovo.way.opengldemo.tutorialone;

import android.opengl.GLSurfaceView;
import android.opengl.GLU;

import com.lenovo.way.opengldemo.mesh.Cube;
import com.lenovo.way.opengldemo.mesh.CubeTwo;
import com.lenovo.way.opengldemo.mesh.Group;
import com.lenovo.way.opengldemo.mesh.Mesh;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import static android.R.attr.angle;

/**
 * @author way
 * @data 2017/5/9
 * @description GLSurfaceView.Renderer
 */

public class OpenGLRenderer implements GLSurfaceView.Renderer {

    // Draw square.
    // Square square = new Square();

    // Draw smooth colored square.
    // SmoothColoredSquare square = new SmoothColoredSquare();


    private Mesh root;
    private float rotate = -30f;
//    CubeTwo cubeTwo = new CubeTwo();
//    NewCube newCube = new NewCube(1,1,1);

    // Draw cube.
    public OpenGLRenderer() {

        Group group = new Group();

        Cube cube = new Cube(1, 1, 1);
        cube.x = 3;
        cube.y = 0;
        cube.z = 0;
        cube.rx = -5;
        cube.ry = 0;
//        cube.rz = 0;

        Cube cube_1 = new Cube(1, 1, 1);
        cube_1.x = -3;
////        cube_1.y = -0.5f;
////        cube_1.z = -1;
//        cube_1.rx = 5;
//        cube_1.ry = 0;
//        cube_1.rz = 0;

        group.add(cube);
        group.add(cube_1);
        root = group;

//        newCube.rx = 15f ;
//        newCube.ry = rotate ;
//        newCube.tz = -7 ;

    }


    /*
     * GLSurfaceView.Renderer定义了一个统一图形绘制的接口，它定义了如下三个接口函数
     */

    /**
     * Called when the surface is created or recreated.
     * 在这个方法中主要用来设置一些绘制时不常变化的参数，比如：背景色，是否打开 z-buffer等
     *
     * @param gl
     * @param config
     */
    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {

        // Set the background color to black ( RGBA ).
        gl.glClearColor(0.0f, 0.0f, 0.0f, 0.5f);
        // gl.glClearColor(0.375f, 0.75f, 0.75f, 0.5f);  // My Favorite Color.

        // Enable Smooth Shading, default not really needed.
        // 启用阴影平滑
        gl.glShadeModel(GL10.GL_SMOOTH);

        // Depth buffer setup.
        // 清楚深度缓存
        gl.glClearDepthf(1.0f);

        // Enables depth testing.
        // 启用深度测试
        gl.glEnable(GL10.GL_DEPTH_TEST);

        // The type of depth testing to do.
        // 所做深度测试的类型
        gl.glDepthFunc(GL10.GL_LEQUAL);

        // Really nice perspective calculations.
        // 告诉系统对透视进行修正
        gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT,
                GL10.GL_NICEST);

    }

    /**
     * Called when the surface changed size.
     * 如果设备支持屏幕横向和纵向切换，这个方法将发生在横向<->纵向互换时。此时可以重新设置绘制的纵横比率
     *
     * @param gl
     * @param width
     * @param height
     */
    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {

        // Sets the current view port to the new size.
        gl.glViewport(0, 0, width, height);
        // Select the projection matrix
        gl.glMatrixMode(GL10.GL_PROJECTION);
        // Reset the projection matrix
        gl.glLoadIdentity();// OpenGL docs.
        // Calculate the aspect ratio of the window
        GLU.gluPerspective(gl, 45.0f,
                (float) width / (float) height,
                0.1f, 100.0f);
        // Select the modelview matrix
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        // Reset the modelview matrix
        gl.glLoadIdentity();// OpenGL docs.

    }

    /**
     * Called to draw the current frame.
     * 定义实际的绘图操作。
     *
     * @param gl
     */
    @Override
    public void onDrawFrame(GL10 gl) {

        // Clears the screen and depth buffer.
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT |
                GL10.GL_DEPTH_BUFFER_BIT);

        // Replace the current matrix with the identity matrix
        // 先重置Matrix的代码
        gl.glLoadIdentity();

        // Translates 4 units into the screen.
        // 因为OpenGL ES从当前位置开始渲染，缺省坐标为(0,0,0)，和View port 的坐标一样，相当于把画面放在眼前，对应这种情况OpenGL不会渲染离view Port很近的画面，因此我们需要将画面向后退一点距离
        gl.glTranslatef(0, 0, -8);

        // 沿着Y轴旋转
        // gl.glRotatef(rotate, 0f, 1f, 0f);

        // Draw square A.
//        gl.glPushMatrix();  // 在变换前，在栈中保存当前矩阵
//        gl.glRotatef(angle, 0, 0, 1);  // 旋转，角度为正时表示逆时针方向
//        square.draw(gl);
//        gl.glPopMatrix();  // 在变换后，从栈中恢复所存矩阵

        // 如果在draw B前没有gl.glPopMatrix();这句 则B的移动旋转是基于A的位置的

        // Draw square B.
//        gl.glPushMatrix();
//        gl.glRotatef(-angle, 0, 0, 1);
//        gl.glTranslatef(2, 0, 0);  // 先平移
//        gl.glScalef(0.5f, 0.5f, 0.5f);  // 后缩放   // 平移缩放的顺序很重要
//        square.draw(gl);
//        gl.glPopMatrix();

        // Draw square C.
//        gl.glPushMatrix();
//        gl.glRotatef(45f,0,0,1);
//        gl.glScalef(2.0f,0.5f,0);
//        gl.glTranslatef(-1.5f,-3,0);
//        square.draw(gl);
//        gl.glPopMatrix();

        // Draw cube A.
        root.draw(gl);

        // rotate--;

    }
}
