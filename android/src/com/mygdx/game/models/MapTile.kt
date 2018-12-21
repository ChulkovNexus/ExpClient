package com.mygdx.game.models

import com.google.gson.annotations.SerializedName
import java.lang.IllegalStateException

class MapTile {

    var x = 0
    var y = 0

    @SerializedName("land_type")
    lateinit var landType: LandType

    @SerializedName("edge_types")
    lateinit var edgeTypes: ArrayList<Int>

    @SerializedName("river_chanel")
    var riverEdges: ArrayList<RiverChannelPart>? = null


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

    class RiverChannelPart {
        var edge = 0
        var start = 0
        var end = 0

        fun getEdgeType(): RiverEdges {
            return when (edge) {
                0-> RiverEdges.WEST
                2-> RiverEdges.NORTH
                4-> RiverEdges.EAST
                6-> RiverEdges.SOUTH
                else -> throw IllegalStateException("wrong river edge type has come from server")
            }
        }

        fun getStartFlowType(): RiverFlowDirection{
            return getFlowDirection(start)
        }

        fun getEndFlowType(): RiverFlowDirection{
            return getFlowDirection(end)
        }

        private fun getFlowDirection(value: Int): RiverFlowDirection {
            return when (value) {
                0-> RiverFlowDirection.RIGHT
                1-> RiverFlowDirection.LEFT
                2-> RiverFlowDirection.FORWARD
                3-> RiverFlowDirection.BEGINNING
                4-> RiverFlowDirection.END
                else -> throw IllegalStateException("wrong river flow type has come from server")
            }
        }
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
        WEST,
        NORTH,
        EAST,
        SOUTH
    }

    enum class RiverFlowDirection {
        RIGHT,
        LEFT,
        FORWARD,
        BEGINNING,
        END
    }
}