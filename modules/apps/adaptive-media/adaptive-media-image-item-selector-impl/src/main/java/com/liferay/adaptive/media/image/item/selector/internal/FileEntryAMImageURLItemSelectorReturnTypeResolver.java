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

package com.liferay.adaptive.media.image.item.selector.internal;

import com.liferay.adaptive.media.image.item.selector.AMImageURLItemSelectorReturnType;
import com.liferay.adaptive.media.image.media.query.Condition;
import com.liferay.adaptive.media.image.media.query.MediaQuery;
import com.liferay.adaptive.media.image.media.query.MediaQueryProvider;
import com.liferay.document.library.util.DLURLHelper;
import com.liferay.item.selector.ItemSelectorReturnTypeResolver;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.portletfilerepository.PortletFileRepository;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.theme.ThemeDisplay;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Roberto Díaz
 */
@Component(
	property = "service.ranking:Integer=100",
	service = ItemSelectorReturnTypeResolver.class
)
public class FileEntryAMImageURLItemSelectorReturnTypeResolver
	implements ItemSelectorReturnTypeResolver
		<AMImageURLItemSelectorReturnType, FileEntry> {

	@Override
	public Class<AMImageURLItemSelectorReturnType>
		getItemSelectorReturnTypeClass() {

		return AMImageURLItemSelectorReturnType.class;
	}

	@Override
	public Class<FileEntry> getModelClass() {
		return FileEntry.class;
	}

	@Override
	public String getValue(FileEntry fileEntry, ThemeDisplay themeDisplay)
		throws Exception {

		String previewURL = null;

		if (fileEntry.getGroupId() == fileEntry.getRepositoryId()) {
			previewURL = _dlURLHelper.getImagePreviewURL(
				fileEntry, fileEntry.getFileVersion(), themeDisplay,
				StringPool.BLANK, false, false);
		}
		else {
			previewURL = _portletFileRepository.getPortletFileEntryURL(
				themeDisplay, fileEntry, "&imagePreview=1", false);
		}

		return JSONUtil.put(
			"defaultSource", previewURL
		).put(
			"fileEntryId", String.valueOf(fileEntry.getFileEntryId())
		).put(
			"sources",
			JSONUtil.toJSONArray(
				_mediaQueryProvider.getMediaQueries(fileEntry),
				this::_getSourceJSONObject)
		).toString();
	}

	private JSONObject _getSourceJSONObject(MediaQuery mediaQuery) {
		return JSONUtil.put(
			"attributes",
			() -> {
				JSONObject attributesJSONObject =
					_jsonFactory.createJSONObject();

				for (Condition condition : mediaQuery.getConditions()) {
					attributesJSONObject.put(
						condition.getAttribute(), condition.getValue());
				}

				return attributesJSONObject;
			}
		).put(
			"src", mediaQuery.getSrc()
		);
	}

	@Reference
	private DLURLHelper _dlURLHelper;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private MediaQueryProvider _mediaQueryProvider;

	@Reference
	private PortletFileRepository _portletFileRepository;

}