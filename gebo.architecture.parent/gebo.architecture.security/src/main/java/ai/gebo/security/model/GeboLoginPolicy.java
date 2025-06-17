package ai.gebo.security.model;
/***********************************************************************************************
 * Gebo Login and registration policy enum
 */
public enum GeboLoginPolicy {
	// Every users coming from an OAUTH2 provider signed as AUTHENTICATION can use
	// the system
	TRUST_ANY_IDENTITY,
	// Every user registered as user inside the platform if OAUTH2 or inserted as
	// JWT can enter the system
	REQUIRE_REGISTERED_USER,
	// Every user must be invited from an admin and must register himself following
	// the registration ticket
	REQUIRE_INVITATION,
	// Every user coming from OAUTH2 provider or with JWT registration can enter the
	// system
	USER_SELF_REGISTERS
}
