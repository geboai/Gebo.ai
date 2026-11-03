package ai.gebo.application.messaging.orchestration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import ai.gebo.application.messaging.IGTimedOutMessageReceiver;
import ai.gebo.application.messaging.IGTimedOutMessageReceiverFactory;
import ai.gebo.application.messaging.model.GMessageEnvelope;

/**
 * A receiver thread of a timed-out factory (the batch aggregators: tokenizer,
 * vectorizator, full-text indexer, graph extractor...) holds the messages it
 * gets in its own receiver instance and hands them on when the instance's
 * timeout expires with nothing new. A message that arrives while that timeout
 * flush is running must be flushed by the next timeout too, or it stays in the
 * instance until another message happens to reach this very thread: the
 * document it carries is then missing from its step for good, and the job never
 * finishes.
 */
class MessageReceiverRunnerTimeoutTest {

	private static final long TIMEOUT_MS = 200;

	private final List<GMessageEnvelope> held = new CopyOnWriteArrayList<>();
	private final List<GMessageEnvelope> flushed = new CopyOnWriteArrayList<>();
	/** the first timeout flush starts: the window the test aims at */
	private final CountDownLatch firstFlushStarted = new CountDownLatch(1);
	/** opened by the test, lets the first timeout flush end */
	private final CountDownLatch firstFlushMayEnd = new CountDownLatch(1);
	private MessageReceiverRunner runner;
	private Thread thread;
	/** when set, delivered to the runner from inside the first timeout flush */
	private GMessageEnvelope arrivesDuringTheFirstFlush;

	@AfterEach
	void stop() {
		firstFlushMayEnd.countDown();
		if (runner != null) {
			runner.startShutdown();
		}
	}

	private MessageReceiverRunner start() {
		IGTimedOutMessageReceiver instance = mock(IGTimedOutMessageReceiver.class);
		doAnswer(invocation -> {
			held.add(invocation.getArgument(0));
			return null;
		}).when(instance).accept(any());
		doAnswer(invocation -> {
			boolean first = firstFlushStarted.getCount() > 0;
			firstFlushStarted.countDown();
			if (first) {
				// a real flush takes time (a vectorization batch, an index commit...)
				firstFlushMayEnd.await(10, TimeUnit.SECONDS);
				if (arrivesDuringTheFirstFlush != null) {
					// ...and the thread's lock is reentrant: a message the flush itself
					// causes to be routed back here lands in the queue before the flush
					// is over, exactly like one queued by another thread on that lock
					runner.accept(arrivesDuringTheFirstFlush);
				}
			}
			List<GMessageEnvelope> batch = new ArrayList<>(held);
			held.clear();
			flushed.addAll(batch);
			return null;
		}).when(instance).onTimeoutEvent(any());
		IGTimedOutMessageReceiverFactory factory = mock(IGTimedOutMessageReceiverFactory.class);
		when(factory.getReceivingTimeout()).thenReturn(TIMEOUT_MS);
		when(factory.create()).thenReturn(instance);
		when(factory.getMessagingModuleId()).thenReturn("test-module");
		when(factory.getMessagingSystemId()).thenReturn("test-component");
		runner = new MessageReceiverRunner(factory, new NoOpMessageDeliveryObserver());
		thread = new Thread(runner, "test-receiver");
		thread.setDaemon(true);
		thread.start();
		return runner;
	}

	private static GMessageEnvelope envelope(String id) {
		GMessageEnvelope envelope = new GMessageEnvelope();
		envelope.setId(id);
		return envelope;
	}

	private static boolean within(long millis, java.util.function.BooleanSupplier condition) throws InterruptedException {
		long deadline = System.currentTimeMillis() + millis;
		while (System.currentTimeMillis() < deadline) {
			if (condition.getAsBoolean()) {
				return true;
			}
			Thread.sleep(10);
		}
		return condition.getAsBoolean();
	}

	@Test
	void aMessageArrivingDuringTheTimeoutFlushIsFlushedByTheNextTimeout() throws Exception {
		arrivesDuringTheFirstFlush = envelope("second");
		MessageReceiverRunner runner = start();

		GMessageEnvelope first = envelope("first");
		runner.accept(first);
		assertTrue(firstFlushStarted.await(5, TimeUnit.SECONDS), "the first message is flushed on timeout");

		// the second message arrives while the flush of the first is under way: it
		// is handed to the receiver instance right after the flush
		firstFlushMayEnd.countDown();
		assertTrue(within(2000, () -> held.size() == 1 && "second".equals(held.get(0).getId())),
				"the second message reaches the receiver instance, held: " + held + " flushed: " + flushed);

		// nothing else arrives: the instance's timeout is the only thing that can
		// still hand the second message on
		assertTrue(within(TIMEOUT_MS * 10, () -> flushed.stream().anyMatch(m -> "second".equals(m.getId()))),
				"the second message is flushed on the next timeout, flushed: " + flushed + " still held: " + held);
		assertEquals(0, held.size(), "nothing stays behind in the receiver instance");
	}

	@Test
	void anIdleThreadDoesNotFlushOverAndOver() throws Exception {
		MessageReceiverRunner runner = start();
		runner.accept(envelope("only"));
		assertTrue(firstFlushStarted.await(5, TimeUnit.SECONDS));
		firstFlushMayEnd.countDown();
		assertTrue(within(2000, () -> flushed.size() == 1));

		// with nothing new, one timeout flush is all there is
		Thread.sleep(TIMEOUT_MS * 5);
		org.mockito.Mockito.verify(((IGTimedOutMessageReceiverFactory) factoryOf(runner)).create(),
				org.mockito.Mockito.times(1)).onTimeoutEvent(any());
	}

	private static Object factoryOf(MessageReceiverRunner runner) throws Exception {
		java.lang.reflect.Field field = MessageReceiverRunner.class.getDeclaredField("factory");
		field.setAccessible(true);
		return field.get(runner);
	}
}
