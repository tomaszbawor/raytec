package com.tbawor.raytec.materials

import com.tbawor.raytec.Color
import com.tbawor.raytec.Ray
import com.tbawor.raytec.objects.HitRecord

abstract class Material(val color: Color) {
    abstract fun scatter(ray: Ray, hit: HitRecord): Scatter?
}

data class Scatter(val ray: Ray, val color: Color)
