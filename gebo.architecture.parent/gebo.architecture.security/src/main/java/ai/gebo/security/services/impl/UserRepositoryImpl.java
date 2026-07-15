package ai.gebo.security.services.impl;

import ai.gebo.security.model.UserInfos;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import ai.gebo.security.model.EditableUser;
import ai.gebo.security.model.User;
import ai.gebo.security.repository.UserRepository;
import ai.gebo.security.repository.UserRepositoryCustom;

@Service
public class UserRepositoryImpl implements UserRepositoryCustom {

	private final MongoTemplate mongoTemplate;

	@Autowired
	public UserRepositoryImpl(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}

	@Override
	public Page<UserInfos> findByQbe(EditableUser example, Pageable pageable) {
		Query query = buildQuery(example);

		long total = mongoTemplate.count(query, User.class);

		query.with(pageable);

		/*
		 * Opzionale: proietta solo i campi che servono a UserInfos. Questo riduce
		 * payload e mapping.
		 */
		query.fields().include("username").include("name").include("sourname").include("disabled").include("roles")
				.include("customInfos");

		List<User> users = mongoTemplate.find(query, User.class);

		List<UserInfos> content = users.stream().map(UserInfos::of).toList();

		return new PageImpl<>(content, pageable, total);
	}

	private Query buildQuery(EditableUser example) {
		Query query = new Query();

		if (example == null) {
			return query;
		}

		List<Criteria> criteria = new ArrayList<>();

		addContainsIgnoreCase(criteria, "username", example.getUsername());
		addContainsIgnoreCase(criteria, "name", example.getName());
		addContainsIgnoreCase(criteria, "sourname", example.getSourname());

		/*
		 * Role: match esatto su elemento della lista roles.
		 *
		 * Se example.getRoles() contiene ["ADMIN"], MongoDB cerca documenti dove
		 * l'array roles contiene esattamente "ADMIN".
		 *
		 * Se contiene più ruoli, questa versione cerca utenti che abbiano almeno uno
		 * dei ruoli indicati.
		 */
		List<String> roles = normalizeRoles(example.getRoles());
		if (!roles.isEmpty()) {
			if (roles.size() == 1) {
				criteria.add(Criteria.where("roles").is(roles.get(0)));
			} else {
				criteria.add(Criteria.where("roles").in(roles));
			}
		}

		if (!criteria.isEmpty()) {
			query.addCriteria(new Criteria().andOperator(criteria.toArray(new Criteria[0])));
		}

		return query;
	}

	private void addContainsIgnoreCase(List<Criteria> criteria, String field, String value) {
		if (isBlank(value)) {
			return;
		}

		String escaped = Pattern.quote(value.trim());

		/*
		 * Regex Mongo: .*test.* => sottostringa i => case-insensitive
		 */
		criteria.add(Criteria.where(field).regex(".*" + escaped + ".*", "i"));
	}

	private List<String> normalizeRoles(List<String> roles) {
		if (roles == null) {
			return List.of();
		}

		return roles.stream().filter(role -> !isBlank(role)).map(String::trim).distinct().toList();
	}

	private boolean isBlank(String value) {
		return value == null || value.trim().isEmpty();
	}
}