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

import DateTimeInput from '../../../../src/main/resources/META-INF/resources/js/components/inputs/DateTimeInput';
import {testControlledDateInput} from '../../utils';

const DATE_INPUT_TESTID = 'date-input';

describe('DateTimeInput', () => {
	afterEach(cleanup);

	const defaultValue = '2019/01/23';

	const valueDate = '2019-01-23';

	const isoDefaultDate = '2019-01-23T00:00:00.000Z';

	it('renders type date-time', () => {
		const mockOnChange = jest.fn();

		const {asFragment, getByTestId} = render(
			<DateTimeInput
				onChange={mockOnChange}
				propertyLabel="Test label"
				propertyType="date-time"
				value={isoDefaultDate}
			/>
		);

		expect(asFragment()).toMatchSnapshot();

		const element = getByTestId(DATE_INPUT_TESTID);

		testControlledDateInput({
			element,
			mockOnChangeFunc: mockOnChange,
			newValue: '2019/01/24',
			newValueExpected: '2019/01/24',
			newValueOnChange: '2019-01-24T00:00:00.000Z',
			value: defaultValue,
		});
	});

	it('renders previous date with wrong date-time', () => {
		const mockOnChange = jest.fn();

		const {asFragment, getByTestId} = render(
			<DateTimeInput
				onChange={mockOnChange}
				propertyLabel="Test label"
				propertyType="date-time"
				value={isoDefaultDate}
			/>
		);

		expect(asFragment()).toMatchSnapshot();

		const element = getByTestId(DATE_INPUT_TESTID);

		testControlledDateInput({
			element,
			mockOnChangeFunc: mockOnChange,
			newValue: '2019-01-XX',
			newValueExpected: defaultValue,
			newValueOnChange: defaultValue,
			value: defaultValue,
		});
	});

	it('renders type date', () => {
		const mockOnChange = jest.fn();

		const {asFragment, getByTestId} = render(
			<DateTimeInput
				onChange={mockOnChange}
				propertyLabel="Test label"
				propertyType="date"
				value={valueDate}
			/>
		);

		expect(asFragment()).toMatchSnapshot();

		const element = getByTestId(DATE_INPUT_TESTID);

		const expectedDateValueOnChange = '2019-01-24';
		const expectedDateValueDisplayed = '2019/01/24';

		testControlledDateInput({
			element,
			mockOnChangeFunc: mockOnChange,
			newValue: expectedDateValueDisplayed,
			newValueExpected: expectedDateValueDisplayed,
			newValueOnChange: expectedDateValueOnChange,
			value: defaultValue,
		});
	});

	it('renders previous date with wrong date', () => {
		const mockOnChange = jest.fn();

		const {asFragment, getByTestId} = render(
			<DateTimeInput
				onChange={mockOnChange}
				propertyLabel="Test label"
				propertyType="date"
				value={valueDate}
			/>
		);

		expect(asFragment()).toMatchSnapshot();

		const element = getByTestId(DATE_INPUT_TESTID);

		testControlledDateInput({
			element,
			mockOnChangeFunc: mockOnChange,
			newValue: '2019/01/XX',
			newValueExpected: defaultValue,
			newValueOnChange: defaultValue,
			value: defaultValue,
		});
	});

	it('renders date-time with no change in the input', () => {
		const mockOnChange = jest.fn();

		const {asFragment, getByTestId} = render(
			<DateTimeInput
				onChange={mockOnChange}
				propertyLabel="Test label"
				propertyType="date-time"
				value={defaultValue}
			/>
		);

		expect(asFragment()).toMatchSnapshot();

		const element = getByTestId(DATE_INPUT_TESTID);

		testControlledDateInput({
			element,
			inputChange: false,
			mockOnChangeFunc: mockOnChange,
			newValue: defaultValue,
			newValueExpected: defaultValue,
			newValueOnChange: defaultValue,
			value: defaultValue,
		});
	});

	it('renders date with no change in the input', () => {
		const mockOnChange = jest.fn();

		const {asFragment, getByTestId} = render(
			<DateTimeInput
				onChange={mockOnChange}
				propertyLabel="Test label"
				propertyType="date"
				value={valueDate}
			/>
		);

		expect(asFragment()).toMatchSnapshot();

		const element = getByTestId(DATE_INPUT_TESTID);

		testControlledDateInput({
			element,
			inputChange: false,
			mockOnChangeFunc: mockOnChange,
			newValue: defaultValue,
			newValueExpected: defaultValue,
			newValueOnChange: defaultValue,
			value: defaultValue,
		});
	});
});
