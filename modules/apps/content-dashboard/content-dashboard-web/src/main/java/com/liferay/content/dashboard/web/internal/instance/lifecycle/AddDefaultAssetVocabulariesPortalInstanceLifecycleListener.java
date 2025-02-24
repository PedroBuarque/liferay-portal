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

package com.liferay.content.dashboard.web.internal.instance.lifecycle;

import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.model.AssetVocabularyConstants;
import com.liferay.asset.kernel.service.AssetVocabularyLocalService;
import com.liferay.content.dashboard.web.internal.constants.ContentDashboardConstants;
import com.liferay.content.dashboard.web.internal.item.ContentDashboardItemFactoryRegistry;
import com.liferay.content.dashboard.web.internal.util.AssetVocabularyUtil;
import com.liferay.info.search.InfoSearchClassMapperRegistry;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.instance.lifecycle.BasePortalInstanceLifecycleListener;
import com.liferay.portal.instance.lifecycle.PortalInstanceLifecycleListener;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.asset.util.AssetVocabularySettingsHelper;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pavel Savinov
 */
@Component(service = PortalInstanceLifecycleListener.class)
public class AddDefaultAssetVocabulariesPortalInstanceLifecycleListener
	extends BasePortalInstanceLifecycleListener {

	@Override
	public void portalInstanceRegistered(Company company) throws Exception {
		_addAssetVocabulary(company);

		Set<Long> searchClassNameIds = new HashSet<>();

		for (String className :
				_contentDashboardItemFactoryRegistry.getClassNames()) {

			searchClassNameIds.add(
				_portal.getClassNameId(
					_infoSearchClassMapperRegistry.getSearchClassName(
						className)));
		}

		for (ContentDashboardConstants.DefaultInternalAssetVocabularyName
				defaultInternalAssetVocabularyName :
					ContentDashboardConstants.
						DefaultInternalAssetVocabularyName.values()) {

			AssetVocabularyUtil.addAssetVocabulary(
				_assetVocabularyLocalService, searchClassNameIds, company,
				defaultInternalAssetVocabularyName.toString(),
				AssetVocabularyConstants.VISIBILITY_TYPE_INTERNAL);
		}
	}

	private void _addAssetVocabulary(Company company) throws Exception {
		AssetVocabulary assetVocabulary =
			_assetVocabularyLocalService.fetchGroupVocabulary(
				company.getGroupId(),
				StringUtil.toLowerCase(
					GetterUtil.getString(
						PropsValues.ASSET_VOCABULARY_DEFAULT)));

		if (assetVocabulary != null) {
			return;
		}

		Map<Locale, String> titleMap = new HashMap<>();

		User guestUser = company.getGuestUser();

		for (Locale locale :
				_language.getCompanyAvailableLocales(company.getCompanyId())) {

			titleMap.put(
				locale,
				_language.get(locale, PropsValues.ASSET_VOCABULARY_DEFAULT));
		}

		AssetVocabularySettingsHelper assetVocabularySettingsHelper =
			new AssetVocabularySettingsHelper();

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);

		_assetVocabularyLocalService.addVocabulary(
			null, guestUser.getUserId(), company.getGroupId(),
			PropsValues.ASSET_VOCABULARY_DEFAULT, StringPool.BLANK, titleMap,
			Collections.emptyMap(), assetVocabularySettingsHelper.toString(),
			AssetVocabularyConstants.VISIBILITY_TYPE_PUBLIC, serviceContext);
	}

	@Reference
	private AssetVocabularyLocalService _assetVocabularyLocalService;

	@Reference
	private ContentDashboardItemFactoryRegistry
		_contentDashboardItemFactoryRegistry;

	@Reference
	private InfoSearchClassMapperRegistry _infoSearchClassMapperRegistry;

	@Reference
	private Language _language;

	@Reference
	private Portal _portal;

}