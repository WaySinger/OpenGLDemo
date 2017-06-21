package com.lenovo.way.opengldemo.tutorialone;

import com.lenovo.way.opengldemo.utils.BufferUtil;

import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

/**
 * @author way
 * @data 2017/5/11
 * @description .
 */

public class NewCube {

    // vertex buffer.
    private FloatBuffer verticesBuffer = null;

    // vertices.length
    private int vertices_length_count;

    // The number of indices.
    private int numOfIndices = -1;

    // color buffer.
    private FloatBuffer colorBuffer = null;

    // vertices x y z
    public float x = 0, y = 0, z = 0;

    // Translate params.
    public float tx = 0, ty = 0, tz = 0;

    // Rotate params.
    public float rx = 0, ry = 0, rz = 0;

    public NewCube(float width, float height, float depth) {

        x = width;
        y = height;
        z = depth;

        // vertices
        float[] vertices = {
                0, y, z,
                0, 0, z,
                x, 0, z,
                0, y, z,
                x, 0, z,
                x, y, z,  // front

                x, y, z,
                x, 0, z,
                x, 0, 0,
                x, y, z,
                x, 0, 0,
                x, y, 0,  // right

                0, y, 0,
                0, 0, 0,
                0, y, z,
                0, 0, 0,
                0, 0, z,
                0, y, z,  // left

                x, y, 0,
                x, 0, 0,
                0, 0, 0,
                x, y, 0,
                0, 0, 0,
                0, y, 0,  // behind

                0, y, 0,
                0, y, z,
                x, y, 0,
                0, y, z,
                x, y, z,
                x, y, 0,  // up

                0, 0, z,
                0, 0, 0,
                x, 0, 0,
                0, 0, z,
                x, 0, 0,
                x, 0, z,  // down

        };


        // colors
        float[] colors = {
                1f, 0f, 0f, 1f,
                1f, 0f, 0f, 1f,
                1f, 0f, 0f, 1f,
                1f, 0f, 0f, 1f,
                1f, 0f, 0f, 1f,
                1f, 0f, 0f, 1f,

                1f, 0f, 1f, 1f,
                1f, 0f, 1f, 1f,
                1f, 0f, 1f, 1f,
                1f, 0f, 1f, 1f,
                1f, 0f, 1f, 1f,
                1f, 0f, 1f, 1f,

                0f, 1f, 0f, 1f,
                0f, 1f, 0f, 1f,
                0f, 1f, 0f, 1f,
                0f, 1f, 0f, 1f,
                0f, 1f, 0f, 1f,
                0f, 1f, 0f, 1f,

                0f, 0f, 1f, 1f,
                0f, 0f, 1f, 1f,
                0f, 0f, 1f, 1f,
                0f, 0f, 1f, 1f,
                0f, 0f, 1f, 1f,
                0f, 0f, 1f, 1f,

                0.5f, 0f, 1f, 1f,
                0.5f, 0f, 1f, 1f,
                0.5f, 0f, 1f, 1f,
                0.5f, 0f, 1f, 1f,
                0.5f, 0f, 1f, 1f,
                0.5f, 0f, 1f, 1f,

                1f, 0f, 0.5f, 1f,
                1f, 0f, 0.5f, 1f,
                1f, 0f, 0.5f, 1f,
                1f, 0f, 0.5f, 1f,
                1f, 0f, 0.5f, 1f,
                1f, 0f, 0.5f, 1f,

        };

        verticesBuffer = BufferUtil.bufferUtil(vertices);
        colorBuffer = BufferUtil.bufferUtil(colors);

        vertices_length_count = vertices.length;
    }

    public void draw(GL10 gl){
        // Counter-clockwise winding.
        gl.glFrontFace(GL10.GL_CCW);
        // Enable face culling.
        gl.glEnable(GL10.GL_CULL_FACE);
        // What faces to remove with the face culling.
        gl.glCullFace(GL10.GL_BACK);

        //开启顶点和纹理缓冲
        // Enabled the vertices buffer for writing and to be used during rendering.
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        // Specifies the location and data format of an array of vertex coordinates to use when rendering.
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, verticesBuffer);
        // Enable the color array buffer to be used during rendering.
        gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
        gl.glColorPointer(4, GL10.GL_FLOAT, 0, colorBuffer);

        gl.glLoadIdentity();
        gl.glTranslatef(x, y, z);
        gl.glRotatef(rx, 1, 0, 0);
        gl.glRotatef(ry, 0, 1, 0);
        gl.glRotatef(rz, 0, 0, 1);

        gl.glDrawArrays(GL10.GL_TRIANGLES, 0, vertices_length_count);
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glDisableClientState(GL10.GL_COLOR_ARRAY);
        gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
        gl.glDisable(GL10.GL_CULL_FACE);
    }

}
