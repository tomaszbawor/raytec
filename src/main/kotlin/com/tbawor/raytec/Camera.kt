package com.tbawor.raytec

class Camera(
    aspectRatio: Double = 16.0 / 9.0,
) {
    private val viewportHeight = 2.0
    private val viewportWidth = aspectRatio * viewportHeight
    private val focalLength = 1.0

    private val origin = Point3D(0.0, 0.0, 0.0)
    private val horizontal = Vector3D(viewportWidth, 0.0, 0.0)
    private val vertical = Vector3D(0.0, viewportHeight, 0.0)
    private val lowerLeftCorner = origin - horizontal / 2.0 - vertical / 2.0 - Vector3D(0.0, 0.0, focalLength)

    fun getRay(u: Double, v: Double): Ray {
        return Ray(origin, lowerLeftCorner + horizontal * u + vertical * v - origin)
    }
}
