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
long fragmentCollectionId = ParamUtil.getLong(request, "fragmentCollectionId");

FragmentCollection fragmentCollection = FragmentCollectionLocalServiceUtil.fetchFragmentCollection(fragmentCollectionId);

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(fragmentEntriesDisplayContext.getRedirect());

renderResponse.setTitle((fragmentCollection != null) ? fragmentCollection.getName() : LanguageUtil.get(request, "add-fragment-set"));
%>

<portlet:actionURL name="/fragment/edit_fragment_collection" var="editFragmentCollectionURL">
	<portlet:param name="mvcRenderCommandName" value="/fragment/edit_fragment_collection" />
</portlet:actionURL>

<liferay-frontend:edit-form
	action="<%= editFragmentCollectionURL %>"
	name="fm"
>
	<aui:input name="redirect" type="hidden" value="<%= fragmentEntriesDisplayContext.getRedirect() %>" />
	<aui:input name="fragmentCollectionId" type="hidden" value="<%= fragmentCollectionId %>" />

	<liferay-frontend:edit-form-body>
		<liferay-ui:error exception="<%= DuplicateFragmentCollectionException.class %>" message="please-enter-a-unique-name" />
		<liferay-ui:error exception="<%= FragmentCollectionNameException.class %>" message="please-enter-a-valid-name" />

		<aui:model-context bean="<%= fragmentCollection %>" model="<%= FragmentCollection.class %>" />

		<liferay-frontend:fieldset>
			<aui:input label="name" name="name" placeholder="name" />

			<aui:input name="description" placeholder="description" />
		</liferay-frontend:fieldset>
	</liferay-frontend:edit-form-body>

	<liferay-frontend:edit-form-footer>
		<liferay-frontend:edit-form-buttons
			redirect="<%= fragmentEntriesDisplayContext.getRedirect() %>"
		/>
	</liferay-frontend:edit-form-footer>
</liferay-frontend:edit-form>