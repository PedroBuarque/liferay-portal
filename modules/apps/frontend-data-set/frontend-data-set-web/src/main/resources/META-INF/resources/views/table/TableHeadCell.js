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

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import classNames from 'classnames';
import PropTypes from 'prop-types';
import React, {useContext, useEffect, useState} from 'react';

import ViewsContext from '../ViewsContext';
import {VIEWS_ACTION_TYPES} from '../viewsReducer';
import Cell from './dnd_table/Cell';

function TableHeadCell({
	contentRenderer,
	fieldName,
	hideColumnLabel,
	label,
	sortable,
	sortingKey: sortingKeyProp,
}) {
	const [{sorting}, viewsDispatch] = useContext(ViewsContext);

	const [sortingKey, setSortingKey] = useState(null);
	const [sortingMatch, setSortingMatch] = useState(null);

	useEffect(() => {
		const newSortingKey =
			sortingKeyProp ||
			(Array.isArray(fieldName) ? fieldName[0] : fieldName);

		setSortingKey(newSortingKey);
		setSortingMatch(
			sorting.find((element) => element.key === newSortingKey)
		);
	}, [fieldName, sorting, sortingKeyProp]);

	function handleSortingCellClick(event) {
		event.preventDefault();

		const updatedSortedElements = sortingMatch
			? sorting.map((element) =>
					element.key === sortingKey
						? {
								...element,
								direction:
									element.direction === 'asc'
										? 'desc'
										: 'asc',
						  }
						: element
			  )
			: [
					{
						direction: 'asc',
						fieldName,
						key: sortingKey,
					},
			  ];

		viewsDispatch({
			type: VIEWS_ACTION_TYPES.UPDATE_SORTING,
			value: updatedSortedElements,
		});
	}

	return (
		<Cell
			className={classNames({
				[`content-renderer-${contentRenderer}`]: contentRenderer,
			})}
			columnName={String(fieldName)}
			heading
			resizable
		>
			{sortable ? (
				<ClayButton
					className="btn-sorting inline-item text-nowrap text-truncate-inline"
					displayType="unstyled"
					onClick={handleSortingCellClick}
					small
				>
					{!hideColumnLabel && label}

					<span className="inline-item inline-item-after sorting-icons-wrapper">
						<ClayIcon
							className={classNames(
								'sorting-icon',
								sortingMatch?.direction === 'asc' && 'active'
							)}
							draggable
							symbol="order-arrow-up"
						/>

						<ClayIcon
							className={classNames(
								'sorting-icon',
								sortingMatch?.direction === 'desc' && 'active'
							)}
							draggable
							symbol="order-arrow-down"
						/>
					</span>
				</ClayButton>
			) : (
				!hideColumnLabel && label
			)}
		</Cell>
	);
}

TableHeadCell.proptypes = {
	contentRenderer: PropTypes.string,
	fieldName: PropTypes.oneOfType([
		PropTypes.string,
		PropTypes.arrayOf(PropTypes.string),
	]),
	hideColumnLabel: PropTypes.bool,
	label: PropTypes.string,
	sortable: PropTypes.bool,
	sortingKey: PropTypes.string,
};

export default TableHeadCell;
