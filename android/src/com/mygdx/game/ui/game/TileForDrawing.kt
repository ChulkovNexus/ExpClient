package com.mygdx.game.ui.game

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.graphics.glutils.FrameBuffer
import com.mygdx.game.models.MapTile

class TileForDrawing {

    companion object {
        fun getTextureName(color:Color, topEdge: Int, botEdge: Int, rightEdge: Int, leftEdge: Int): String {
            return "${color.r} ${color.g} ${color.b} $topEdge $botEdge  $rightEdge $leftEdge"
        }

        fun getColorForLandType(landType: MapTile.LandType) : Color {
            return when(landType) {
                MapTile.LandType.GRASS -> Color(0.217f, 0.688f, 0.221f, 1f)
                MapTile.LandType.PLANT -> Color(0.57f, 0.65f, 0.12f, 1f)
                MapTile.LandType.TUNDRA -> Color(0.48f, 0.49f, 0.45f, 1f)
                MapTile.LandType.DESERT -> Color(0.72f, 0.72f, 0.04f, 1f)
                MapTile.LandType.COST -> Color(0.07f, 0.55f, 0.75f, 1f)
                MapTile.LandType.OCEAN -> Color(0.24f, 0.29f, 0.67f, 1f)
                MapTile.LandType.ICE_GROUND -> Color(0.63f, 0.67f, 0.96f, 1f)
            }
        }

        private var batch : SpriteBatch = SpriteBatch()
    }

    private lateinit var buffer: FrameBuffer
    private lateinit var texture: TextureRegion
    private var textureColor = Color(1f,1f,1f,1f)

    fun beginCreation() {
        batch.projectionMatrix.setToOrtho2D(0f, 0f, TextureGenerator.TILE_SIDE_SIZE.toFloat(), TextureGenerator.TILE_SIDE_SIZE.toFloat())
        batch.enableBlending()
        buffer = FrameBuffer(Pixmap.Format.RGBA4444, TextureGenerator.TILE_SIDE_SIZE, TextureGenerator.TILE_SIDE_SIZE, false)
        batch.begin()
        buffer.begin()
        batch.color = textureColor
    }

    fun stopCreation() {
        batch.color = Color(1f,1f,1f,1f)
        batch.end()
        buffer.end()
//        batch.dispose()
        texture = TextureRegion(buffer.colorBufferTexture)
    }

    fun getTexture() : TextureRegion {
        return texture
    }

    fun setTextureColor(color: Color) {
        this.textureColor = color
    }

    fun addMainTexture(atlas: TextureAtlas.AtlasRegion) {
        batch.draw(atlas, 0f, 0f)
    }

    fun addTopTexture(atlas: TextureAtlas.AtlasRegion) {
        atlas.flip(true, true)
        batch.draw(atlas, 0f, 40f, 0f, 0f, atlas.packedWidth.toFloat(), atlas.packedHeight.toFloat(), 1f, 1f, -90f)
        atlas.flip(true, true)
    }

    fun addRightTexture(atlas: TextureAtlas.AtlasRegion) {
        batch.draw(atlas, 0f, 0f)
    }

    fun addBotTexture(atlas: TextureAtlas.AtlasRegion) {
        atlas.flip(true, true)
        batch.draw(atlas, 0f, 200f, 0f, 0f, atlas.packedWidth.toFloat(), atlas.packedHeight.toFloat(), 1f, 1f, -90f)
        atlas.flip(true, true)
    }

    fun addLeftTexture(atlas: TextureAtlas.AtlasRegion) {
        batch.draw(atlas, 160f, 0f)
    }

}