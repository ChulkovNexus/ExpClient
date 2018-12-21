package com.mygdx.game.ui.game

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.mygdx.game.models.MapTile

class TextureGenerator {

    companion object {
        const val TILE_SIDE_SIZE = 300
    }

    var texturesMap = HashMap<String, TextureAtlas.AtlasRegion>()
    var tiles= HashMap<String, TileForDrawing>()

    fun loadTextureAtlas() {
        var textureAtlas = TextureAtlas("map_textures2.txt")
        texturesMap["r0"] = textureAtlas.findRegion("0r")
        texturesMap["l0"] = textureAtlas.findRegion("0l")
        texturesMap["r1"] = textureAtlas.findRegion("1r")
        texturesMap["l1"] = textureAtlas.findRegion("1l")
        texturesMap["r2"] = textureAtlas.findRegion("2r")
        texturesMap["l2"] = textureAtlas.findRegion("2l")
        texturesMap["r3"] = textureAtlas.findRegion("3r")
        texturesMap["l3"] = textureAtlas.findRegion("3l")
        texturesMap["r4"] = textureAtlas.findRegion("4r")
        texturesMap["l4"] = textureAtlas.findRegion("4l")
//        texturesMap["river1"] = textureAtlas.findRegion("river1")
        texturesMap["tile_main_box"] = textureAtlas.findRegion("tile_main_box")

    }

    fun createTextureForMapTile(map: ArrayList<ArrayList<MapTile>>, x: Int, y: Int) : TextureRegion {
        val mapTile = map[y][x]
        val colorForLandType = TileForDrawing.getColorForLandType(mapTile.landType)
        val textureName = TileForDrawing.getTextureName(colorForLandType, mapTile.getTopEdgeType(), mapTile.getBotEdgeType(),
                mapTile.getRightEdgeType(), mapTile.getLeftEdgeType())
        var tile = tiles[textureName]
        if (tile == null) {
            tile = createTexture(mapTile, colorForLandType)
            tiles[textureName] = tile
        }
        return tile.getTexture()
    }

    private fun createTexture(mapTile: MapTile, colorForLandType: Color): TileForDrawing {
        val tileForDrawing = TileForDrawing()
        tileForDrawing.setTextureColor(colorForLandType)
        tileForDrawing.beginCreation()
        tileForDrawing.addMainTexture(texturesMap["tile_main_box"]!!)
        tileForDrawing.addBotTexture(texturesMap["r${mapTile.getBotEdgeType()}"]!!)
        tileForDrawing.addLeftTexture(texturesMap["r${mapTile.getLeftEdgeType()}"]!!)
        tileForDrawing.addTopTexture(texturesMap["l${mapTile.getTopEdgeType()}"]!!)
        tileForDrawing.addRightTexture(texturesMap["l${mapTile.getRightEdgeType()}"]!!)
        tileForDrawing.stopCreation()
        return tileForDrawing
    }

    fun getRiverTile(worldMap: ArrayList<ArrayList<MapTile>>, x: Int, y: Int): TextureRegion {
        val mapTile = worldMap[y][x]
        val riverName = TileForDrawing.getRiverName(mapTile.riverEdges!!)
//        val reversedArray = ArrayList(mapTile.riverEdges!!)
//        reversedArray.reverse()
//        val mirroredRiverName = TileForDrawing.getRiverName(reversedArray)
        var tile = tiles[riverName]
//        val mirroredTile = tiles[mirroredRiverName]
        if (tile == null) {
            tile = createRiverTexture(mapTile)
            tiles[riverName] = tile
        }
//        if (mirroredTile!=null) {
//            return mirroredTile.getTexture()
//        }
        return tile!!.getTexture()
    }

    private fun createRiverTexture(mapTile: MapTile): TileForDrawing {
        val tileForDrawing = TileForDrawing()
        tileForDrawing.beginCreation()
        mapTile.riverEdges?.forEach {
            when(it.getEdgeType()) {
                MapTile.RiverEdges.WEST -> tileForDrawing.addLeftTexture(texturesMap["river1"]!!)
                MapTile.RiverEdges.NORTH -> tileForDrawing.addTopTexture(texturesMap["river1"]!!)
                MapTile.RiverEdges.EAST -> tileForDrawing.addRightTexture(texturesMap["river1"]!!)
                MapTile.RiverEdges.SOUTH -> tileForDrawing.addBotTexture(texturesMap["river1"]!!)
            }
        }
        tileForDrawing.stopCreation()
        return tileForDrawing
    }

}