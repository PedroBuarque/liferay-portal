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

package com.liferay.portal.search.web.internal.search.bar.portlet;

import com.liferay.portal.search.web.internal.display.context.SearchScopePreference;

/**
 * @author André de Oliveira
 */
public interface SearchBarPortletPreferences {

	public static final String PREFERENCE_KEY_DESTINATION = "destination";

	public static final String PREFERENCE_KEY_FEDERATED_SEARCH_KEY =
		"federatedSearchKey";

	public static final String PREFERENCE_KEY_INVISIBLE = "invisible";

	public static final String PREFERENCE_KEY_KEYWORDS_PARAMETER_NAME =
		"keywordsParameterName";

	public static final String PREFERENCE_KEY_SCOPE_PARAMETER_NAME =
		"scopeParameterName";

	public static final String PREFERENCE_KEY_SEARCH_SCOPE = "searchScope";

	public static final String PREFERENCE_KEY_SHOW_STAGED_RESULTS =
		"showStagedResults";

	public static final String
		PREFERENCE_KEY_SUGGESTIONS_CONTRIBUTOR_CONFIGURATION =
			"suggestionsContributorConfigurations";

	public static final String PREFERENCE_KEY_SUGGESTIONS_DISPLAY_THRESHOLD =
		"suggestionsDisplayThreshold";

	public static final String PREFERENCE_KEY_SUGGESTIONS_ENABLED =
		"suggestionsEnabled";

	public static final String PREFERENCE_KEY_USE_ADVANCED_SEARCH_SYNTAX =
		"useAdvancedSearchSyntax";

	public String getDestination();

	public String getFederatedSearchKey();

	public String getKeywordsParameterName();

	public String getScopeParameterName();

	public SearchScopePreference getSearchScopePreference();

	public String getSearchScopePreferenceString();

	public boolean isInvisible();

	public boolean isShowStagedResults();

	public boolean isSuggestionsEnabled();

	public boolean isUseAdvancedSearchSyntax();

}