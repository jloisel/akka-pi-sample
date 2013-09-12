package com.jloisel.akka.math;

import scala.concurrent.duration.Duration;

final class PiApproximation {
	private final double pi;
	private final Duration duration;

	PiApproximation(final double pi, final Duration duration) {
		this.pi = pi;
		this.duration = duration;
	}

	double getPi() {
		return pi;
	}

	Duration getDuration() {
		return duration;
	}
}