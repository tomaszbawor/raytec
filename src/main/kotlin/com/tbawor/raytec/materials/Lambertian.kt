package com.tbawor.raytec.materials

import com.tbawor.raytec.Color
import com.tbawor.raytec.Ray
import com.tbawor.raytec.Vector3D
import com.tbawor.raytec.objects.HitRecord

class Lambertian(color: Color) : Material(color) {

    override fun scatter(ray: Ray, hit: HitRecord): Scatter {
        var scatterDirection = hit.normal + Vector3D.randomUnitVector()

        if (scatterDirection.isNearZero()) {
            scatterDirection = hit.normal
        }

        val scatteredRay = Ray(hit.point, scatterDirection)
        return Scatter(scatteredRay, color)
    }
}
