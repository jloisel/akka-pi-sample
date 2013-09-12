package com.jloisel.akka.math;

final class Work {

	private final int start;
	private final int nbOfElements;

	Work(final int start, final int nbOfElements) {
		super();
		this.start = start;
		this.nbOfElements = nbOfElements;
	}

	int getStart() {
		return start;
	}

	int getNbOfElements() {
		return nbOfElements;
	}
}
