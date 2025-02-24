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

package com.liferay.info.field.type;

import com.liferay.portal.kernel.util.KeyValuePair;

/**
 * @author Jorge Ferrer
 */
public class CategoriesInfoFieldType implements InfoFieldType {

	public static final Attribute<CategoriesInfoFieldType, KeyValuePair>
		DEPENDENCY = new Attribute<>();

	public static final Attribute<CategoriesInfoFieldType, String>
		INFO_ITEM_SELECTOR_URL = new Attribute<>();

	public static final CategoriesInfoFieldType INSTANCE =
		new CategoriesInfoFieldType();

	@Override
	public String getName() {
		return "categories";
	}

	private CategoriesInfoFieldType() {
	}

}