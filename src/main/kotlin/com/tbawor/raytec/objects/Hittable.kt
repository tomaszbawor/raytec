package com.tbawor.raytec.objects

import com.tbawor.raytec.Point3D
import com.tbawor.raytec.Ray
import com.tbawor.raytec.Vector3D
import com.tbawor.raytec.materials.Material

interface Hittable {
    fun isHit(ray: Ray, tMin: Double, tMax: Double): HitRecord?
}

class HitRecord(
    val point: Point3D,
    var normal: Vector3D,
    val material: Material,
    val t: Double,
    var isFrontFace: Boolean?
) {
    fun setFaceNormal(ray: Ray, outwardNormal: Vector3D) {
        isFrontFace = ray.direction.dot(outwardNormal) < 0
        this.normal = if (this.isFrontFace!!) outwardNormal else -outwardNormal
    }
}

class HittableList(private val hittables: List<Hittable>) : Hittable {

    override fun isHit(ray: Ray, tMin: Double, tMax: Double): HitRecord? {
        var closestSoFar = tMax
        var closestHitRecord: HitRecord? = null

        for (hittable in hittables) {
            val hitRecord = hittable.isHit(ray, tMin, closestSoFar)
            if (hitRecord != null) {
                closestSoFar = hitRecord.t
                closestHitRecord = hitRecord
            }
        }
        return closestHitRecord
    }
}
