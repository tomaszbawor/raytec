package com.tbawor.raytec

import com.tbawor.raytec.materials.Lambertian
import com.tbawor.raytec.materials.Metal
import com.tbawor.raytec.objects.HitRecord
import com.tbawor.raytec.objects.Hittable
import com.tbawor.raytec.objects.HittableList
import com.tbawor.raytec.objects.Sphere
import java.awt.image.BufferedImage

class Renderer {

    companion object {
        private const val aspectRatio = 16.0 / 9.0
        private const val IMAGE_WIDTH = 1200
        private const val IMAGE_HEIGHT = (IMAGE_WIDTH / aspectRatio).toInt()
        private const val samplesPerPixel = 200
        private const val maxDepth = 70
    }

    val imageBuffer = BufferedImage(IMAGE_WIDTH, IMAGE_HEIGHT, BufferedImage.TYPE_INT_RGB)

    fun render() {
        val startTime = System.currentTimeMillis()
        println("Rendering started")

        // World
        val world = HittableList(
            listOf(
                Sphere(Vector3D(0.0, 0.0, -1.0), 0.5, Lambertian(Color(0.1, 0.2, 0.5))),
                Sphere(Vector3D(1.0, 0.0, -1.0), 0.5, Metal(Color(0.8, 0.6, 0.2))),
                Sphere(Vector3D(-1.0, 0.0, -1.0), 0.5, Metal(Color(1.0, 1.0, 1.0))),

                Sphere(Vector3D(0.0, -100.5, -1.0), 100.0, Lambertian(Color(0.8, 0.8, 0.0)))
            )
        )

        // Camera
        val camera = Camera()
        val allPixels = IMAGE_WIDTH * IMAGE_HEIGHT
        var currentPixelsPainted = 0
        var currentPercent = 0

        // Render
        for (y in 0 until IMAGE_HEIGHT) {
            for (x in 0 until IMAGE_WIDTH) {

                val color = sampleColorForPixel(x, y, camera, world)

                // image coordinates are flipped
                imageBuffer.setRGB(x, IMAGE_HEIGHT - y - 1, color.toAwtColor().rgb)
                currentPixelsPainted++

                val percentage = (currentPixelsPainted.toDouble() / allPixels.toDouble()) * 100
                if (percentage.toInt() != currentPercent) {
                    currentPercent = percentage.toInt()
                    println("Rendering: $currentPercent%")
                }
            }
        }

        val endTime = System.currentTimeMillis()
        // time in seconds
        val time = (endTime - startTime) / 1000.0
        println("Rendered in $time s")
    }

    private fun sampleColorForPixel(x: Int, y: Int, camera: Camera, world: Hittable): Color {

        var color = Color(0.0, 0.0, 0.0)

        for (s in 0 until samplesPerPixel) {
            // add random to sample to get anti-aliasing
            val u = (x + Math.random()) / (IMAGE_WIDTH - 1)
            val v = (y + Math.random()) / (IMAGE_HEIGHT - 1)
            val ray = camera.getRay(u, v)
            color += rayColor(ray, world, maxDepth)
        }

        // normalize color based on samples per pixel
        color /= samplesPerPixel.toDouble()
        return color
    }

    private fun rayColor(ray: Ray, world: Hittable, depth: Int): Color {
        val hitRecord: HitRecord? = world.isHit(ray, 0.001, Double.MAX_VALUE)

        // if we exceeded the ray bounce limit, no more light is gathered
        if (depth <= 0) {
            return Color(0.0, 0.0, 0.0)
        }

        return when (hitRecord) {
            null -> {
                // background color
                val unitVectorOfDirection = ray.direction.unitVector()
                val t = 0.5 * (unitVectorOfDirection.y + 1.0) // normalize t to 0.0 to 1.0
                return (Color(1.0, 1.0, 1.0) * (1.0 - t)) + (Color(0.5, 0.7, 1.0) * t) // make it blue
            }

            else -> {
                when (val scatter = hitRecord.material.scatter(ray, hitRecord)) {
                    null -> Color(0.0, 0.0, 0.0)
                    else -> return rayColor(scatter.ray, world, depth - 1) * scatter.color
                }
            }
        }
    }
}
