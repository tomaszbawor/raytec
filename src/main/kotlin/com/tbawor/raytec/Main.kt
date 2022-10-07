package com.tbawor.raytec

import java.io.FileOutputStream
import kotlin.math.sqrt

fun main(args: Array<String>) {
    // Image
    val aspectRatio = 16.0 / 9.0
    val imageWidth = 2000
    val imageHeight = (imageWidth / aspectRatio).toInt()

    // Camera
    val viewportHeight = 2.0
    val viewportWidth = aspectRatio * viewportHeight
    val focalLength = 1.0

    val origin = Point3D(0.0, 0.0, 0.0)
    val horizontal = Vector3D(viewportWidth, 0.0, 0.0)
    val vertical = Vector3D(0.0, viewportHeight, 0.0)
    val lowerLeftCorner = origin - horizontal / 2.0 - vertical / 2.0 - Vector3D(0.0, 0.0, focalLength)

    // Render
    val bbs = BitmapStorage(imageWidth, imageHeight)

    for (y in 0 until imageHeight) {
        for (x in 0 until imageWidth) {
            val u = x.toDouble() / (imageWidth - 1)
            val v = y.toDouble() / (imageHeight - 1)
            val r = Ray(origin, lowerLeftCorner + horizontal * u + vertical * v - origin)
            val color = rayColor(r)
            bbs.setPixel(x, y, color.toAwtColor())
        }
    }

    // now write it to a PPM file
    val fos = FileOutputStream("output.ppm")
    val buffer = ByteArray(imageWidth * 3) // write one line at a time
    fos.use {
        val header = "P6\n$imageWidth $imageHeight\n255\n".toByteArray()
        with(it) {
            write(header)
            for (y in 0 until imageHeight) {
                for (x in 0 until imageWidth) {
                    val c = bbs.getPixel(x, y)
                    buffer[x * 3] = c.red.toByte()
                    buffer[x * 3 + 1] = c.green.toByte()
                    buffer[x * 3 + 2] = c.blue.toByte()
                }
                write(buffer)
            }
        }
    }
}

fun rayColor(r: Ray): Color {
    val pointOfHitAlongRay = rayParameterForSphereHit(Point3D(0.0, 0.0, -1.0), 0.5, r)
    // only if we hit the sphere in front of camera
    if (pointOfHitAlongRay > 0.0) { // second hit will be on the back of sphere
        val normalOfHitRay = (r.pointAtParameter(pointOfHitAlongRay) - Point3D(0.0, 0.0, -1.0)).unitVector()
        return Color(normalOfHitRay.x + 1.0, normalOfHitRay.y + 1.0, normalOfHitRay.z + 1.0) * 0.5
    }

    val unitDirection = r.direction.unitVector()
    val t = 0.5 * (unitDirection.y + 1.0)
    return Color(1.0, 1.0, 1.0) * (1.0 - t) + Color(0.5, 0.7, 1.0) * t
}

fun rayParameterForSphereHit(center: Point3D, radius: Double, r: Ray): Double {
    val oc = r.origin - center
    val a = r.direction.lengthSquared()
    val halfB = oc.dot(r.direction)
    val c = oc.lengthSquared() - radius * radius
    val discriminant = halfB * halfB - a * c

    return if (discriminant < 0) {
        -1.0
    } else {
        (-halfB - sqrt(discriminant)) / a
    }
}
