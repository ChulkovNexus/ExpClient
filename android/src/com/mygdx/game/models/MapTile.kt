package com.mygdx.game.models

import com.google.gson.annotations.SerializedName

class MapTile {

    var x = 0
    var y = 0
    @SerializedName("land_type")
    lateinit var landType: LandType

    @SerializedName("edge_types")
    lateinit var edgeTypes: ArrayList<Int>

    @SerializedName("river_edges")
    lateinit var riverEdges: ArrayList<RiverEdges>


    fun getTopEdgeType(): Int {
        return edgeTypes[0]
    }
    fun getLeftEdgeType(): Int {
        return edgeTypes[1]
    }
    fun getRightEdgeType(): Int {
        return edgeTypes[2]
    }
    fun getBotEdgeType(): Int {
        return edgeTypes[3]
    }

    enum class LandType {
        @SerializedName("LandType.GRASS")
        GRASS,
        @SerializedName("LandType.TUNDRA")
        TUNDRA,
        @SerializedName("LandType.DESERT")
        DESERT,
        @SerializedName("LandType.PLANT")
        PLANT,
        @SerializedName("LandType.COST")
        COST,
        @SerializedName("LandType.OCEAN")
        OCEAN,
        @SerializedName("LandType.ICE_GROUND")
        ICE_GROUND
    }

    enum class RiverEdges {
        @SerializedName("LandType.WEST")
        WEST,
        @SerializedName("LandType.WEST_NORTH")
        WEST_NORTH,
        @SerializedName("LandType.NORTH")
        NORTH,
        @SerializedName("LandType.EAST_NORTH")
        EAST_NORTH,
        @SerializedName("LandType.EAST")
        EAST,
        @SerializedName("LandType.EAST_SOUTH")
        EAST_SOUTH,
        @SerializedName("LandType.SOUTH")
        SOUTH,
        @SerializedName("LandType.WEST_SOUTH")
        WEST_SOUTH
    }
}