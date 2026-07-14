package ai.gebo.security.services;

@FunctionalInterface
public interface RunAsWith2Exceptions<E extends Exception, E1 extends Exception> {
	public void run() throws E, E1;
}