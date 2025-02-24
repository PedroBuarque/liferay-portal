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

package com.liferay.calendar.search.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.calendar.model.CalendarResource;
import com.liferay.calendar.service.CalendarLocalService;
import com.liferay.calendar.util.CalendarResourceUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.BaseSearcher;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchEngine;
import com.liferay.portal.kernel.search.SearchEngineHelperUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DataGuard;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.SearchContextTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.search.test.util.SearchTestRule;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import org.junit.Assert;
import org.junit.Assume;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Inácio Nery
 */
@DataGuard(scope = DataGuard.Scope.METHOD)
@RunWith(Arquillian.class)
public class CalendarSearcherTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
		_user = UserTestUtil.addUser();

		_searchContext = getSearchContext(_group);
	}

	@Test
	public void testBasicSearchWithOneTerm() throws Exception {
		addCalendar("Joe Bloggs", "Simple description");
		addCalendar("Bloggs", "Another description example");
		addCalendar(
			RandomTestUtil.randomString(), RandomTestUtil.randomString());

		assertSearch("example", 1);
		assertSearch("description", 2);
	}

	@Test
	public void testExactPhrase() throws Exception {
		addCalendar("Joe Bloggs", "Simple description");
		addCalendar("Bloggs", "Another description example");
		addCalendar(
			RandomTestUtil.randomString(), RandomTestUtil.randomString());

		assertSearch("\"Joe Bloggs\"", 1);
		assertSearch("Bloggs", 2);
	}

	@Test
	public void testExactPhraseMixedWithWords() throws Exception {
		addCalendar(
			"One Two Three Four Five Six", RandomTestUtil.randomString());
		addCalendar(
			RandomTestUtil.randomString(), RandomTestUtil.randomString());

		assertSearch("\"Two Three\" Five", 1);
		assertSearch("\"Two Three\" Nine", 0);
		assertSearch("\"Two  Four\" Five", 0);
		assertSearch("\"Two  Four\" Nine", 0);
		assertSearch("Three \"Five Six\"", 1);
		assertSearch("Zero  \"Five Six\"", 0);
		assertSearch("Three \"Four Six\"", 0);
		assertSearch("Zero  \"Four Six\"", 0);
		assertSearch("One  \"Three Four\" Six ", 1);
		assertSearch("Zero \"Three Four\" Nine", 0);
		assertSearch("One  \"Three Five\" Six ", 0);
		assertSearch("Zero \"Three Five\" Nine", 0);
	}

	@Test
	public void testPunctuationInExactPhrase() throws Exception {
		addCalendar("Joe? Bloggs!");
		addCalendar("Joe! Bloggs?");
		addCalendar("Joe Bloggs");
		addCalendar("Bloggs");

		assertSearch("\"Joe? Bloggs!\"", 3);
		assertSearch("\"Joe! Bloggs?\"", 3);
	}

	@Test
	public void testQuestionMarksVersusStopWords1() throws Exception {
		addCalendar(RandomTestUtil.randomString());
		addCalendar("how ? create ? coupon");

		assertSearch("\"how ? create ? coupon\"", 1);
		assertSearch("\"how to create a coupon\"", 0);
		assertSearch("\"how with create the coupon\"", 0);
	}

	@Test
	public void testQuestionMarksVersusStopWords2() throws Exception {
		Assume.assumeTrue(isExactPhraseQueryImplementedForSearchEngine());

		addCalendar(RandomTestUtil.randomString());
		addCalendar("how with create the coupon");

		assertSearch("\"how ? create ? coupon\"", 0);
		assertSearch("\"how to create a coupon\"", 1);
		assertSearch("\"how with create the coupon\"", 1);
	}

	@Test
	public void testQuestionMarksVersusStopWords3() throws Exception {
		Assume.assumeTrue(isExactPhraseQueryImplementedForSearchEngine());

		addCalendar(RandomTestUtil.randomString());
		addCalendar("how to create a coupon");

		assertSearch("\"how ? create ? coupon\"", 0);
		assertSearch("\"how to create a coupon\"", 1);
		assertSearch("\"how with create the coupon\"", 1);
	}

	@Test
	public void testQuestionMarksVersusStopWords4() throws Exception {
		addCalendar(RandomTestUtil.randomString());
		addCalendar("how ! create ! coupon");

		assertSearch("\"how ? create ? coupon\"", 1);
		assertSearch("\"how to create a coupon\"", 0);
		assertSearch("\"how with create the coupon\"", 0);
	}

	@Test
	public void testStopWords() throws Exception {
		addCalendar(RandomTestUtil.randomString());
		addCalendar(
			RandomTestUtil.randomString(), "Another description example");

		assertSearch("Another The Example", 1);
	}

	@Test
	public void testStopWordsInExactPhrase() throws Exception {
		Assume.assumeTrue(isExactPhraseQueryImplementedForSearchEngine());

		addCalendar("how to create a coupon");
		addCalendar("Joe Of Bloggs");
		addCalendar("Joe Bloggs");
		addCalendar("Bloggs");

		assertSearch("\"how to create a coupon\"", 1);
		assertSearch("\"how with create the coupon\"", 1);
		assertSearch("\"how Liferay create Liferay coupon\"", 0);
		assertSearch("\"how create coupon\"", 0);
		assertSearch("\"Joe Of Bloggs\"", 1);
		assertSearch("\"Joe The Bloggs\"", 1);
		assertSearch("\"Bloggs A\"", 3);
		assertSearch("\"Of Bloggs\"", 3);
		assertSearch("\"The Bloggs\"", 3);
	}

	@Rule
	public SearchTestRule searchTestRule = new SearchTestRule();

	protected static SearchContext getSearchContext(Group group)
		throws Exception {

		return SearchContextTestUtil.getSearchContext(group.getGroupId());
	}

	protected void addCalendar(String name) throws PortalException {
		addCalendar(name, RandomTestUtil.randomString());
	}

	protected void addCalendar(String name, String description)
		throws PortalException {

		ServiceContext serviceContext = new ServiceContext();

		CalendarResource calendarResource =
			CalendarResourceUtil.getGroupCalendarResource(
				_group.getGroupId(), serviceContext);

		_calendarLocalService.addCalendar(
			_user.getUserId(), _group.getGroupId(),
			calendarResource.getCalendarResourceId(),
			HashMapBuilder.put(
				LocaleUtil.getSiteDefault(), name
			).build(),
			HashMapBuilder.put(
				LocaleUtil.getSiteDefault(), description
			).build(),
			StringPool.UTC, RandomTestUtil.randomInt(0, 255), false, false,
			false, serviceContext);
	}

	protected void assertSearch(String keywords, int length) throws Exception {
		_searchContext.setKeywords(StringUtil.toLowerCase(keywords));

		Hits hits = _baseSearcher.search(_searchContext);

		Assert.assertEquals(hits.toString(), length, hits.getLength());
	}

	protected boolean isExactPhraseQueryImplementedForSearchEngine() {
		SearchEngine searchEngine = SearchEngineHelperUtil.getSearchEngine();

		String vendor = searchEngine.getVendor();

		if (vendor.equals("Elasticsearch") || vendor.equals("Solr")) {
			return false;
		}

		return true;
	}

	@Inject(filter = "model.class.name=com.liferay.calendar.model.Calendar")
	private BaseSearcher _baseSearcher;

	@Inject
	private CalendarLocalService _calendarLocalService;

	private Group _group;
	private SearchContext _searchContext;
	private User _user;

}