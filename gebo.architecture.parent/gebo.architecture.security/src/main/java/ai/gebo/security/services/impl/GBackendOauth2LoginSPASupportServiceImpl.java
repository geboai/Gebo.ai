package ai.gebo.security.services.impl;

import java.io.IOException;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Optional;
import java.util.UUID;

import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ai.gebo.crypting.services.GeboCryptSecretException;
import ai.gebo.crypting.services.IGeboCryptingService;
import ai.gebo.model.OperationStatus;
import ai.gebo.security.LocalJwtTokenProvider;
import ai.gebo.security.SecurityHeaderUtil;
import ai.gebo.security.SecurityHeaderUtil.XAuthType;
import ai.gebo.security.model.SecurityHeaderData;
import ai.gebo.security.model.oauth2.Oauth2DeliveryData;
import ai.gebo.security.repository.Oauth2DeliveryDataRepository;
import ai.gebo.security.services.BackendOauth2LoginSPASupportException;
import ai.gebo.security.services.IGBackendOauth2LoginSPASupportService;
import lombok.AllArgsConstructor;
import lombok.Data;

@Service
@AllArgsConstructor
public class GBackendOauth2LoginSPASupportServiceImpl implements IGBackendOauth2LoginSPASupportService {
	private final IGeboCryptingService cryptingService;
	private final Oauth2DeliveryDataRepository oauth2DeliveryDataRepository;
	private final LocalJwtTokenProvider jwtTokenProvider;
	private final static ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public String registerOauth2LoginAttempt(String remoteAddress, String nextUri, String tenantId,
			String registrationId) throws BackendOauth2LoginSPASupportException, GeboCryptSecretException {
		Oauth2DeliveryData _data = new Oauth2DeliveryData();
		_data.setId(UUID.randomUUID().toString());
		_data.setRemoteAddress(remoteAddress);
		_data.setRegistrationId(registrationId);
		_data.setTenantId(tenantId);
		_data.setNextUri(nextUri);
		GregorianCalendar gc = new GregorianCalendar();
		gc.add(GregorianCalendar.MINUTE, 10);
		_data.setExpires(gc.getTime());
		oauth2DeliveryDataRepository.insert(_data);
		return cryptingService.crypt(_data.getId());
	}

	@Override
	public String oauth2LoginSuccess(String loginAttemptId, String remoteAddress, DefaultOidcUser user)
			throws BackendOauth2LoginSPASupportException, GeboCryptSecretException {
		String id = cryptingService.decrypt(loginAttemptId);
		Optional<Oauth2DeliveryData> _optionalData = oauth2DeliveryDataRepository.findById(id);
		if (_optionalData.isEmpty())
			throw new BackendOauth2LoginSPASupportException("Invalid loginAttemptId");
		try {
			Oauth2DeliveryData _data = _optionalData.get();
			if (!_data.getRemoteAddress().equalsIgnoreCase(remoteAddress))
				throw new RuntimeException("Client registered from host:" + _data.getRemoteAddress()
						+ " but now presenting as:" + remoteAddress);
			String jwt = jwtTokenProvider.createToken(user);
			SecurityHeaderData data = SecurityHeaderUtil.createSelfsignedJwtSecurityHeaderOauth2(jwt);
			String json = objectMapper.writeValueAsString(data);
			String crypted = cryptingService.crypt(json);
			_data.setData(crypted);
			oauth2DeliveryDataRepository.save(_data);
			return _data.getNextUri();
		} catch (GeboCryptSecretException | JsonProcessingException e) {

			throw new BackendOauth2LoginSPASupportException("Exception crypting", e);

		}

	}

	@Data
	public static class SecurityHeaderDataValue {
		private String token;
		private XAuthType authType;
		private String authProviderId;
		private String authTenantId;
		private boolean empty;
	}

	@Override
	public OperationStatus<SecurityHeaderData> getAuthorizationData(String loginAttemptId, String remoteAddress)
			throws BackendOauth2LoginSPASupportException, GeboCryptSecretException, IOException {
		String id = cryptingService.decrypt(loginAttemptId);
		Optional<Oauth2DeliveryData> deliveredData = this.oauth2DeliveryDataRepository.findById(id);
		if (deliveredData.isEmpty()) {
			return OperationStatus.ofError("Wrong login status", "Wrong login status");
		}
		Oauth2DeliveryData data = deliveredData.get();
		if (!data.getRemoteAddress().equalsIgnoreCase(remoteAddress))
			throw new RuntimeException("Client registered from host:" + data.getRemoteAddress()
					+ " but now presenting as:" + remoteAddress);
		if (data.getExpires().after(new Date())) {
			this.oauth2DeliveryDataRepository.delete(data);
			String json = cryptingService.decrypt(data.getData());
			SecurityHeaderDataValue read;

			read = objectMapper.readValue(json.getBytes(), SecurityHeaderDataValue.class);
			return OperationStatus.of(
					new SecurityHeaderData(read.token, read.authType, read.authProviderId, read.authTenantId, false));

		}

		return OperationStatus.ofError("Wrong login status", "Too slow scripting or network");

	}

}
