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

package com.liferay.item.selector.criteria;

import com.liferay.item.selector.ItemSelectorReturnType;

/**
 * This return type should return the following information of an info item as a
 * JSON object:
 *
 * <ul>
 * <li>
 * <code>assetEntryId</code>: The entryId of the selected asset entry
 * </li>
 * <li>
 * <code>assetType</code>: The type of the selected asset entry
 * </li>
 * <li>
 * <code>className</code>: The class name of the selected asset entry
 * </li>
 * <li>
 * <code>classNameId</code>: The class name ID of the selected asset entry
 * </li>
 * <li>
 * <code>classPK</code>: The class pk of the selected asset entry
 * </li>
 * <li>
 * <code>groupDescriptiveName</code>: The group name of the selected asset entry
 * </li>
 * <li>
 * <code>title</code>: The title of the selected asset entry
 * </li>
 * </ul>
 *
 * @author Adolfo Pérez
 * @review
 */
public class AssetEntryItemSelectorReturnType
	implements ItemSelectorReturnType {
}