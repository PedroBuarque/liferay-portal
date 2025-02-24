<%--
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
--%>

<%@ include file="/dynamic_include/init.jsp" %>

<aui:script sandbox="<%= true %>">
	var onVote = function (event) {
		if (window.Analytics) {
			let title = event.contentTitle;

			if (!title) {
				const dmNode = document.querySelector(
					'[data-analytics-file-entry-id="' + event.classPK + '"]'
				);

				if (dmNode) {
					title = dmNode.dataset.analyticsFileEntryTitle;
				}
			}

			Analytics.send('VOTE', 'Ratings', {
				className: event.className,
				classPK: event.classPK,
				ratingType: event.ratingType,
				score: event.score,
				title,
			});
		}
	};

	var onDestroyPortlet = function () {
		Liferay.detach('ratings:vote', onVote);
		Liferay.detach('destroyPortlet', onDestroyPortlet);
	};

	Liferay.on('ratings:vote', onVote);
	Liferay.on('destroyPortlet', onDestroyPortlet);
</aui:script>