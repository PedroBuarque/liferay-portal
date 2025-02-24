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

package com.liferay.asset.link.internal.exportimport.staged.model.repository;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetLink;
import com.liferay.asset.kernel.model.adapter.StagedAssetLink;
import com.liferay.asset.kernel.service.AssetLinkLocalService;
import com.liferay.asset.util.StagingAssetEntryHelper;
import com.liferay.osgi.util.service.Snapshot;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.adapter.ModelAdapterUtil;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.List;

/**
 * @author Joao Victor Alves
 */
public class StagedAssetLinkStagedModelRepositoryUtil {

	public static StagedAssetLink fetchExistingAssetLink(
			long groupId, String assetEntry1Uuid, String assetEntry2Uuid)
		throws PortalException {

		StagingAssetEntryHelper stagingAssetEntryHelper =
			_stagingAssetEntryHelperSnapshot.get();

		AssetEntry assetEntry1 = stagingAssetEntryHelper.fetchAssetEntry(
			groupId, assetEntry1Uuid);
		AssetEntry assetEntry2 = stagingAssetEntryHelper.fetchAssetEntry(
			groupId, assetEntry2Uuid);

		if ((assetEntry1 == null) || (assetEntry2 == null)) {
			return null;
		}

		DynamicQuery dynamicQuery = _getAssetLinkDynamicQuery(
			assetEntry1.getEntryId(), assetEntry2.getEntryId());

		AssetLinkLocalService assetLinkLocalService =
			_assetLinkLocalServiceSnapshot.get();

		List<AssetLink> assetLinks = assetLinkLocalService.dynamicQuery(
			dynamicQuery);

		if (ListUtil.isNotEmpty(assetLinks)) {
			return ModelAdapterUtil.adapt(
				assetLinks.get(0), AssetLink.class, StagedAssetLink.class);
		}

		return null;
	}

	private static DynamicQuery _getAssetLinkDynamicQuery(
		long entryId1, long entryId2) {

		AssetLinkLocalService assetLinkLocalService =
			_assetLinkLocalServiceSnapshot.get();

		DynamicQuery dynamicQuery = assetLinkLocalService.dynamicQuery();

		Property entryId1IdProperty = PropertyFactoryUtil.forName("entryId1");

		dynamicQuery.add(entryId1IdProperty.eq(entryId1));

		Property entryId2IdProperty = PropertyFactoryUtil.forName("entryId2");

		dynamicQuery.add(entryId2IdProperty.eq(entryId2));

		return dynamicQuery;
	}

	private static final Snapshot<AssetLinkLocalService>
		_assetLinkLocalServiceSnapshot = new Snapshot<>(
			StagedAssetLinkStagedModelRepositoryUtil.class,
			AssetLinkLocalService.class);
	private static final Snapshot<StagingAssetEntryHelper>
		_stagingAssetEntryHelperSnapshot = new Snapshot<>(
			StagedAssetLinkStagedModelRepositoryUtil.class,
			StagingAssetEntryHelper.class);

}