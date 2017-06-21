package com.lenovo.way.opengldemo.mesh;

import com.lenovo.way.opengldemo.mesh.Mesh;

/**
 * @author way
 * @data 2017/5/10
 * @description Plane.
 * Face (三角形) .
 */

public class Plane extends Mesh {

    /*
     * Segments为形体宽度，高度，深度可以分成的份数。
     * Segments在构造一个非均匀分布的Surface特别有用，
     * 比如在一个游戏场景中，构造地貌，使的Z轴的值随机分布在-0.1到0.1之间，然后给它渲染好看的材质就可以造成地图凹凸不平的效果。
     */

    public Plane() {
        this(1, 1, 1, 1);
    }

    public Plane(float width, float height) {
        this(width, height, 1, 1);
    }

    public Plane(float width, float height, int widthSegments, int heightSegments) {

        float[] vertices = new float[(widthSegments + 1) * (heightSegments + 1) * 3];

        short[] indices = new short[(widthSegments + 1) * (heightSegments + 1) * 6];

        float xOffset = width / -2;
        float yOffset = height / -2;

        float xWidth = width / (widthSegments);
        float yHeight = height / (heightSegments);

        int currentVertex = 0;
        int currentIndex = 0;
        short w = (short) (widthSegments + 1);

        for (int y = 0; y < heightSegments + 1; y++) {
            for (int x = 0; x < widthSegments + 1; x++) {
                vertices[currentVertex] = xOffset + x * xWidth;
                vertices[currentVertex + 1] = yOffset + y * yHeight;
                vertices[currentVertex + 2] = 0;
                currentVertex += 3;

                int n = y * (widthSegments + 1) + x;

                if (y < heightSegments && x < widthSegments) {
                    // Face one
                    indices[currentIndex] = (short) n;
                    indices[currentIndex + 1] = (short) (n + 1);
                    indices[currentIndex + 2] = (short) (n + w);
                    // Face two
                    indices[currentIndex + 3] = (short) (n + 1);
                    indices[currentIndex + 4] = (short) (n + 1 + w);
                    indices[currentIndex + 5] = (short) (n + 1 + w - 1);

                    currentIndex += 6;
                }
            }
        }

        // 顶点顺序
        setIndices(indices);
        // 顶点坐标
        setVertices(vertices);
    }

}
