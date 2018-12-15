package com.mygdx.game.models

import com.google.gson.annotations.SerializedName

class MapTile {

    var x = 0
    var y = 0
    @SerializedName("land_type")
    lateinit var landType: LandType

//                self.top_edge_type,
//                self.left_edge_type,
//                self.right_edge_type,
//                self.bot_edge_type
    @SerializedName("edge_types")
    lateinit var edgeTypes: ArrayList<Int>


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
}