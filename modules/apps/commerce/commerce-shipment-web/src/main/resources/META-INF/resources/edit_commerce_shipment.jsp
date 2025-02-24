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
CommerceShipmentDisplayContext commerceShipmentDisplayContext = (CommerceShipmentDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CommerceShipment commerceShipment = commerceShipmentDisplayContext.getCommerceShipment();

AccountEntry accountEntry = commerceShipment.getAccountEntry();

portletDisplay.setShowBackIcon(true);

if (Validator.isNull(redirect)) {
	portletDisplay.setURLBack(String.valueOf(renderResponse.createRenderURL()));
}
else {
	portletDisplay.setURLBack(redirect);
}
%>

<liferay-ui:error embed="<%= false %>" exception="<%= CommerceShipmentStatusException.class %>" message="please-select-a-valid-warehouse-and-quantity-for-all-shipment-items" />
<liferay-ui:error embed="<%= false %>" exception="<%= CommerceShipmentItemQuantityException.class %>" message="please-add-at-least-one-item-to-the-shipment" />

<liferay-portlet:renderURL var="editCommerceShipmentExternalReferenceCodeURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
	<portlet:param name="mvcRenderCommandName" value="/commerce_shipment/edit_commerce_shipment_external_reference_code" />
	<portlet:param name="commerceShipmentId" value="<%= String.valueOf(commerceShipment.getCommerceShipmentId()) %>" />
</liferay-portlet:renderURL>

<commerce-ui:header
	actions="<%= commerceShipmentDisplayContext.getHeaderActionModels() %>"
	bean="<%= commerceShipment %>"
	beanIdLabel="id"
	externalReferenceCode="<%= commerceShipment.getExternalReferenceCode() %>"
	externalReferenceCodeEditUrl="<%= editCommerceShipmentExternalReferenceCodeURL %>"
	model="<%= CommerceShipment.class %>"
	thumbnailUrl="<%= commerceShipmentDisplayContext.getCommerceAccountThumbnailURL(accountEntry, themeDisplay.getPathImage()) %>"
	title="<%= String.valueOf(commerceShipment.getCommerceShipmentId()) %>"
	wrapperCssClasses="side-panel-top-anchor"
/>

<div id="<portlet:namespace />editShipmentContainer">
	<liferay-frontend:screen-navigation
		containerWrapperCssClass="container"
		key="<%= CommerceShipmentScreenNavigationConstants.SCREEN_NAVIGATION_KEY_COMMERCE_SHIPMENT_GENERAL %>"
		modelBean="<%= commerceShipment %>"
		portletURL="<%= currentURLObj %>"
	/>
</div>