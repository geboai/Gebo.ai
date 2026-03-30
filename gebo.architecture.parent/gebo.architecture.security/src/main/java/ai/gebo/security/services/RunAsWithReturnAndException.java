package ai.gebo.security.services;

@FunctionalInterface
public interface RunAsWithReturnAndException<T, E extends Exception> {
	public T apply() throws E;
}