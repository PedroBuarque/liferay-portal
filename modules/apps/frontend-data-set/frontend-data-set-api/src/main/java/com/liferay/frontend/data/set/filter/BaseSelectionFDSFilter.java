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

package com.liferay.frontend.data.set.filter;

import com.liferay.portal.kernel.util.ResourceBundleUtil;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * @author Marko Cikos
 */
public abstract class BaseSelectionFDSFilter implements FDSFilter {

	public String getAPIURL() {
		return null;
	}

	public String getItemKey() {
		return null;
	}

	public String getItemLabel() {
		return null;
	}

	public String getPlaceholder() {
		return "search";
	}

	public ResourceBundle getResourceBundle(Locale locale) {
		return ResourceBundleUtil.getBundle(
			"content.Language", locale, getClass());
	}

	public List<SelectionFDSFilterItem> getSelectionFDSFilterItems(
		Locale locale) {

		return Collections.emptyList();
	}

	@Override
	public String getType() {
		return "selection";
	}

	public boolean isAutocompleteEnabled() {
		return false;
	}

	public boolean isMultiple() {
		return true;
	}

}