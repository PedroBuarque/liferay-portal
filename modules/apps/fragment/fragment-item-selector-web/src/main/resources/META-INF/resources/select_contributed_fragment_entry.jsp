<%--
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
--%>

<%@ include file="/init.jsp" %>

<%
DefaultFragmentEntriesDisplayContext defaultFragmentEntriesDisplayContext = new DefaultFragmentEntriesDisplayContext(request, liferayPortletRequest, liferayPortletResponse);
%>

<clay:management-toolbar
	managementToolbarDisplayContext="<%= new ContributedFragmentEntriesItemSelectorViewManagementToolbarDisplayContext(request, liferayPortletRequest, liferayPortletResponse, defaultFragmentEntriesDisplayContext.getFragmentsSearchContainer()) %>"
/>

<clay:container-fluid>
	<liferay-site-navigation:breadcrumb
		breadcrumbEntries="<%= defaultFragmentEntriesDisplayContext.getBreadcrumbEntries() %>"
	/>

	<liferay-ui:search-container
		searchContainer="<%= defaultFragmentEntriesDisplayContext.getFragmentsSearchContainer() %>"
	>
		<liferay-ui:search-container-row
			className="com.liferay.fragment.model.FragmentEntry"
			modelVar="fragmentEntry"
		>
			<liferay-ui:search-container-column-text>
				<clay:vertical-card
					data-fragmententrykey="<%= fragmentEntry.getFragmentEntryKey() %>"
					data-fragmententryname="<%= fragmentEntry.getName() %>"
					verticalCard="<%= new FragmentEntryVerticalCard(fragmentEntry) %>"
				/>
			</liferay-ui:search-container-column-text>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			displayStyle="icon"
			markupView="lexicon"
		/>
	</liferay-ui:search-container>
</clay:container-fluid>