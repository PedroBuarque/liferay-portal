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

package com.liferay.commerce.model;

import com.liferay.portal.kernel.bean.AutoEscape;
import com.liferay.portal.kernel.model.AttachedModel;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.GroupedModel;
import com.liferay.portal.kernel.model.MVCCModel;
import com.liferay.portal.kernel.model.ShardedModel;

import java.util.Date;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The base model interface for the CommerceAddressRestriction service. Represents a row in the &quot;CommerceAddressRestriction&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This interface and its corresponding implementation <code>com.liferay.commerce.model.impl.CommerceAddressRestrictionModelImpl</code> exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in <code>com.liferay.commerce.model.impl.CommerceAddressRestrictionImpl</code>.
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CommerceAddressRestriction
 * @generated
 */
@ProviderType
public interface CommerceAddressRestrictionModel
	extends AttachedModel, BaseModel<CommerceAddressRestriction>, GroupedModel,
			MVCCModel, ShardedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. All methods that expect a commerce address restriction model instance should use the {@link CommerceAddressRestriction} interface instead.
	 */

	/**
	 * Returns the primary key of this commerce address restriction.
	 *
	 * @return the primary key of this commerce address restriction
	 */
	public long getPrimaryKey();

	/**
	 * Sets the primary key of this commerce address restriction.
	 *
	 * @param primaryKey the primary key of this commerce address restriction
	 */
	public void setPrimaryKey(long primaryKey);

	/**
	 * Returns the mvcc version of this commerce address restriction.
	 *
	 * @return the mvcc version of this commerce address restriction
	 */
	@Override
	public long getMvccVersion();

	/**
	 * Sets the mvcc version of this commerce address restriction.
	 *
	 * @param mvccVersion the mvcc version of this commerce address restriction
	 */
	@Override
	public void setMvccVersion(long mvccVersion);

	/**
	 * Returns the commerce address restriction ID of this commerce address restriction.
	 *
	 * @return the commerce address restriction ID of this commerce address restriction
	 */
	public long getCommerceAddressRestrictionId();

	/**
	 * Sets the commerce address restriction ID of this commerce address restriction.
	 *
	 * @param commerceAddressRestrictionId the commerce address restriction ID of this commerce address restriction
	 */
	public void setCommerceAddressRestrictionId(
		long commerceAddressRestrictionId);

	/**
	 * Returns the group ID of this commerce address restriction.
	 *
	 * @return the group ID of this commerce address restriction
	 */
	@Override
	public long getGroupId();

	/**
	 * Sets the group ID of this commerce address restriction.
	 *
	 * @param groupId the group ID of this commerce address restriction
	 */
	@Override
	public void setGroupId(long groupId);

	/**
	 * Returns the company ID of this commerce address restriction.
	 *
	 * @return the company ID of this commerce address restriction
	 */
	@Override
	public long getCompanyId();

	/**
	 * Sets the company ID of this commerce address restriction.
	 *
	 * @param companyId the company ID of this commerce address restriction
	 */
	@Override
	public void setCompanyId(long companyId);

	/**
	 * Returns the user ID of this commerce address restriction.
	 *
	 * @return the user ID of this commerce address restriction
	 */
	@Override
	public long getUserId();

	/**
	 * Sets the user ID of this commerce address restriction.
	 *
	 * @param userId the user ID of this commerce address restriction
	 */
	@Override
	public void setUserId(long userId);

	/**
	 * Returns the user uuid of this commerce address restriction.
	 *
	 * @return the user uuid of this commerce address restriction
	 */
	@Override
	public String getUserUuid();

	/**
	 * Sets the user uuid of this commerce address restriction.
	 *
	 * @param userUuid the user uuid of this commerce address restriction
	 */
	@Override
	public void setUserUuid(String userUuid);

	/**
	 * Returns the user name of this commerce address restriction.
	 *
	 * @return the user name of this commerce address restriction
	 */
	@AutoEscape
	@Override
	public String getUserName();

	/**
	 * Sets the user name of this commerce address restriction.
	 *
	 * @param userName the user name of this commerce address restriction
	 */
	@Override
	public void setUserName(String userName);

	/**
	 * Returns the create date of this commerce address restriction.
	 *
	 * @return the create date of this commerce address restriction
	 */
	@Override
	public Date getCreateDate();

	/**
	 * Sets the create date of this commerce address restriction.
	 *
	 * @param createDate the create date of this commerce address restriction
	 */
	@Override
	public void setCreateDate(Date createDate);

	/**
	 * Returns the modified date of this commerce address restriction.
	 *
	 * @return the modified date of this commerce address restriction
	 */
	@Override
	public Date getModifiedDate();

	/**
	 * Sets the modified date of this commerce address restriction.
	 *
	 * @param modifiedDate the modified date of this commerce address restriction
	 */
	@Override
	public void setModifiedDate(Date modifiedDate);

	/**
	 * Returns the fully qualified class name of this commerce address restriction.
	 *
	 * @return the fully qualified class name of this commerce address restriction
	 */
	@Override
	public String getClassName();

	public void setClassName(String className);

	/**
	 * Returns the class name ID of this commerce address restriction.
	 *
	 * @return the class name ID of this commerce address restriction
	 */
	@Override
	public long getClassNameId();

	/**
	 * Sets the class name ID of this commerce address restriction.
	 *
	 * @param classNameId the class name ID of this commerce address restriction
	 */
	@Override
	public void setClassNameId(long classNameId);

	/**
	 * Returns the class pk of this commerce address restriction.
	 *
	 * @return the class pk of this commerce address restriction
	 */
	@Override
	public long getClassPK();

	/**
	 * Sets the class pk of this commerce address restriction.
	 *
	 * @param classPK the class pk of this commerce address restriction
	 */
	@Override
	public void setClassPK(long classPK);

	/**
	 * Returns the country ID of this commerce address restriction.
	 *
	 * @return the country ID of this commerce address restriction
	 */
	public long getCountryId();

	/**
	 * Sets the country ID of this commerce address restriction.
	 *
	 * @param countryId the country ID of this commerce address restriction
	 */
	public void setCountryId(long countryId);

	@Override
	public CommerceAddressRestriction cloneWithOriginalValues();

	public default String toXmlString() {
		return null;
	}

}