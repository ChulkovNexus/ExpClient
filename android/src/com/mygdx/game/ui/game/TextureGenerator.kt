package com.mygdx.game.ui.game

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.mygdx.game.models.MapTile
import com.mygdx.game.utils.Logger

class TextureGenerator {

    companion object {
        const val TILE_SIDE_SIZE = 200
    }

    var texturesMap = HashMap<String, TextureAtlas.AtlasRegion>()
    var tiles= HashMap<String, TileForDrawing>()
    lateinit var texture : TextureRegion

    fun loadTextureAtlas() {
        var textureAtlas = TextureAtlas("map_textures.txt")
        texturesMap["r1"] = textureAtlas.findRegion("r1")
        texturesMap["l1"] = textureAtlas.findRegion("l1")
        texturesMap["tile_main_box"] = textureAtlas.findRegion("tile_main_box")

//        val tileForDrawing = TileForDrawing()
//        tileForDrawing.beginCreation()
//        tileForDrawing.addMainTexture(texturesMap["tile_main_box"]!!)
//        tileForDrawing.addTopTexture(texturesMap["r1"]!!)
//        tileForDrawing.addRightTexture(texturesMap["r1"]!!)
//        tileForDrawing.addBotTexture(texturesMap["l1"]!!)
//        tileForDrawing.addLeftTexture(texturesMap["l1"]!!)
//        tileForDrawing.stopCreation()
//        texture = tileForDrawing.getTexture()
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
        tileForDrawing.addTopTexture(texturesMap["r1"]!!)
        tileForDrawing.addRightTexture(texturesMap["r1"]!!)
        tileForDrawing.addBotTexture(texturesMap["l1"]!!)
        tileForDrawing.addLeftTexture(texturesMap["l1"]!!)
        tileForDrawing.stopCreation()
        return tileForDrawing
    }

}