package com.tbawor.raytec

import com.tbawor.raytec.objects.HitRecord
import com.tbawor.raytec.objects.Hittable
import com.tbawor.raytec.objects.HittableList
import com.tbawor.raytec.objects.Sphere
import java.io.FileOutputStream

fun main(args: Array<String>) {
    // Image
    val aspectRatio = 16.0 / 9.0
    val imageWidth = 2000
    val imageHeight = (imageWidth / aspectRatio).toInt()
    val samplesPerPixel = 100

    // World
    val world = HittableList(
        listOf(
            Sphere(Vector3D(0.0, 0.0, -1.0), 0.5),
            Sphere(Vector3D(0.0, -100.5, -1.0), 100.0)
        )
    )

    // Camera
    val camera = Camera()

    // Render
    val bbs = BitmapStorage(imageWidth, imageHeight)

    for (y in 0 until imageHeight) {
        for (x in 0 until imageWidth) {
            val u = x.toDouble() / (imageWidth - 1)
            val v = y.toDouble() / (imageHeight - 1)
            val r = camera.getRay(u, v)
            val color = rayColor(r, world)
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
            // need to write to buffer from top of the image to bottom
            for (y in imageHeight - 1 downTo 0) {
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

fun rayColor(ray: Ray, world: Hittable): Color {
    val hitRecord: HitRecord? = world.isHit(ray, 0.0, Double.MAX_VALUE)

    if (hitRecord != null) { // ray hit something
        // Order of operations are very important here
        return (
            hitRecord.normal + Color(
                1.0,
                1.0,
                1.0
            )
            ) * 0.5 // normal has range -1.0 to 1.0, so we need to scale it to 0.0 to 1.0
    }

    val unitVectorOfDirection = ray.direction.unitVector()
    val t = 0.5 * (unitVectorOfDirection.y + 1.0) // normalize t to 0.0 to 1.0
    return (Color(1.0, 1.0, 1.0) * (1.0 - t)) + (Color(0.5, 0.7, 1.0) * t) // make it blue
}
