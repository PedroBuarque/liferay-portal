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

package com.liferay.portal.kernel.settings;

import com.liferay.portal.kernel.settings.definition.ConfigurationPidMapping;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Iván Zaera
 */
@ProviderType
public interface SettingsLocatorHelper {

	public Settings getCompanyConfigurationBeanSettings(
		long companyId, String configurationPid, Settings parentSettings);

	public Settings getCompanyPortletPreferencesSettings(
		long companyId, String settingsId, Settings parentSettings);

	public Settings getConfigurationBeanSettings(String configurationPid);

	public ConfigurationPidMapping getConfigurationPidMapping(
		String configurationId);

	public Settings getGroupConfigurationBeanSettings(
		long groupId, String configurationPid, Settings parentSettings);

	public Settings getGroupPortletPreferencesSettings(
		long groupId, String settingsId, Settings parentSettings);

	public Settings getPortalPreferencesSettings(
		long companyId, Settings parentSettings);

	public Settings getPortletInstanceConfigurationBeanSettings(
		String portletId, String configurationPid, Settings parentSettings);

	public Settings getPortletInstancePortletPreferencesSettings(
		long companyId, long ownerId, int ownerType, long plid,
		String portletId, Settings parentSettings);

	public Settings getPortletInstancePortletPreferencesSettings(
		long companyId, long plid, String portletId, Settings parentSettings);

	public Settings getServerSettings(String settingsId);

	public SettingsDescriptor getSettingsDescriptor(String settingsId);

}