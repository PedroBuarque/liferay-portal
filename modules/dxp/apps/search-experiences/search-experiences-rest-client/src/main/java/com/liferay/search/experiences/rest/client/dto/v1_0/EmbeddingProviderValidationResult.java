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

package com.liferay.search.experiences.rest.client.dto.v1_0;

import com.liferay.search.experiences.rest.client.function.UnsafeSupplier;
import com.liferay.search.experiences.rest.client.serdes.v1_0.EmbeddingProviderValidationResultSerDes;

import java.io.Serializable;

import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Brian Wing Shun Chan
 * @generated
 */
@Generated("")
public class EmbeddingProviderValidationResult
	implements Cloneable, Serializable {

	public static EmbeddingProviderValidationResult toDTO(String json) {
		return EmbeddingProviderValidationResultSerDes.toDTO(json);
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public void setErrorMessage(
		UnsafeSupplier<String, Exception> errorMessageUnsafeSupplier) {

		try {
			errorMessage = errorMessageUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String errorMessage;

	public Integer getExpectedDimensions() {
		return expectedDimensions;
	}

	public void setExpectedDimensions(Integer expectedDimensions) {
		this.expectedDimensions = expectedDimensions;
	}

	public void setExpectedDimensions(
		UnsafeSupplier<Integer, Exception> expectedDimensionsUnsafeSupplier) {

		try {
			expectedDimensions = expectedDimensionsUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Integer expectedDimensions;

	@Override
	public EmbeddingProviderValidationResult clone()
		throws CloneNotSupportedException {

		return (EmbeddingProviderValidationResult)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof EmbeddingProviderValidationResult)) {
			return false;
		}

		EmbeddingProviderValidationResult embeddingProviderValidationResult =
			(EmbeddingProviderValidationResult)object;

		return Objects.equals(
			toString(), embeddingProviderValidationResult.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return EmbeddingProviderValidationResultSerDes.toJSON(this);
	}

}