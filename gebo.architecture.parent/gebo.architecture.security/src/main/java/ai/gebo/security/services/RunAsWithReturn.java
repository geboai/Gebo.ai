package ai.gebo.security.services;

@FunctionalInterface
public interface RunAsWithReturn<T> {
	public T apply();
}