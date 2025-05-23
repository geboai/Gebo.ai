/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.llms.abstraction.layer.functions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;

import org.springframework.ai.chat.model.ToolContext;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.ai.IGToolCallbackSource;
import ai.gebo.architecture.ai.ToolCallbackDeclarationUtil;
import ai.gebo.architecture.ai.model.LLMtInteractionContextThreadLocal;
import ai.gebo.architecture.ai.model.LLMtInteractionContextThreadLocal.CalledFunction;
import ai.gebo.architecture.ai.model.LLMtInteractionContextThreadLocal.KBContext;
import ai.gebo.architecture.ai.model.ToolReference;
import ai.gebo.architecture.ai.model.ToolsCategory;
import ai.gebo.llms.abstraction.layer.functions.model.CurrentUserTeamsMembersGroupsFilter;
import ai.gebo.llms.abstraction.layer.functions.model.RestrictedUserInfos;
import ai.gebo.llms.abstraction.layer.functions.model.VoidObject;
import ai.gebo.security.model.UsersGroup;
import ai.gebo.security.repository.UserRepository;
import ai.gebo.security.repository.UserRepository.UserInfos;
import ai.gebo.security.repository.UsersGroupRepository;
import ai.gebo.security.services.IGSecurityService;

/**
 * AI generated comments
 * Service class implementing user-related functions that support tool callbacks.
 */
@Service
public class UsersFunctions implements IGToolCallbackSource {
	@Autowired
	UserRepository usersRepository;
	@Autowired
	UsersGroupRepository groupsRepository;
	@Autowired
	IGSecurityService securityService;

	/**
	 * Default constructor.
	 */
	public UsersFunctions() {

	}

	/**
	 * Gets the unique identifier of this tool.
	 * 
	 * @return the ID of the tool.
	 */
	@Override
	public String getId() {

		return "UsersFunctions";
	}

	/**
	 * Defines a tool callback for retrieving current user information.
	 * 
	 * @return a ToolCallback object for current user retrieval.
	 */
	private ToolCallback currentUserFunction() {

		BiFunction<VoidObject, ToolContext, RestrictedUserInfos> thisFunction = (t, c) -> {
			KBContext contextVisibility = LLMtInteractionContextThreadLocal.Context.get();
			CalledFunction function = new CalledFunction();
			function.setFunctionName("getActualUser");
			function.setFunctionDescription("Get actual user informations");
			function.setParamsDescription(List.of("No parameters"));
			if (contextVisibility != null) {

				contextVisibility.getCalledFunctions().add(function);
			}
			ToolCallbackDeclarationUtil.addCallToContext(c, function);
			return RestrictedUserInfos.of(securityService.getCurrentUser());
		};
		return ToolCallbackDeclarationUtil.declare(thisFunction, "getActualUser", "Get actual user informations",
				VoidObject.class, RestrictedUserInfos.class);
	}

	/**
	 * Inner class representing a list of restricted user information objects.
	 */
	public static class UsersList extends ArrayList<RestrictedUserInfos> {
	}

	/**
	 * Defines a tool callback for retrieving the current user's team and colleagues.
	 * 
	 * @return a ToolCallback object for retrieving the user's team and colleagues.
	 */
	private ToolCallback currentUsersTeamsColleagues() {
		BiFunction<CurrentUserTeamsMembersGroupsFilter, ToolContext, UsersList> thisFunction = (t, c) -> {

			return searchCurrentUsersTeamsColleague(t, c);
		};

		return ToolCallbackDeclarationUtil.declare(thisFunction, "searchCurrentUsersTeamsColleagues",
				"Get actual user's and colleagues list", CurrentUserTeamsMembersGroupsFilter.class, UsersList.class);
	}

