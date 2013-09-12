package com.jloisel.akka.math;

import akka.actor.UntypedActor;

final class PiCalculator extends UntypedActor {

	@Override
	public void onReceive(final Object message) throws Exception {
		if(message instanceof Work) {
			final Work work = (Work)message;
			final double result = calculatePiFor(work.getStart(), work.getNbOfElements());
			getSender().tell(new Result(result), getSelf());
		} else {
			unhandled(message);
		}
	}

	private static double calculatePiFor(final int start, final int nrOfElements) {
		double acc = 0.0;
		for (int i = start * nrOfElements; i <= ((start + 1) * nrOfElements - 1); i++) {
			acc += 4.0 * (1 - (i % 2) * 2) / (2 * i + 1);
		}
		return acc;
	}
}
