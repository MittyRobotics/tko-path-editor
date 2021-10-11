/*
 * MIT License
 *
 * Copyright (c) 2019 Mitty Robotics (Team 1351)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.amhsrobotics.tkopatheditor.parametrics.library;

import com.amhsrobotics.tkopatheditor.parametrics.interfaces.WithCurvature;
import com.amhsrobotics.tkopatheditor.parametrics.interfaces.WithVelocity;

public class TransformWithVelocityAndCurvature extends Transform implements WithVelocity, WithCurvature {
    private double velocity;
    private double curvature;


    public TransformWithVelocityAndCurvature(Transform transform) {
        super(transform);
        this.curvature = 0;
        this.velocity = 0;
    }

    public TransformWithVelocityAndCurvature(Transform transform, double velocity, double curvature) {
        super(transform);
        this.curvature = curvature;
        this.velocity = velocity;
    }

    @Override
    public double getVelocity() {
        return velocity;
    }

    @Override
    public double getCurvature() {
        return curvature;
    }
}
