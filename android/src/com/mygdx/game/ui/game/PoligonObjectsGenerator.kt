package com.mygdx.game.ui.game

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.PolygonRegion
import com.badlogic.gdx.graphics.g2d.PolygonRegionLoader
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.maps.objects.PolygonMapObject
import com.badlogic.gdx.math.Polygon


class PoligonObjectsGenerator {
    lateinit var polygonMapObject: PolygonMapObject
    lateinit var polygonRegion : PolygonRegion
    lateinit var polygon: Polygon
    fun loadTextureAtlas() {
        var loader = PolygonRegionLoader()
        val resolve = loader.resolve("r1.psh")
        val pix = Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pix.setColor(Color.WHITE); // DE is red, AD is green and BE is blue.
        pix.fill()
        val textureSolid = Texture(pix)
        polygonRegion = loader.load(TextureRegion(textureSolid), resolve)
        val mainCube = floatArrayOf(// Four vertices
                40f, 40f, // Vertex 0         3--2
                160f, 40f, // Vertex 1         | /|
                160f, 160f, // Vertex 2         |/ |
                40f, 100f           // Vertex 3         0--1
        )
        val mutableList = polygonRegion.vertices.toMutableList()
        mutableList.addAll(mainCube.toList())
        val mutableTriangles = polygonRegion.triangles.toMutableList()
        var triangles = shortArrayOf(
            (mutableList.size + 0).toShort(), (mutableList.size + 1).toShort(), (mutableList.size + 2).toShort(),         // Two triangles using vertex indices.
            (mutableList.size + 0).toShort(), (mutableList.size + 2).toShort(), (mutableList.size + 3).toShort()         // Two triangles using vertex indices.
        )
        mutableTriangles.addAll(triangles.toList())
        polygonRegion = PolygonRegion(TextureRegion(textureSolid), mutableList.toFloatArray(), mutableTriangles.toShortArray())
    }


}