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

package com.liferay.change.tracking.rest.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.change.tracking.model.CTCollection;
import com.liferay.change.tracking.rest.client.dto.v1_0.CTProcess;
import com.liferay.change.tracking.service.CTCollectionLocalService;
import com.liferay.change.tracking.service.CTProcessLocalService;
import com.liferay.portal.kernel.test.rule.DataGuard;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.Inject;

import org.junit.runner.RunWith;

/**
 * @author Pei-Jung Lan
 */
@DataGuard(scope = DataGuard.Scope.METHOD)
@RunWith(Arquillian.class)
public class CTProcessResourceTest extends BaseCTProcessResourceTestCase {

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {"description", "name"};
	}

	@Override
	protected String[] getIgnoredEntityFieldNames() {
		return new String[] {"description", "ownerName", "status"};
	}

	@Override
	protected CTProcess testGetCTProcess_addCTProcess() throws Exception {
		return _addCTProcess();
	}

	@Override
	protected CTProcess testGetCTProcessesPage_addCTProcess(CTProcess ctProcess)
		throws Exception {

		CTProcess postCTProcess = _addCTProcess(
			ctProcess.getName(), ctProcess.getDescription());

		com.liferay.change.tracking.model.CTProcess serviceBuilderCTProcess =
			_ctProcessLocalService.getCTProcess(postCTProcess.getId());

		serviceBuilderCTProcess.setCreateDate(ctProcess.getDatePublished());

		_ctProcessLocalService.updateCTProcess(serviceBuilderCTProcess);

		return ctProcessResource.getCTProcess(
			serviceBuilderCTProcess.getCtProcessId());
	}

	@Override
	protected CTProcess testGraphQLCTProcess_addCTProcess() throws Exception {
		return _addCTProcess();
	}

	@Override
	protected CTProcess testPostCTProcessRevert_addCTProcess()
		throws Exception {

		CTProcess ctProcess = _addCTProcess();

		com.liferay.change.tracking.model.CTProcess serviceBuilderCTProcess =
			_ctProcessLocalService.getCTProcess(ctProcess.getId());

		CTCollection ctCollection = _ctCollectionLocalService.getCTCollection(
			serviceBuilderCTProcess.getCtCollectionId());

		ctCollection.setStatus(WorkflowConstants.STATUS_APPROVED);

		_ctCollectionLocalService.updateCTCollection(ctCollection);

		return ctProcess;
	}

	private CTProcess _addCTProcess() throws Exception {
		return _addCTProcess(
			RandomTestUtil.randomString(), RandomTestUtil.randomString());
	}

	private CTProcess _addCTProcess(String name, String description)
		throws Exception {

		CTCollection ctCollection = _ctCollectionLocalService.addCTCollection(
			TestPropsValues.getCompanyId(), TestPropsValues.getUserId(), name,
			description);

		com.liferay.change.tracking.model.CTProcess serviceBuilderCTProcess =
			_ctProcessLocalService.addCTProcess(
				TestPropsValues.getUserId(), ctCollection.getCtCollectionId());

		return ctProcessResource.getCTProcess(
			serviceBuilderCTProcess.getCtProcessId());
	}

	@Inject
	private CTCollectionLocalService _ctCollectionLocalService;

	@Inject
	private CTProcessLocalService _ctProcessLocalService;

}