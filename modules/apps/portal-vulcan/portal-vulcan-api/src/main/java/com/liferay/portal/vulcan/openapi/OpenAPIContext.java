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

package com.liferay.portal.vulcan.openapi;

import javax.ws.rs.core.UriInfo;

/**
 * @author Carlos Correa
 */
public class OpenAPIContext {

	public String getBaseURL() {
		return _baseURL;
	}

	public String getPath() {
		return _path;
	}

	public UriInfo getUriInfo() {
		return _uriInfo;
	}

	public String getVersion() {
		return _version;
	}

	public void setBaseURL(String baseURL) {
		_baseURL = baseURL;
	}

	public void setPath(String path) {
		_path = path;
	}

	public void setUriInfo(UriInfo uriInfo) {
		_uriInfo = uriInfo;
	}

	public void setVersion(String version) {
		_version = version;
	}

	private String _baseURL;
	private String _path;
	private UriInfo _uriInfo;
	private String _version;

}