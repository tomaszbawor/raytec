package com.tbawor.raytec

import kotlin.math.sqrt

data class Vector3D(
    val x: Double,
    val y: Double,
    val z: Double
) {

    companion object {
        fun random(): Vector3D {
            return Vector3D(Math.random(), Math.random(), Math.random())
        }

        fun random(min: Double, max: Double): Vector3D {
            return Vector3D(
                Math.random() * (max - min) + min,
                Math.random() * (max - min) + min,
                Math.random() * (max - min) + min
            )
        }

        fun randomInUnitSphere(): Vector3D {
            while (true) {
                val p = Vector3D.random(-1.0, 1.0)
                if (p.lengthSquared() >= 1) continue
                return p
            }
        }
    }

    operator fun plus(other: Vector3D) = Vector3D(x + other.x, y + other.y, z + other.z)
    operator fun minus(other: Vector3D) = Vector3D(x - other.x, y - other.y, z - other.z)
    operator fun times(other: Vector3D) = Vector3D(x * other.x, y * other.y, z * other.z)
    operator fun times(scalar: Double) = Vector3D(x * scalar, y * scalar, z * scalar)
    operator fun div(scalar: Double) = Vector3D(x / scalar, y / scalar, z / scalar)
    operator fun unaryMinus() = Vector3D(-x, -y, -z)

    fun dot(other: Vector3D) = x * other.x + y * other.y + z * other.z
    fun cross(other: Vector3D) = Vector3D(
        y * other.z - z * other.y,
        z * other.x - x * other.z,
        x * other.y - y * other.x
    )

    private fun length() = sqrt(x * x + y * y + z * z)
    fun unitVector() = this / length()

    fun toAwtColor() = java.awt.Color(x.toFloat(), y.toFloat(), z.toFloat())

    fun lengthSquared(): Double {
        return this.dot(this)
    }
}

typealias Color = Vector3D
typealias Point3D = Vector3D
