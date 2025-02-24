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

package com.liferay.document.library.web.internal.exportimport.portlet.preferences.processor;

import com.liferay.document.library.constants.DLPortletKeys;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.PortletDataException;
import com.liferay.exportimport.portlet.preferences.processor.Capability;
import com.liferay.exportimport.portlet.preferences.processor.ExportImportPortletPreferencesProcessor;

import java.util.List;

import javax.portlet.PortletPreferences;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marcellus Tavares
 */
@Component(
	property = "javax.portlet.name=" + DLPortletKeys.MEDIA_GALLERY_DISPLAY,
	service = ExportImportPortletPreferencesProcessor.class
)
public class IGDisplayExportImportPortletPreferencesProcessor
	implements ExportImportPortletPreferencesProcessor {

	@Override
	public List<Capability> getExportCapabilities() {
		List<Capability> exportCapabilities =
			_dlExportImportPortletPreferencesProcessor.getExportCapabilities();

		exportCapabilities.add(_igDisplayExportCapability);

		return exportCapabilities;
	}

	@Override
	public List<Capability> getImportCapabilities() {
		List<Capability> importCapabilities =
			_dlExportImportPortletPreferencesProcessor.getImportCapabilities();

		importCapabilities.add(_igDisplayImportCapability);

		return importCapabilities;
	}

	@Override
	public PortletPreferences processExportPortletPreferences(
			PortletDataContext portletDataContext,
			PortletPreferences portletPreferences)
		throws PortletDataException {

		return _dlExportImportPortletPreferencesProcessor.
			processExportPortletPreferences(
				portletDataContext, portletPreferences);
	}

	@Override
	public PortletPreferences processImportPortletPreferences(
			PortletDataContext portletDataContext,
			PortletPreferences portletPreferences)
		throws PortletDataException {

		return _dlExportImportPortletPreferencesProcessor.
			processImportPortletPreferences(
				portletDataContext, portletPreferences);
	}

	@Reference(
		target = "(javax.portlet.name=" + DLPortletKeys.DOCUMENT_LIBRARY + ")"
	)
	private ExportImportPortletPreferencesProcessor
		_dlExportImportPortletPreferencesProcessor;

	@Reference(target = "(name=IGDisplayExportCapability)")
	private Capability _igDisplayExportCapability;

	@Reference(target = "(name=IGDisplayImportCapability)")
	private Capability _igDisplayImportCapability;

}