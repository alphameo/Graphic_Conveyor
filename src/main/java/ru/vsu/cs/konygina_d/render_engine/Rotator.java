package ru.vsu.cs.konygina_d.render_engine;

import io.github.alphameo.linear_algebra.mat.Mat4;
import io.github.alphameo.linear_algebra.mat.Mat4Math;
import io.github.alphameo.linear_algebra.mat.Matrix4;
import io.github.alphameo.linear_algebra.vec.Vec3;
import io.github.alphameo.linear_algebra.vec.Vec3Math;
import io.github.alphameo.linear_algebra.vec.Vector3;
import io.github.alphameo.linear_algebra.vec.Vector4;
import java.util.Objects;

public class Rotator implements AffineTransformation {
    private final float angle;
    private final Axis axis;

    public enum Axis {
        X, Y, Z;
    }

    public Rotator(int dangle, Rotator.Axis axis) {
        this.angle = (float) Math.toRadians(dangle);
        this.axis = axis;
    }

    public Rotator(float rangle, Rotator.Axis axis) {
        this.angle = rangle;
        this.axis = axis;
    }

    @Override
    public Matrix4 getMatrix() {
        float cosA = (float) Math.cos(angle);
        float sinA = (float) Math.sin(angle);

        switch (axis) {
            case X -> {
                return new Mat4(
                        1, 0, 0, 0,
                        0, cosA, -sinA, 0,
                        0, sinA, cosA, 0,
                        0, 0, 0, 1);
            }
            case Y -> {
                return new Mat4(
                        cosA, 0, sinA, 0,
                        0, 1, 0, 0,
                        -sinA, 0, cosA, 0,
                        0, 0, 0, 1);
            }
            case Z -> {
                return new Mat4(
                        cosA, -sinA, 0, 0,
                        sinA, cosA, 0, 0,
                        0, 0, 1, 0,
                        0, 0, 0, 1);
            }
            default -> {
                return Mat4Math.unitMat();
            }
        }
    }

    @Override
    public Vector3 transform(Vector3 v) {
        Vector4 resVertex = Mat4Math.prod(getMatrix(), Vec3Math.toVec4(v));
        return new Vec3(resVertex.x(), resVertex.y(), resVertex.z());
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rotator rotator = (Rotator) o;
        return Float.compare(angle, rotator.angle) == 0 && axis == rotator.axis;
    }

    @Override
    public int hashCode() {
        return Objects.hash(angle, axis);
    }
}

