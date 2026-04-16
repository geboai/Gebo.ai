package ai.gebo.security.services;

@FunctionalInterface
public interface RunAsWithException<E extends Exception> {
	public void run() throws E;
}