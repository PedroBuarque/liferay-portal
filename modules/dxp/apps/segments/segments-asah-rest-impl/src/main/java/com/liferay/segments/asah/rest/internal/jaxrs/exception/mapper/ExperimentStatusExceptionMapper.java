/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.segments.asah.rest.internal.jaxrs.exception.mapper;

import com.liferay.portal.vulcan.jaxrs.exception.mapper.BaseExceptionMapper;
import com.liferay.portal.vulcan.jaxrs.exception.mapper.Problem;
import com.liferay.segments.exception.SegmentsExperimentStatusException;

import javax.ws.rs.ext.ExceptionMapper;

import org.osgi.service.component.annotations.Component;

/**
 * Converts any {@code SegmentsExperimentStatusException} to a {@code 400}
 * error.
 *
 * @author Sarai Díaz
 * @review
 */
@Component(
	property = {
		"osgi.jaxrs.application.select=(osgi.jaxrs.name=Liferay.Segments.Asah.REST)",
		"osgi.jaxrs.extension=true",
		"osgi.jaxrs.name=Liferay.Segments.Asah.REST.ExperimentStatusExceptionMapper"
	},
	service = ExceptionMapper.class
)
public class ExperimentStatusExceptionMapper
	extends BaseExceptionMapper<SegmentsExperimentStatusException> {

	@Override
	protected Problem getProblem(
		SegmentsExperimentStatusException segmentsExperimentStatusException) {

		return new Problem(segmentsExperimentStatusException);
	}

}