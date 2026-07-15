/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.microservices.acl.controller;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ai.gebo.acl.AclGrantType;
import ai.gebo.acl.GAclEntry;
import ai.gebo.acl.IAclAliasesDao;

/**
 * The ACL alias store exposed to the other microservices of the cluster - the
 * whole {@link IAclAliasesDao}, reads and writes.
 *
 * <p>
 * <b>The writes are the point.</b> {@link IAclAliasesDao#addAcl(GAclEntry)} draws
 * alias integers from a Mongo sequence, so allocation has to happen in exactly one
 * place: two services allocating independently would hand out colliding integers,
 * and an alias collision is a silent authorization bug - two different grants
 * sharing a number. Consumers therefore never allocate locally. They ask the owner,
 * and the owner is the only service holding {@code gebo.acl.mongo}.
 * </p>
 *
 * <p>
 * The <i>decision</i> still never crosses the network: {@code AclAccessCheck} runs
 * in every service, over its own objects. What crosses is only the alias data it
 * needs - which is why the client can cache it.
 * </p>
 *
 * <p>
 * <b>Deliberately not a {@code @RestController}</b>: every Gebo app component-scans
 * {@code ai.gebo}, and a {@code @Component} would be published by that scan without
 * the participants guard, which only the auto-configuration installs alongside it.
 * </p>
 *
 * Gebo.ai comment agent
 */
@ResponseBody
@RequestMapping("${ai.gebo.acl.cluster.base-path:api/cluster/AclController}")
public class AclAliasesClusterController {

	private final IAclAliasesDao aliasesDao;

	public AclAliasesClusterController(IAclAliasesDao aliasesDao) {
		this.aliasesDao = aliasesDao;
	}

	// --- Reads --------------------------------------------------------------

	@GetMapping(value = "findAliasesByAclGrantedUniqueId", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Integer> findAliasesByAclGrantedUniqueId(@RequestParam("uniqueId") String aclGrantedUniqueId) {
		return aliasesDao.findAliasesByAclGrantedUniqueId(aclGrantedUniqueId);
	}

	@GetMapping(value = "findAliasesByAclGrantedUniqueIdAndAclGrantType",
			produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Integer> findAliasesByAclGrantedUniqueIdAndAclGrantType(
			@RequestParam("uniqueId") String aclGrantedUniqueId, @RequestParam("grantType") AclGrantType grantType) {
		return aliasesDao.findAliasesByAclGrantedUniqueIdAndAclGrantType(aclGrantedUniqueId, grantType);
	}

	/**
	 * The bulk read. It is a POST because the id list is unbounded - a user plus every
	 * group they belong to - and that does not belong in a query string.
	 */
	@PostMapping(value = "findAliasesByAclGrantedUniqueIdIn", consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Integer> findAliasesByAclGrantedUniqueIdIn(@RequestBody List<String> aclGrantedUniqueIds) {
		return aliasesDao.findAliasesByAclGrantedUniqueIdIn(aclGrantedUniqueIds);
	}

	@PostMapping(value = "findAliasesByAclGrantedUniqueIdInAndAclGrantType",
			consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Integer> findAliasesByAclGrantedUniqueIdInAndAclGrantType(
			@RequestParam("grantType") AclGrantType grantType, @RequestBody List<String> aclGrantedUniqueIds) {
		return aliasesDao.findAliasesByAclGrantedUniqueIdInAndAclGrantType(aclGrantedUniqueIds, grantType);
	}

	@GetMapping(value = "findAcl", produces = MediaType.APPLICATION_JSON_VALUE)
	public GAclEntry findAcl(@RequestParam("alias") int alias) {
		return aliasesDao.findAcl(alias);
	}

	@PostMapping(value = "findAlias", consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public Integer findAlias(@RequestBody GAclEntry entry) {
		return aliasesDao.findAlias(entry);
	}

	// --- Writes (owner-only allocation) --------------------------------------

	/**
	 * Allocates an alias. This is the call that must not be reimplemented locally in a
	 * consumer: the integer comes from a single Mongo sequence, held here.
	 */
	@PostMapping(value = "addAcl", consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public int addAcl(@RequestBody GAclEntry entry) {
		return aliasesDao.addAcl(entry);
	}

	@DeleteMapping("removeAcl")
	public void removeAcl(@RequestParam("alias") int alias) {
		aliasesDao.removeAcl(alias);
	}
}