	/**
	 * Searches for and retrieves the list of the current user's teams and colleagues based on a filter.
	 * 
	 * @param filter the filter criteria for the search.
	 * @param c the tool context.
	 * @return a list of restricted user information objects for team members and colleagues.
	 */
	private UsersList searchCurrentUsersTeamsColleague(CurrentUserTeamsMembersGroupsFilter filter, ToolContext c) {
		UsersList out = new UsersList();
		UserInfos currentUser = securityService.getCurrentUser();
		KBContext contextVisibility = LLMtInteractionContextThreadLocal.Context.get();
		List<String> params = new ArrayList<String>();
		UsersGroup groupFilter = new UsersGroup();
		if (filter.getGroupCode() != null && filter.getGroupCode().trim().length() > 0) {
			params.add("group code=\"" + filter.getGroupCode() + "\"");
			groupFilter.setCode(filter.getGroupCode());
		}
		if (filter.getGroupName() != null && filter.getGroupName().trim().length() > 0) {
			params.add("group name=\"" + filter.getGroupName() + "\"");
			groupFilter.setDescription(filter.getGroupName());
		}
		List<UsersGroup> groupsList = new ArrayList<UsersGroup>();
		if (params.isEmpty() && filter.getAllGroups() != null && filter.getAllGroups()) {
			params.add("all groups you are in");
			groupsList = groupsRepository.findByUserIdsIn(currentUser.getUsername());
		} else if (!params.isEmpty()) {
			List<UsersGroup> list = groupsRepository.findAll(Example.of(groupFilter));
			for (UsersGroup usersGroup : list) {
				if (usersGroup.getUserIds() != null && usersGroup.getUserIds().contains(currentUser.getUsername())) {
					groupsList.add(usersGroup);
				}
			}
		}
		final Map<String, Boolean> uids = new HashMap<String, Boolean>();
		for (UsersGroup usersGroup : groupsList) {
			if (usersGroup.getUserIds() != null) {
				usersGroup.getUserIds().stream().forEach(x -> {
					uids.put(x, true);
				});
			}
		}
		List<RestrictedUserInfos> list = usersRepository.findAllById(uids.keySet()).stream().map(x -> {
			return RestrictedUserInfos.of(x);
		}).toList();
		out.addAll(list);
		CalledFunction function = new CalledFunction();
		function.setFunctionName("searchCurrentUsersTeamsColleagues");
		function.setFunctionDescription("Get actual user's and colleagues list");
		function.setParamsDescription(params);
		if (contextVisibility != null) {

			contextVisibility.getCalledFunctions().add(function);
		}
		ToolCallbackDeclarationUtil.addCallToContext(c, function);
		return out;
	}

	/**
	 * Provides the list of available tool callbacks.
	 * 
	 * @return a list of tool callbacks.
	 */
	@Override
	public List<ToolCallback> getToolCallbacks() {
		List<ToolCallback> usersFunctions = new ArrayList<ToolCallback>();
		usersFunctions.add(currentUserFunction());
		usersFunctions.add(currentUsersTeamsColleagues());
		return usersFunctions;
	}

	/**
	 * Provides the category of tools implemented by this service.
	 * 
	 * @return the tool category.
	 */
	@Override
	public ToolsCategory getToolCategory() {

		return ToolsCategory.GEBO_USERS_SEARCH;
	}

	/**
	 * Provides the full list of tool references defined by this service.
	 * 
	 * @return a list of ToolReference objects representing the full tool references.
	 */
	@Override
	public List<ToolReference> getFullToolReferences() {
		ToolReference reference = new ToolReference();
		reference.setName("searchCurrentUsersTeamsColleagues");
		reference.setDescription("Get actual user's and colleagues list");
		reference.setUserUIfunctionDescription(
				"Retrieves minimal informations regarding list of collegues that are in your groups here on gebo.ai");
		ToolReference reference1 = new ToolReference();
		reference1.setName("getActualUser");
		reference1.setDescription("Get actual user informations");
		reference1.setUserUIfunctionDescription(
				"Retrieves minimal informations about you for actions for example like send you an email if you ask it");
		return List.of(reference, reference1);
	}

}