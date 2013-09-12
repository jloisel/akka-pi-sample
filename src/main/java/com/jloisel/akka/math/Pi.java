package com.jloisel.akka.math;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.actor.UntypedActorFactory;

public class Pi {
	public static void main(final String[] args) {
		final Pi pi = new Pi();
		pi.calculate(4, 100000, 10000);
	}

	// actors and messages ...

	public void calculate(final int nrOfWorkers, final int nrOfElements, final int nrOfMessages) {
		// Create an Akka system
		final ActorSystem system = ActorSystem.create("PiSystem");

		// create the result listener, which will print the result and shutdown the system
		final ActorRef printer = system.actorOf(new Props(PiApproximationPrinter.class), "listener");

		// create the master
		final ActorRef master = system.actorOf(new Props(new UntypedActorFactory() {
			@Override
			public UntypedActor create() {
				return new Master(nrOfWorkers, nrOfMessages, nrOfElements, printer);
			}
		}), "master");

		// start the calculation
		master.tell(Calculate.INSTANCE, master);

	}
}
