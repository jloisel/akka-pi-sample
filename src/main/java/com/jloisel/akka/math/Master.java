package com.jloisel.akka.math;

import java.util.concurrent.TimeUnit;

import scala.concurrent.duration.Duration;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.routing.RoundRobinRouter;

final class Master extends UntypedActor {
	private final int nrOfMessages;
	private final int nrOfElements;

	private double pi;
	private int nrOfResults;
	private final long start = System.currentTimeMillis();

	private final ActorRef listener;
	private final ActorRef workerRouter;

	public Master(final int nrOfWorkers, final int nrOfMessages, final int nrOfElements, final ActorRef listener) {
		this.nrOfMessages = nrOfMessages;
		this.nrOfElements = nrOfElements;
		this.listener = listener;

		workerRouter = this.getContext().actorOf(
				new Props(PiCalculator.class).withRouter(new RoundRobinRouter(nrOfWorkers)),
				"workerRouter");
	}

	@Override
	public void onReceive(final Object message) {
		if (message instanceof Calculate) {
			for (int start = 0 ; start < nrOfMessages ; start++) {
				workerRouter.tell(new Work(start, nrOfElements), getSelf());
			}
		} else if (message instanceof Result) {
			final Result result = (Result) message;
			pi += result.getValue();
			nrOfResults += 1;
			if (nrOfResults == nrOfMessages) {
				// Send the result to the listener
				final Duration duration = Duration.create(System.currentTimeMillis() - start, TimeUnit.MILLISECONDS);
				listener.tell(new PiApproximation(pi, duration), getSelf());
				// Stops this actor and all its supervised children
				getContext().stop(getSelf());
			}
		} else {
			unhandled(message);
		}
	}
}
