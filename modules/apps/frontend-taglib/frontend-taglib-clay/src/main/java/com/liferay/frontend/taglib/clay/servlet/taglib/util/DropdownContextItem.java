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

package com.liferay.frontend.taglib.clay.servlet.taglib.util;

import java.util.List;

/**
 * @author Mikel Lorza
 */
public class DropdownContextItem extends DropdownItem {

	public DropdownContextItem() {
		put("type", "contextual");
	}

	public void setDropdownItems(List<DropdownItem> dropdownItems) {
		put("items", dropdownItems);
	}

}