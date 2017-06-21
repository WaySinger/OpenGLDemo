package com.lenovo.way.opengldemo.mesh;

import com.lenovo.way.opengldemo.mesh.Mesh;

/**
 * @author way
 * @data 2017/5/10
 * @description .
 */

public class Cube extends Mesh {

    public Cube(float width, float height, float depth) {
//        width /= 2;
//        height /= 2;
//        depth /= 2;

        float vertices[] = {
//                -width, -height, -depth, // 0
//                width, -height, -depth, // 1
//                width, height, -depth, // 2
//                -width, height, -depth, // 3
//                -width, -height, depth, // 4
//                width, -height, depth, // 5
//                width, height, depth, // 6
//                -width, height, depth, // 7
                0, 0, 0, // 0
                width, 0, 0, // 1
                width, height, 0, // 2
                0, height, 0, // 3
                0, 0, depth, // 4
                width, 0, depth, // 5
                width, height, depth, // 6
                0, height, depth, // 7
        };

        short indices[] = {
                0, 4, 5,
                0, 5, 1,
                1, 5, 6,
                1, 6, 2,
                2, 6, 7,
                2, 7, 3,
                3, 7, 4,
                3, 4, 0,
                4, 7, 6,
                4, 6, 5,
                3, 0, 1,
                3, 1, 2,
//                0,1,2,
//                0,2,3,
//
//                0,4,5,
//                0,5,1,
//
//                0,4,7,
//                0,7,3,
//
//                4,5,6,
//                4,6,7,
//
//                7,3,2,
//                7,6,2,
//
//                1,6,5,
//                1,6,2,
        };

        float[] colors = {

//                0f, 0f, 0f, 1f,  // 0
//                0.5f, 0.5f, 0.5f, 1f,  // 1
//                0.5f, 0.5f, 0.5f, 1f,  // 2
//                0f, 0f, 0f, 1f,  // 3
//                0.5f, 0.5f, 0.5f, 1f,  // 4
//                0f, 0f, 0f, 1f,  // 5
//                0f, 0f, 0f, 1f,  // 6
//                0.5f, 0.5f, 0.5f, 1f,  // 7
                0.2f, 0.5f, 1f, 1f,
                0f, 0f, 1f, 1f,
                0.7f, 1f, 1f, 1f,
                0.2f, 1f, 1f, 1f,
                0f, 0f, 1f, 1f,
                0f, 0f, 0.3f, 1f,
                0.2f, 0.5f, 1f, 1f,
                0.2f, 0.5f, 1f, 1f,
        };

        setIndices(indices);
        setVertices(vertices);
        setColors(colors);
    }

}
