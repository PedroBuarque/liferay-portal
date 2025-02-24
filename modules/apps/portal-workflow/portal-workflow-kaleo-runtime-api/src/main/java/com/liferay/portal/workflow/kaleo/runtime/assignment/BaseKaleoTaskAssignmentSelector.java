/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.workflow.kaleo.runtime.assignment;

import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.workflow.kaleo.KaleoTaskAssignmentFactory;
import com.liferay.portal.workflow.kaleo.model.KaleoTaskAssignment;
import com.liferay.portal.workflow.kaleo.runtime.constants.AssigneeConstants;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.osgi.annotation.versioning.ProviderType;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 */
@ProviderType
public abstract class BaseKaleoTaskAssignmentSelector
	implements KaleoTaskAssignmentSelector {

	@SuppressWarnings("unchecked")
	protected Collection<KaleoTaskAssignment> getKaleoTaskAssignments(
		Map<String, ?> results) {

		List<KaleoTaskAssignment> kaleoTaskAssignments = new ArrayList<>();

		if (results.get(AssigneeConstants.ROLES) != null) {
			getRoleKaleoTaskAssignments(
				(List<Role>)results.get(AssigneeConstants.ROLES),
				kaleoTaskAssignments);

			return kaleoTaskAssignments;
		}

		if (results.get(AssigneeConstants.USER) != null) {
			kaleoTaskAssignments.add(
				getUserKaleoTaskAssignment(
					(User)results.get(AssigneeConstants.USER)));

			return kaleoTaskAssignments;
		}

		for (User user : (List<User>)results.get(AssigneeConstants.USERS)) {
			kaleoTaskAssignments.add(getUserKaleoTaskAssignment(user));
		}

		return kaleoTaskAssignments;
	}

	protected void getRoleKaleoTaskAssignments(
		List<Role> roles, List<KaleoTaskAssignment> kaleoTaskAssignments) {

		if (roles == null) {
			return;
		}

		for (Role role : roles) {
			KaleoTaskAssignment kaleoTaskAssignment =
				kaleoTaskAssignmentFactory.createKaleoTaskAssignment();

			kaleoTaskAssignment.setAssigneeClassName(Role.class.getName());
			kaleoTaskAssignment.setAssigneeClassPK(role.getRoleId());

			kaleoTaskAssignments.add(kaleoTaskAssignment);
		}
	}

	protected KaleoTaskAssignment getUserKaleoTaskAssignment(User user) {
		KaleoTaskAssignment kaleoTaskAssignment =
			kaleoTaskAssignmentFactory.createKaleoTaskAssignment();

		kaleoTaskAssignment.setAssigneeClassName(User.class.getName());
		kaleoTaskAssignment.setAssigneeClassPK(user.getUserId());

		return kaleoTaskAssignment;
	}

	@Reference
	protected KaleoTaskAssignmentFactory kaleoTaskAssignmentFactory;

}