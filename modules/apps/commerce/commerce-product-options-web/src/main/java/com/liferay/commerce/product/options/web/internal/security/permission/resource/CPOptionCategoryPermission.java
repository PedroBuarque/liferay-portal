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

package com.liferay.commerce.product.options.web.internal.security.permission.resource;

import com.liferay.commerce.product.model.CPOptionCategory;
import com.liferay.osgi.util.service.Snapshot;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;

/**
 * @author Marco Leo
 */
public class CPOptionCategoryPermission {

	public static boolean contains(
			PermissionChecker permissionChecker,
			CPOptionCategory cpOptionCategory, String actionId)
		throws PortalException {

		ModelResourcePermission<CPOptionCategory> modelResourcePermission =
			_cpOptionCategoryModelResourcePermissionSnapshot.get();

		return modelResourcePermission.contains(
			permissionChecker, cpOptionCategory, actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, long cpOptionCategoryId,
			String actionId)
		throws PortalException {

		ModelResourcePermission<CPOptionCategory> modelResourcePermission =
			_cpOptionCategoryModelResourcePermissionSnapshot.get();

		return modelResourcePermission.contains(
			permissionChecker, cpOptionCategoryId, actionId);
	}

	private static final Snapshot<ModelResourcePermission<CPOptionCategory>>
		_cpOptionCategoryModelResourcePermissionSnapshot = new Snapshot<>(
			CPOptionCategoryPermission.class,
			Snapshot.cast(ModelResourcePermission.class),
			"(model.class.name=com.liferay.commerce.product.model." +
				"CPOptionCategory)");

}