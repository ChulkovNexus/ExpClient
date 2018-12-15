package com.mygdx.game.ui.game

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputProcessor
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile
import com.mygdx.game.models.MapTile
import com.mygdx.game.utils.Logger


class MyGdxGame : ApplicationAdapter(), InputProcessor {
    private var map = TiledMap()
    private val camera = OrthographicCamera()
//    private lateinit var spriteBatch :SpriteBatch
    private lateinit var tiledMapRenderer: OrthogonalTiledMapRenderer
    private lateinit var textureGenerator: TextureGenerator
    private var newWorldMap: ArrayList<ArrayList<MapTile>>? = null

    override fun create() {
//        spriteBatch = SpriteBatch()
        tiledMapRenderer = OrthogonalTiledMapRenderer(map)
        textureGenerator = TextureGenerator()
        textureGenerator.loadTextureAtlas()

        Gdx.input.inputProcessor = this
    }

    override fun resize(width: Int, height: Int) {
        camera.setToOrtho(false, width.toFloat(), height.toFloat())
        camera.update()
    }

    override fun render() {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        camera.update()
//        camera.zoom = 0.3f
        if (newWorldMap!=null) {
            updateMap(newWorldMap!!)
            newWorldMap = null
        }
        tiledMapRenderer.setView(camera)
        tiledMapRenderer.render()

//        spriteBatch.projectionMatrix = camera.combined
//        spriteBatch.begin()
//        spriteBatch.draw(textureGenerator.test(), 100f, 100f)
//        spriteBatch.end()
    }

    override fun dispose() {
//        spriteBatch.dispose()
        tiledMapRenderer.dispose()
    }

    fun setNewWorldMap(worldMap: ArrayList<ArrayList<MapTile>>) {
        this.newWorldMap = worldMap
    }

    var time = 0L
    fun updateMap(worldMap: ArrayList<ArrayList<MapTile>>) {
        Logger.log("updateMap")
        time = System.currentTimeMillis()
        val layers = map.layers

        val layer1 = TiledMapTileLayer(worldMap.size, worldMap[0].size, 160, 160)
        for (row in worldMap) {
//            var str = ""
            for (worldCell in row) {
                val cell = TiledMapTileLayer.Cell()
                cell.tile = StaticTiledMapTile(textureGenerator.createTextureForMapTile(worldMap, worldCell.x, worldCell.y))
                layer1.setCell(worldCell.x, worldCell.y, cell)
//                str += " ${worldCell.landType}"
            }
//            Logger.log(str)
        }
        layers.add(layer1)
        Logger.log("updateMapFinished ${System.currentTimeMillis() - time}")

    }

    private var lastPointX = 0
    private var lastPointY = 0
    override fun touchDragged(screenX: Int, screenY: Int, pointer: Int): Boolean {
        val deltax = lastPointX - screenX
        val deltay = screenY - lastPointY
        lastPointX = screenX
        lastPointY = screenY
        camera.position.set(camera.position.x + deltax, camera.position.y + deltay, 0f)
        return false
    }

    override fun touchUp(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        return false
    }

    override fun mouseMoved(screenX: Int, screenY: Int): Boolean {
        return false
    }

    override fun keyTyped(character: Char): Boolean {
        return false
    }

    override fun scrolled(amount: Int): Boolean {
        return false
    }

    override fun keyUp(keycode: Int): Boolean {
        return false
    }

    override fun keyDown(keycode: Int): Boolean {
        return false
    }

    override fun touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        lastPointX = screenX
        lastPointY = screenY
        return false
    }
}
