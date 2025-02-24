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

package com.liferay.portal.ee.license;

import com.liferay.portal.kernel.events.ActionException;
import com.liferay.portal.kernel.events.LifecycleEvent;
import com.liferay.portal.kernel.license.messaging.LCSPortletState;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.session.AuthenticatedSessionManagerUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.license.LicenseConstants;
import com.liferay.portlet.admin.util.OmniadminUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Shuyang Zhou
 * @author Amos Fong
 */
public class LifecycleAction
	implements com.liferay.portal.kernel.events.LifecycleAction {

	public void processLifecycleEvent(LifecycleEvent lifecycleEvent)
		throws ActionException {

		HttpServletRequest httpServletRequest = lifecycleEvent.getRequest();
		HttpServletResponse httpServletResponse = lifecycleEvent.getResponse();

		try {
			int[] lcsStates = LCSLicenseManager.getLCSStates(
				httpServletRequest);

			String redirect = getRedirect(
				httpServletRequest, lcsStates[0], lcsStates[1]);

			if (Validator.isNotNull(redirect)) {
				httpServletResponse.sendRedirect(redirect);
			}
		}
		catch (Exception exception) {
			throw new ActionException(exception);
		}
	}

	protected String getRedirect(
		HttpServletRequest httpServletRequest, int lcsPortletState,
		int lcsLicenseState) {

		String path = httpServletRequest.getRequestURI();

		if (path.equals(
				PortalUtil.getPathContext() + "/c/portal/setup_wizard") ||
			path.equals(
				PortalUtil.getPathContext() + "/c/portal/terms_of_use") ||
			path.equals(
				PortalUtil.getPathContext() + "/c/portal/update_password") ||
			path.equals(
				PortalUtil.getPathContext() +
					"/c/portal/update_reminder_query") ||
			path.equals(
				PortalUtil.getPathContext() +
					"/c/portal/update_terms_of_use")) {

			return null;
		}

		if (path.equals(PortalUtil.getPathContext() + "/c/portal/license")) {
			if (_isValidRequest(
					httpServletRequest, lcsPortletState, lcsLicenseState)) {

				httpServletRequest.setAttribute(
					"LCS_LICENSE_STATE", lcsLicenseState);
				httpServletRequest.setAttribute(
					"LCS_PORTLET_STATE", lcsPortletState);

				return null;
			}

			return PortalUtil.getPathContext() + "/c/portal/layout";
		}

		if (path.equals(
				PortalUtil.getPathContext() + "/c/portal/license_activation")) {

			if (_isValidPortalActivationRequest(
					httpServletRequest, lcsLicenseState)) {

				httpServletRequest.setAttribute(
					"LCS_LICENSE_STATE", lcsLicenseState);
				httpServletRequest.setAttribute(
					"LCS_PORTLET_STATE", lcsPortletState);

				return null;
			}

			return PortalUtil.getPathContext() + "/c/portal/layout";
		}

		if ((lcsPortletState == LCSPortletState.GOOD.intValue()) ||
			(lcsLicenseState == LicenseConstants.STATE_GOOD)) {

			return null;
		}

		try {
			if (PortalUtil.getUser(httpServletRequest) != null) {
				AuthenticatedSessionManagerUtil.renewSession(
					httpServletRequest, httpServletRequest.getSession());
			}
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}
		}

		return PortalUtil.getPathContext() + "/c/portal/license_activation";
	}

	private boolean _isOmniAdmin(HttpServletRequest httpServletRequest) {
		User user = null;

		try {
			user = PortalUtil.getUser(httpServletRequest);
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}
		}

		if ((user != null) && OmniadminUtil.isOmniadmin(user)) {
			return true;
		}

		return false;
	}

	private boolean _isValidPortalActivationRequest(
		HttpServletRequest httpServletRequest, int lcsLicenseState) {

		if ((lcsLicenseState != LicenseConstants.STATE_GOOD) ||
			_isOmniAdmin(httpServletRequest)) {

			return true;
		}

		return false;
	}

	private boolean _isValidRequest(
		HttpServletRequest httpServletRequest, int lcsPortletState,
		int lcsLicenseState) {

		if (((lcsPortletState != LCSPortletState.GOOD.intValue()) &&
			 (lcsLicenseState != LicenseConstants.STATE_GOOD)) ||
			_isOmniAdmin(httpServletRequest)) {

			return true;
		}

		return false;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LifecycleAction.class);

}