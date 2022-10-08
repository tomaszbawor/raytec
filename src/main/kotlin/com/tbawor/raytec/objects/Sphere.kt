package com.tbawor.raytec.objects

import com.tbawor.raytec.Point3D
import com.tbawor.raytec.Ray
import com.tbawor.raytec.materials.Material
import kotlin.math.sqrt

class Sphere(
    private val center: Point3D,
    private val radius: Double,
    private val material: Material
) : Hittable {

    override fun isHit(ray: Ray, tMin: Double, tMax: Double): HitRecord? {
        val originToCenterVector = ray.origin - center
        val a = ray.direction.lengthSquared()
        val halfB = originToCenterVector.dot(ray.direction)
        val c = originToCenterVector.lengthSquared() - radius * radius

        val discriminant = halfB * halfB - a * c
        val squaredDiscriminant = sqrt(discriminant)
        if (discriminant < 0) {
            return null
        }

        // Find the nearest root that lies in the acceptable range.
        var root = (-halfB - squaredDiscriminant) / a
        if (root < tMin || tMax < root) {
            root = (-halfB + squaredDiscriminant) / a
            if (root < tMin || tMax < root) {
                return null
            }
        }
        val hr = HitRecord(
            t = root,
            point = ray.pointAtParameter(root),
            normal = (ray.pointAtParameter(root) - center) / radius,
            material = material,
            isFrontFace = null
        )
        // Todo: explain this
        val outwardNormal = (hr.point - center) / radius
        hr.setFaceNormal(ray, outwardNormal)
        return hr
    }
}
