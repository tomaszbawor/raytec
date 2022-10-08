package com.tbawor.raytec.materials

import com.tbawor.raytec.Color
import com.tbawor.raytec.Ray
import com.tbawor.raytec.Vector3D
import com.tbawor.raytec.objects.HitRecord

class Metal(color: Color) : Material(color) {

    override fun scatter(ray: Ray, hit: HitRecord): Scatter? {
        val reflected = reflect(ray.direction.unitVector(), hit.normal)
        val scattered = Ray(hit.point, reflected)
        if (scattered.direction.dot(hit.normal) > 0) {
            return Scatter(scattered, color)
        }
        return null
    }

    private fun reflect(v: Vector3D, n: Vector3D): Vector3D {
        return v - n * 2.0 * v.dot(n)
    }
}
