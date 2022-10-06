package com.tbawor.raytec

import kotlin.math.sqrt

data class Vec3(
    val x: Double,
    val y: Double,
    val z: Double
) {
    operator fun plus(other: Vec3) = Vec3(x + other.x, y + other.y, z + other.z)
    operator fun minus(other: Vec3) = Vec3(x - other.x, y - other.y, z - other.z)
    operator fun times(other: Vec3) = Vec3(x * other.x, y * other.y, z * other.z)
    operator fun times(scalar: Double) = Vec3(x * scalar, y * scalar, z * scalar)
    operator fun div(scalar: Double) = Vec3(x / scalar, y / scalar, z / scalar)
    operator fun unaryMinus() = Vec3(-x, -y, -z)

    fun dot(other: Vec3) = x * other.x + y * other.y + z * other.z
    fun cross(other: Vec3) = Vec3(
        y * other.z - z * other.y,
        z * other.x - x * other.z,
        x * other.y - y * other.x
    )

    private fun length() = sqrt(x * x + y * y + z * z)
    fun normalize() = this / length()
}
