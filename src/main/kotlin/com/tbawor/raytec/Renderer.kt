package com.tbawor.raytec

import com.tbawor.raytec.materials.Lambertian
import com.tbawor.raytec.materials.Metal
import com.tbawor.raytec.objects.HitRecord
import com.tbawor.raytec.objects.Hittable
import com.tbawor.raytec.objects.HittableList
import com.tbawor.raytec.objects.Sphere

class Renderer {

    val aspectRatio = 16.0 / 9.0
    val IMAGE_WIDTH = 1200
    val IMAGE_HEIGHT = (IMAGE_WIDTH / aspectRatio).toInt()

    val imageBuffer = BitmapStorage(IMAGE_WIDTH, IMAGE_HEIGHT)

    fun render() {
        val startTime = System.currentTimeMillis()
        // Image
        val samplesPerPixel = 10
        val maxDepth = 3

        // World
        val world = HittableList(
            listOf(
                Sphere(Vector3D(0.0, 0.0, -1.0), 0.5, Lambertian(Color(0.1, 0.2, 0.5))),
                Sphere(Vector3D(1.0, 0.0, -1.0), 0.5, Metal(Color(0.8, 0.6, 0.2))),
                Sphere(Vector3D(-1.0, 0.0, -1.0), 0.5, Metal(Color(0.2, 0.6, 0.1))),

                Sphere(Vector3D(0.0, -100.5, -1.0), 100.0, Lambertian(Color(0.8, 0.8, 0.0)))
            )
        )

        // Camera
        val camera = Camera()

        // Render
        for (y in 0 until IMAGE_HEIGHT) {
            for (x in 0 until IMAGE_WIDTH) {

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
                imageBuffer.setPixel(x, y, color.toAwtColor())
            }
        }
        imageBuffer.flip() // flip image vertically

        val endTime = System.currentTimeMillis()
        // time in seconds
        val time = (endTime - startTime) / 1000.0
        println("Rendered in $time s")
    }

    private fun rayColor(ray: Ray, world: Hittable, depth: Int): Color {
        val hitRecord: HitRecord? = world.isHit(ray, 0.001, Double.MAX_VALUE)

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
}
