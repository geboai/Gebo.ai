package ai.gebo.security.services;

@FunctionalInterface
public interface RunAsWith3Exceptions<E extends Exception, E1 extends Exception, E2 extends Exception> {
	public void run() throws E, E1, E2;
}