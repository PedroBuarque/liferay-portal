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

import {cleanup, render} from '@testing-library/react';
import React from 'react';

import EmptyAuditBarChart from '../../../src/main/resources/META-INF/resources/js/components/AuditGraphApp/EmptyAuditBarChart';

import '@testing-library/jest-dom/extend-expect';

const LEARN_HOW_URL =
	'https://help.liferay.com/hc/en-us/articles/360028820492-Defining-Categories-for-Content';

describe('EmptyAuditBarChart', () => {
	Liferay.Util.sub.mockImplementation((langKey) => langKey);
	const {ResizeObserver} = window;

	beforeAll(() => {
		delete window.ResizeObserver;
		window.ResizeObserver = jest.fn().mockImplementation(() => ({
			disconnect: jest.fn(),
			observe: jest.fn(),
			unobserve: jest.fn(),
		}));
	});

	afterEach(() => {
		cleanup();
		window.ResizeObserver = ResizeObserver;
		jest.restoreAllMocks();
	});

	it('renders empty bar chart if there is no content labeled with categories', () => {
		const {getByText} = render(
			<EmptyAuditBarChart
				learnHowLink={{
					message: 'Learn how to tailor categories to your needs.',
					url: LEARN_HOW_URL,
				}}
			/>
		);

		expect(getByText('there-is-no-data')).toBeInTheDocument();
		expect(
			getByText('Learn how to tailor categories to your needs.')
		).toBeInTheDocument();
	});
});
