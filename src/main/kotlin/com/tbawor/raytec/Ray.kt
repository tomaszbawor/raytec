package com.tbawor.raytec

data class Ray(
    val origin: Vector3D,
    val direction: Vector3D
) {
    fun pointAtParameter(t: Double): Vector3D {
        return origin + direction * t
    }
}
