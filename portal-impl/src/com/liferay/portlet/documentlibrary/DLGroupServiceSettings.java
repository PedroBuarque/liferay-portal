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

package com.liferay.portlet.documentlibrary;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.settings.GroupServiceSettingsLocator;
import com.liferay.portal.kernel.settings.LocalizedValuesMap;
import com.liferay.portal.kernel.settings.ParameterMapSettings;
import com.liferay.portal.kernel.settings.Settings;
import com.liferay.portal.kernel.settings.SettingsFactoryUtil;
import com.liferay.portal.kernel.settings.TypedSettings;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portlet.documentlibrary.constants.DLConstants;

import java.util.Map;

/**
 * @author Adolfo Pérez
 */
@Settings.Config
public class DLGroupServiceSettings {

	public static DLGroupServiceSettings getInstance(long groupId)
		throws PortalException {

		Settings settings = SettingsFactoryUtil.getSettings(
			new GroupServiceSettingsLocator(groupId, DLConstants.SERVICE_NAME));

		return new DLGroupServiceSettings(settings);
	}

	public static DLGroupServiceSettings getInstance(
			long groupId, Map<String, String[]> parameterMap)
		throws PortalException {

		Settings settings = SettingsFactoryUtil.getSettings(
			new GroupServiceSettingsLocator(groupId, DLConstants.SERVICE_NAME));

		Settings parameterMapSettings = new ParameterMapSettings(
			parameterMap, settings);

		return new DLGroupServiceSettings(parameterMapSettings);
	}

	public DLGroupServiceSettings(Settings settings) {
		_typedSettings = new TypedSettings(settings);
	}

	public LocalizedValuesMap getEmailFileEntryAddedBody() {
		return _typedSettings.getLocalizedValuesMap("emailFileEntryAddedBody");
	}

	@Settings.Property(ignore = true)
	public String getEmailFileEntryAddedBodyXml() {
		return LocalizationUtil.getXml(
			getEmailFileEntryAddedBody(), "emailFileEntryAdded");
	}

	public LocalizedValuesMap getEmailFileEntryAddedSubject() {
		return _typedSettings.getLocalizedValuesMap(
			"emailFileEntryAddedSubject");
	}

	@Settings.Property(ignore = true)
	public String getEmailFileEntryAddedSubjectXml() {
		return LocalizationUtil.getXml(
			getEmailFileEntryAddedSubject(), "emailFileEntryAddedSubject");
	}

	public LocalizedValuesMap getEmailFileEntryExpiredBody() {
		return _typedSettings.getLocalizedValuesMap(
			"emailFileEntryExpiredBody");
	}

	@Settings.Property(ignore = true)
	public String getEmailFileEntryExpiredBodyXml() {
		return LocalizationUtil.getXml(
			getEmailFileEntryExpiredBody(), "emailFileEntryExpired");
	}

	public LocalizedValuesMap getEmailFileEntryExpiredSubject() {
		return _typedSettings.getLocalizedValuesMap(
			"emailFileEntryExpiredSubject");
	}

	@Settings.Property(ignore = true)
	public String getEmailFileEntryExpiredSubjectXml() {
		return LocalizationUtil.getXml(
			getEmailFileEntryExpiredSubject(), "emailFileEntryExpiredSubject");
	}

	public LocalizedValuesMap getEmailFileEntryReviewBody() {
		return _typedSettings.getLocalizedValuesMap("emailFileEntryReviewBody");
	}

	public String getEmailFileEntryReviewBodyXml() {
		return LocalizationUtil.getXml(
			getEmailFileEntryReviewBody(), "emailFileEntryReview");
	}

	public LocalizedValuesMap getEmailFileEntryReviewSubject() {
		return _typedSettings.getLocalizedValuesMap(
			"emailFileEntryReviewSubject");
	}

	public String getEmailFileEntryReviewSubjectXml() {
		return LocalizationUtil.getXml(
			getEmailFileEntryReviewSubject(), "emailFileEntryReviewSubject");
	}

	public LocalizedValuesMap getEmailFileEntryUpdatedBody() {
		return _typedSettings.getLocalizedValuesMap(
			"emailFileEntryUpdatedBody");
	}

	@Settings.Property(ignore = true)
	public String getEmailFileEntryUpdatedBodyXml() {
		return LocalizationUtil.getXml(
			getEmailFileEntryUpdatedBody(), "emailFileEntryUpdatedBody");
	}

	public LocalizedValuesMap getEmailFileEntryUpdatedSubject() {
		return _typedSettings.getLocalizedValuesMap(
			"emailFileEntryUpdatedSubject");
	}

	@Settings.Property(ignore = true)
	public String getEmailFileEntryUpdatedSubjectXml() {
		return LocalizationUtil.getXml(
			getEmailFileEntryUpdatedSubject(), "emailFileEntryUpdatedSubject");
	}

	public String getEmailFromAddress() {
		return _typedSettings.getValue("emailFromAddress");
	}

	public String getEmailFromName() {
		return _typedSettings.getValue("emailFromName");
	}

	public boolean isEmailFileEntryAddedEnabled() {
		return _typedSettings.getBooleanValue("emailFileEntryAddedEnabled");
	}

	public boolean isEmailFileEntryExpiredEnabled() {
		return _typedSettings.getBooleanValue("emailFileEntryExpiredEnabled");
	}

	public boolean isEmailFileEntryReviewEnabled() {
		return _typedSettings.getBooleanValue("emailFileEntryReviewEnabled");
	}

	public boolean isEmailFileEntryUpdatedEnabled() {
		return _typedSettings.getBooleanValue("emailFileEntryUpdatedEnabled");
	}

	public boolean isShowHiddenMountFolders() {
		return _typedSettings.getBooleanValue("showHiddenMountFolders");
	}

	private final TypedSettings _typedSettings;

}