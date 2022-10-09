package com.tbawor.raytec

import com.tbawor.raytec.materials.Lambertian
import com.tbawor.raytec.materials.Metal
import com.tbawor.raytec.objects.HitRecord
import com.tbawor.raytec.objects.Hittable
import com.tbawor.raytec.objects.HittableList
import com.tbawor.raytec.objects.Sphere
import java.io.FileOutputStream

fun main(args: Array<String>) {
    val startTime = System.currentTimeMillis()
    // Image
    val aspectRatio = 16.0 / 9.0
    val imageWidth = 500
    val imageHeight = (imageWidth / aspectRatio).toInt()
    val samplesPerPixel = 200
    val maxDepth = 75

    // World
    val world = HittableList(
        listOf(
            Sphere(Vector3D(0.0, 0.0, -1.0), 0.5, Lambertian(Color(0.1, 0.2, 0.5))),
            Sphere(Vector3D(1.0, 0.0, -1.0), 0.5, Metal(Color(0.8, 0.6, 0.2))),
            Sphere(Vector3D(0.0, -100.5, -1.0), 100.0, Lambertian(Color(0.8, 0.8, 0.0)))
        )
    )

    // Camera
    val camera = Camera()

    // Render
    val bbs = BitmapStorage(imageWidth, imageHeight)

    for (y in 0 until imageHeight) {
        for (x in 0 until imageWidth) {

            var color = Color(0.0, 0.0, 0.0)
            for (s in 0 until samplesPerPixel) {
                // add random to sample to get anti-aliasing
                val u = (x + Math.random()) / (imageWidth - 1)
                val v = (y + Math.random()) / (imageHeight - 1)
                val ray = camera.getRay(u, v)
                color += rayColor(ray, world, maxDepth)
            }
            // normalize color based on samples per pixel
            color /= samplesPerPixel.toDouble()
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

    val endTime = System.currentTimeMillis()
    // time in seconds
    val time = (endTime - startTime) / 1000.0
    println("Rendered in $time ms")
}

fun rayColor(ray: Ray, world: Hittable, depth: Int): Color {
    val hitRecord: HitRecord? = world.isHit(ray, 0.0001, Double.MAX_VALUE)

    // if we exceeded the ray bounce limit, no more light is gathered
    if (depth <= 0) {
        return Color(0.0, 0.0, 0.0)
    }

    if (hitRecord != null) { // ray hit something
        val scatter = hitRecord.material.scatter(ray, hitRecord)
        if (scatter != null) {
            return rayColor(scatter.ray, world, depth - 1) * scatter.color
        }
        return Color(0.0, 0.0, 0.0)
    }

    // background color
    val unitVectorOfDirection = ray.direction.unitVector()
    val t = 0.5 * (unitVectorOfDirection.y + 1.0) // normalize t to 0.0 to 1.0
    return (Color(1.0, 1.0, 1.0) * (1.0 - t)) + (Color(0.5, 0.7, 1.0) * t) // make it blue
}
