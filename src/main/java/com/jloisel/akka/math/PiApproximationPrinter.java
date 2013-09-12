package com.jloisel.akka.math;

import akka.actor.UntypedActor;

final class PiApproximationPrinter extends UntypedActor {
	@Override
	public void onReceive(final Object message) {
		if (message instanceof PiApproximation) {
			final PiApproximation approximation = (PiApproximation) message;
			System.out.println(String.format("\n\tPi approximation: \t\t%s\n\tCalculation time: \t%s",
					approximation.getPi(), approximation.getDuration()));
			getContext().system().shutdown();
		} else {
			unhandled(message);
		}
	}
}