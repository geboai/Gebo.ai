package ai.gebo.secrets.services;

import java.util.List;

import ai.gebo.crypting.services.GeboCryptSecretException;
import ai.gebo.secrets.model.AbstractGeboSecretContent;
import ai.gebo.secrets.model.GeboCustomSecretContent;
import ai.gebo.secrets.model.SecretInfo;

/**
 * Extension point that lets a deployment resolve a custom secret from a source
 * {@link IGeboSecretsAccessService} knows nothing about.
 *
 * Every implementation found on the classpath is collected into
 * {@link IGSecretsAdditionalProviderRepositoryPattern} and consulted, in
 * repository order, BEFORE the predefined resolution chain of
 * {@code GeboSecretsAccessServiceImpl} (configuration-declared secrets, the
 * external storage service, then the encrypted store). The first provider that
 * returns a non-null content wins and the predefined chain is not reached; if
 * every provider declines, resolution continues exactly as if none existed.
 *
 * Because the providers run inside the service implementation, they apply both
 * to the monolithic application and - through the secrets microservice that
 * hosts the same implementation - to every microservice that resolves secrets
 * remotely. There is deliberately no implementation in the community platform:
 * the repository is empty there and the hook costs one iteration over an empty
 * list.
 *
 * Implementations must be side-effect free for unknown ids: returning
 * {@code null} is how a provider says "not mine, keep looking". A provider that
 * throws is logged and skipped, so one misbehaving provider cannot take secret
 * resolution down.
 */
public interface IGSecretsAdditionalProvider {

	/**
	 * Stable identifier of this provider, unique across the implementations
	 * bound in one application.
	 *
	 * It is what the repository reports as the implementation code, so it is
	 * also what makes a duplicate binding fail fast at startup. It plays no part
	 * in choosing a provider: the repository is cycled in order, not looked up.
	 *
	 * @return the provider identifier, never {@code null}
	 */
	public String getProviderId();

	/**
	 * Resolves a custom secret by id, or declines it.
	 *
	 * Mirrors
	 * {@link IGeboSecretsAccessService#getCustomSecretContentById(String, Class)}
	 * so that a provider can be written against the same contract the service
	 * exposes to its callers.
	 *
	 * @param id   the secret id being resolved
	 * @param type the caller-side {@link GeboCustomSecretContent} subclass the
	 *             content has to be returned as
	 * @return the resolved content, or {@code null} to let the next provider -
	 *         and ultimately the predefined chain - handle the id
	 * @throws GeboCryptSecretException if the provider owns the id but cannot
	 *                                  produce its content
	 */
	public <T extends GeboCustomSecretContent> T getCustomSecretContentById(String id, Class<T> type)
			throws GeboCryptSecretException;

	/**
	 * Resolves a secret of any type by id, or declines it.
	 *
	 * Mirrors {@link IGeboSecretsAccessService#getSecretContentById(String)} and
	 * is integrated the same way the other two are: the providers are cycled in
	 * repository order ahead of the predefined chain and the first non-null
	 * content wins.
	 *
	 * Unlike {@link #getCustomSecretContentById(String, Class)} the caller names
	 * no type, so the provider chooses the {@link AbstractGeboSecretContent}
	 * subclass and must make {@code type()} report the matching
	 * {@code GeboSecretType} - that is what every caller switches on.
	 *
	 * A provider that only serves custom secrets can answer this method by
	 * delegating to its own {@link #getCustomSecretContentById(String, Class)}
	 * with {@code GeboCustomSecretContent.class}; the two are separate methods
	 * because the platform reads secrets through both entry points and a
	 * provider that implemented only one would be invisible to half of them.
	 *
	 * @param id the secret id being resolved
	 * @return the resolved content, or {@code null} to let the next provider -
	 *         and ultimately the predefined chain - handle the id
	 * @throws GeboCryptSecretException if the provider owns the id but cannot
	 *                                  produce its content
	 */
	public AbstractGeboSecretContent getSecretContentById(String id) throws GeboCryptSecretException;

	/**
	 * Lists the secrets this provider holds for a context, or declines the
	 * context.
	 *
	 * Mirrors
	 * {@link IGeboSecretsAccessService#getSecretInfoByContextCode(String)} and is
	 * integrated the same way {@link #getCustomSecretContentById(String, Class)}
	 * is: the providers are cycled in repository order and the first non-null
	 * answer is returned as-is, without the predefined chain running at all.
	 *
	 * Note what that means for a list. Returning {@code null} is how a provider
	 * says "this context is not mine, keep looking". Returning an EMPTY list is a
	 * different statement - "this context is mine and it holds no secrets" - and
	 * it wins, so the configured and stored secrets of that context are not
	 * listed. A provider that only wants to contribute to a context must not
	 * answer it at all.
	 *
	 * @param contextCode the context whose secrets are being listed
	 * @return the secrets this provider holds for the context, or {@code null} to
	 *         let the next provider - and ultimately the predefined chain -
	 *         answer
	 * @throws GeboCryptSecretException if the provider owns the context but
	 *                                  cannot list it
	 */
	public List<SecretInfo> getSecretInfoByContextCode(String contextCode) throws GeboCryptSecretException;
}
