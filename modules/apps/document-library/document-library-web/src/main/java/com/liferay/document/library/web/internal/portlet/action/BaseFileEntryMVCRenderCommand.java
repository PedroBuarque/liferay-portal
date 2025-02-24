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

package com.liferay.document.library.web.internal.portlet.action;

import com.liferay.change.tracking.spi.constants.CTTimelineKeys;
import com.liferay.document.library.kernel.exception.NoSuchFileEntryException;
import com.liferay.document.library.kernel.exception.NoSuchFileVersionException;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.portal.kernel.exception.NoSuchRepositoryEntryException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * @author Sergio González
 */
public abstract class BaseFileEntryMVCRenderCommand
	implements MVCRenderCommand {

	@Override
	public String render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortletException {

		try {
			FileEntry fileEntry = ActionUtil.getFileEntry(renderRequest);

			if (fileEntry != null) {
				ThemeDisplay themeDisplay =
					(ThemeDisplay)renderRequest.getAttribute(
						WebKeys.THEME_DISPLAY);

				checkPermissions(
					themeDisplay.getPermissionChecker(), fileEntry);

				renderRequest.setAttribute(
					CTTimelineKeys.CLASS_NAME, DLFileEntry.class.getName());
				renderRequest.setAttribute(
					CTTimelineKeys.CLASS_PK, fileEntry.getFileEntryId());
			}

			renderRequest.setAttribute(
				WebKeys.DOCUMENT_LIBRARY_FILE_ENTRY, fileEntry);

			String version = ParamUtil.getString(renderRequest, "version");

			if (Validator.isNotNull(version)) {
				renderRequest.setAttribute(
					WebKeys.DOCUMENT_LIBRARY_FILE_VERSION,
					ActionUtil.getFileVersion(renderRequest, fileEntry));
			}

			setAttributes(renderRequest, renderResponse);

			return getPath();
		}
		catch (NoSuchFileEntryException | NoSuchFileVersionException |
			   NoSuchRepositoryEntryException | PrincipalException exception) {

			SessionErrors.add(renderRequest, exception.getClass());

			return "/document_library/error.jsp";
		}
		catch (PortalException portalException) {
			throw new PortletException(portalException);
		}
	}

	protected void checkPermissions(
			PermissionChecker permissionChecker, FileEntry fileEntry)
		throws PortalException {
	}

	protected abstract String getPath();

	protected void setAttributes(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortalException {
	}

}