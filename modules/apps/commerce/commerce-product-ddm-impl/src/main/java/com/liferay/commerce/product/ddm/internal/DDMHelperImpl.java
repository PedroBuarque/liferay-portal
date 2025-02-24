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

package com.liferay.commerce.product.ddm.internal;

import com.liferay.commerce.constants.CommerceWebKeys;
import com.liferay.commerce.context.CommerceContext;
import com.liferay.commerce.product.constants.CPConstants;
import com.liferay.commerce.product.ddm.DDMHelper;
import com.liferay.commerce.product.model.CPDefinitionOptionRel;
import com.liferay.commerce.product.model.CPDefinitionOptionValueRel;
import com.liferay.commerce.product.service.CPDefinitionOptionValueRelLocalService;
import com.liferay.commerce.product.util.DDMFormValuesHelper;
import com.liferay.commerce.util.CommerceUtil;
import com.liferay.dynamic.data.mapping.form.renderer.DDMFormRenderer;
import com.liferay.dynamic.data.mapping.form.renderer.DDMFormRenderingContext;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldOptions;
import com.liferay.dynamic.data.mapping.model.DDMFormRule;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.taglib.servlet.PipingServletResponseFactory;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 * @author Alessio Antonio Rendina
 * @author Igor Beslic
 */
@Component(service = DDMHelper.class)
public class DDMHelperImpl implements DDMHelper {

	@Override
	public DDMForm getCPAttachmentFileEntryDDMForm(
		Locale locale,
		Map<CPDefinitionOptionRel, List<CPDefinitionOptionValueRel>>
			cpDefinitionOptionRelCPDefinitionOptionValueRels) {

		return _getDDMForm(
			locale, false, true, false,
			cpDefinitionOptionRelCPDefinitionOptionValueRels);
	}

	@Override
	public DDMForm getCPInstanceDDMForm(
		Locale locale, boolean ignoreSKUCombinations,
		Map<CPDefinitionOptionRel, List<CPDefinitionOptionValueRel>>
			cpDefinitionOptionRelCPDefinitionOptionValueRels) {

		return _getDDMForm(
			locale, ignoreSKUCombinations, false, false,
			cpDefinitionOptionRelCPDefinitionOptionValueRels);
	}

	@Override
	public DDMForm getPublicStoreDDMForm(
		long groupId, long commerceAccountId, long cpDefinitionId,
		Locale locale, boolean ignoreSKUCombinations,
		Map<CPDefinitionOptionRel, List<CPDefinitionOptionValueRel>>
			cpDefinitionOptionRelCPDefinitionOptionValueRels,
		long companyId, long userId) {

		DDMForm ddmForm = _getDDMForm(
			locale, ignoreSKUCombinations, false, true,
			cpDefinitionOptionRelCPDefinitionOptionValueRels);

		if (!ignoreSKUCombinations) {
			ddmForm.addDDMFormRule(
				_createDDMFormRule(
					ddmForm, groupId, commerceAccountId, cpDefinitionId,
					companyId, userId, locale));
		}

		return ddmForm;
	}

	@Override
	public String renderCPAttachmentFileEntryOptions(
			long cpDefinitionId, String json, PageContext pageContext,
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse,
			Map<CPDefinitionOptionRel, List<CPDefinitionOptionValueRel>>
				cpDefinitionOptionRelCPDefinitionOptionValueRels)
		throws PortalException {

		Locale locale = _portal.getLocale(httpServletRequest);

		DDMForm ddmForm = getCPAttachmentFileEntryDDMForm(
			locale, cpDefinitionOptionRelCPDefinitionOptionValueRels);

		return _render(
			cpDefinitionId, locale, ddmForm, json, pageContext,
			httpServletRequest, httpServletResponse);
	}

	@Override
	public String renderCPAttachmentFileEntryOptions(
			long cpDefinitionId, String json, RenderRequest renderRequest,
			RenderResponse renderResponse,
			Map<CPDefinitionOptionRel, List<CPDefinitionOptionValueRel>>
				cpDefinitionOptionRelCPDefinitionOptionValueRels)
		throws PortalException {

		throw new UnsupportedOperationException();
	}

	@Override
	public String renderCPInstanceOptions(
			long cpDefinitionId, String json, boolean ignoreSKUCombinations,
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse,
			Map<CPDefinitionOptionRel, List<CPDefinitionOptionValueRel>>
				cpDefinitionOptionRelCPDefinitionOptionValueRels)
		throws PortalException {

		Locale locale = _portal.getLocale(httpServletRequest);

		DDMForm ddmForm = getCPInstanceDDMForm(
			locale, ignoreSKUCombinations,
			cpDefinitionOptionRelCPDefinitionOptionValueRels);

		return _render(
			cpDefinitionId, locale, ddmForm, json, null, httpServletRequest,
			httpServletResponse);
	}

	@Override
	public String renderPublicStoreOptions(
			long cpDefinitionId, String json, boolean ignoreSKUCombinations,
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse,
			Map<CPDefinitionOptionRel, List<CPDefinitionOptionValueRel>>
				cpDefinitionOptionRelCPDefinitionOptionValueRels)
		throws PortalException {

		Locale locale = _portal.getLocale(httpServletRequest);

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		DDMForm ddmForm = getPublicStoreDDMForm(
			_portal.getScopeGroupId(httpServletRequest),
			CommerceUtil.getCommerceAccountId(
				(CommerceContext)httpServletRequest.getAttribute(
					CommerceWebKeys.COMMERCE_CONTEXT)),
			cpDefinitionId, locale, ignoreSKUCombinations,
			cpDefinitionOptionRelCPDefinitionOptionValueRels,
			themeDisplay.getCompanyId(), themeDisplay.getUserId());

		return _render(
			cpDefinitionId, locale, ddmForm, json, null, httpServletRequest,
			httpServletResponse);
	}

	private DDMFormRule _createDDMFormRule(
		DDMForm ddmForm, long groupId, long commerceAccountId,
		long cpDefinitionId, long companyId, long userId, Locale locale) {

		String action = _createDDMFormRuleAction(
			ddmForm, groupId, commerceAccountId, cpDefinitionId, companyId,
			userId, locale);

		return new DDMFormRule("TRUE", action);
	}

	/**
	 * Create a DDM form rule action as a call function, e.g.
	 * <pre>
	 * call(
	 * 	'getCPInstanceOptionsValues',
	 * 	concat(
	 * 		'cpDefinitionId=56698', ';', '56703=', getValue('56703'), ';',
	 * 		'56706=', getValue('56706')),
	 * 	'56703=color;56706=size')
	 * </pre>
	 */
	private String _createDDMFormRuleAction(
		DDMForm ddmForm, long groupId, long commerceAccountId,
		long cpDefinitionId, long companyId, long userId, Locale locale) {

		return StringBundler.concat(
			"call('getCPInstanceOptionsValues', concat(",
			_createDDMFormRuleInputMapping(
				ddmForm, groupId, commerceAccountId, cpDefinitionId, companyId,
				userId, locale),
			"), '", _createDDMFormRuleOutputMapping(ddmForm), "')");
	}

	private String _createDDMFormRuleInputMapping(
		DDMForm ddmForm, long groupId, long commerceAccountId,
		long cpDefinitionId, long companyId, long userId, Locale locale) {

		// The input information will be transformed in parameter request of
		// DDMDataProviderRequest class and it'll be accessible in the data
		// provider implementation.

		List<DDMFormField> ddmFormFields = ddmForm.getDDMFormFields();

		StringBundler sb = new StringBundler((ddmFormFields.size() * 5) + 13);

		sb.append("'locale=");
		sb.append(LocaleUtil.toLanguageId(locale));
		sb.append("', ';','userId=");
		sb.append(userId);
		sb.append("', ';','commerceAccountId=");
		sb.append(commerceAccountId);
		sb.append("', ';','groupId=");
		sb.append(groupId);
		sb.append("', ';','cpDefinitionId=");
		sb.append(cpDefinitionId);
		sb.append("', ';','companyId=");
		sb.append(companyId);
		sb.append("'");

		for (DDMFormField ddmFormField : ddmFormFields) {
			sb.append(", ';','");
			sb.append(ddmFormField.getName());
			sb.append("=', getValue('");
			sb.append(ddmFormField.getName());
			sb.append("')");
		}

		return sb.toString();
	}

	private String _createDDMFormRuleOutputMapping(DDMForm ddmForm) {
		List<DDMFormField> ddmFormFields = ddmForm.getDDMFormFields();

		StringBundler sb = new StringBundler(ddmFormFields.size() * 4);

		for (DDMFormField ddmFormField : ddmFormFields) {
			sb.append(ddmFormField.getName());
			sb.append(StringPool.EQUAL);
			sb.append(ddmFormField.getName());
			sb.append(StringPool.SEMICOLON);
		}

		if (sb.index() > 0) {
			sb.setIndex(sb.index() - 1);
		}

		return sb.toString();
	}

	private DDMForm _getDDMForm(
		Locale locale, boolean ignoreSKUCombinations, boolean optional,
		boolean publicStore,
		Map<CPDefinitionOptionRel, List<CPDefinitionOptionValueRel>>
			cpDefinitionOptionRelCPDefinitionOptionValueRels) {

		if (cpDefinitionOptionRelCPDefinitionOptionValueRels.isEmpty()) {
			return null;
		}

		DDMForm ddmForm = new DDMForm();

		for (Map.Entry<CPDefinitionOptionRel, List<CPDefinitionOptionValueRel>>
				cpDefinitionOptionRelEntry :
					cpDefinitionOptionRelCPDefinitionOptionValueRels.
						entrySet()) {

			CPDefinitionOptionRel cpDefinitionOptionRel =
				cpDefinitionOptionRelEntry.getKey();

			if (Validator.isNull(
					cpDefinitionOptionRel.getDDMFormFieldTypeName())) {

				continue;
			}

			List<CPDefinitionOptionValueRel> cpDefinitionOptionValueRels =
				cpDefinitionOptionRelEntry.getValue();

			DDMFormField ddmFormField = _getDDMFormField(
				cpDefinitionOptionRel, cpDefinitionOptionValueRels, locale);

			if (publicStore) {
				_setPredefinedValue(ddmFormField, cpDefinitionOptionRel);

				if (!optional) {
					ddmFormField.setRequired(
						_isDDMFormFieldRequired(
							cpDefinitionOptionRel, ignoreSKUCombinations,
							publicStore));
				}
			}
			else {
				ddmFormField.setRequired(!optional);
			}

			ddmForm.addDDMFormField(ddmFormField);
		}

		ddmForm.addAvailableLocale(locale);
		ddmForm.setDefaultLocale(locale);

		return ddmForm;
	}

	private DDMFormField _getDDMFormField(
		CPDefinitionOptionRel cpDefinitionOptionRel,
		List<CPDefinitionOptionValueRel> cpDefinitionOptionValueRels,
		Locale locale) {

		DDMFormField ddmFormField = new DDMFormField(
			cpDefinitionOptionRel.getKey(),
			cpDefinitionOptionRel.getDDMFormFieldTypeName());

		LocalizedValue ddmFormFieldLabelLocalizedValue = new LocalizedValue(
			locale);

		ddmFormFieldLabelLocalizedValue.addString(
			locale, cpDefinitionOptionRel.getName(locale));

		ddmFormField.setLabel(ddmFormFieldLabelLocalizedValue);

		if (cpDefinitionOptionValueRels.isEmpty()) {
			return ddmFormField;
		}

		ddmFormField.setDDMFormFieldOptions(
			_getDDMFormFieldOptions(cpDefinitionOptionValueRels, locale));

		return ddmFormField;
	}

	private DDMFormFieldOptions _getDDMFormFieldOptions(
		List<CPDefinitionOptionValueRel> cpDefinitionOptionValueRels,
		Locale locale) {

		DDMFormFieldOptions ddmFormFieldOptions = new DDMFormFieldOptions();

		for (CPDefinitionOptionValueRel cpDefinitionOptionValueRel :
				cpDefinitionOptionValueRels) {

			ddmFormFieldOptions.addOptionLabel(
				cpDefinitionOptionValueRel.getKey(), locale,
				cpDefinitionOptionValueRel.getName(locale));
		}

		return ddmFormFieldOptions;
	}

	private LocalizedValue _getDDMFormFieldPredefinedValue(
		DDMFormFieldOptions ddmFormFieldOptions, boolean arrayValueFieldType,
		String optionValueKey) {

		Map<String, LocalizedValue> options = ddmFormFieldOptions.getOptions();

		if (options.isEmpty()) {
			return new LocalizedValue(ddmFormFieldOptions.getDefaultLocale());
		}

		for (Map.Entry<String, LocalizedValue> entry : options.entrySet()) {
			LocalizedValue localizedValue = new LocalizedValue();

			LocalizedValue curLocalizedValue = entry.getValue();

			if (arrayValueFieldType) {
				localizedValue.addString(
					curLocalizedValue.getDefaultLocale(),
					"[\"" + entry.getKey() + "\"]");
			}
			else {
				localizedValue.addString(
					curLocalizedValue.getDefaultLocale(), entry.getKey());
			}

			if (Validator.isNull(optionValueKey) ||
				Objects.equals(optionValueKey, entry.getKey())) {

				return localizedValue;
			}
		}

		throw new IllegalArgumentException(
			"Provided DDM field options miss valid field value");
	}

	private boolean _isArrayValueCPDefinitionOptionRelFieldType(
		CPDefinitionOptionRel cpDefinitionOptionRel) {

		if (ArrayUtil.contains(
				_ARRAY_VALUE_FIELD_TYPE,
				cpDefinitionOptionRel.getDDMFormFieldTypeName())) {

			return true;
		}

		return false;
	}

	private boolean _isDDMFormFieldRequired(
		CPDefinitionOptionRel cpDefinitionOptionRel,
		boolean ignoreSKUCombinations, boolean publicStore) {

		if (_isIterableCPDefinitionOptionRelFieldType(cpDefinitionOptionRel) &&
			!_cpDefinitionOptionValueRelLocalService.
				hasCPDefinitionOptionValueRels(
					cpDefinitionOptionRel.getCPDefinitionOptionRelId())) {

			return false;
		}

		if (ignoreSKUCombinations) {
			return cpDefinitionOptionRel.isRequired();
		}

		if (cpDefinitionOptionRel.isSkuContributor() ||
			(publicStore && cpDefinitionOptionRel.isRequired())) {

			return true;
		}

		return false;
	}

	private boolean _isIterableCPDefinitionOptionRelFieldType(
		CPDefinitionOptionRel cpDefinitionOptionRel) {

		if (ArrayUtil.contains(
				CPConstants.PRODUCT_OPTION_MULTIPLE_VALUES_FIELD_TYPES,
				cpDefinitionOptionRel.getDDMFormFieldTypeName())) {

			return true;
		}

		return false;
	}

	private String _render(
			long cpDefinitionId, Locale locale, DDMForm ddmForm, String json,
			PageContext pageContext, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws PortalException {

		if (ddmForm == null) {
			return StringPool.BLANK;
		}

		if (pageContext != null) {
			httpServletResponse =
				PipingServletResponseFactory.createPipingServletResponse(
					pageContext);
		}

		DDMFormRenderingContext ddmFormRenderingContext =
			new DDMFormRenderingContext();

		ddmFormRenderingContext.setContainerId(
			"ProductOptions" + String.valueOf(cpDefinitionId));
		ddmFormRenderingContext.setHttpServletRequest(httpServletRequest);
		ddmFormRenderingContext.setHttpServletResponse(httpServletResponse);
		ddmFormRenderingContext.setLocale(locale);

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		ddmFormRenderingContext.setPortletNamespace(
			portletDisplay.getNamespace());

		ddmFormRenderingContext.setShowRequiredFieldsWarning(false);

		if (Validator.isNotNull(json)) {
			DDMFormValues ddmFormValues = _ddmFormValuesHelper.deserialize(
				ddmForm, json, locale);

			if (ddmFormValues != null) {
				ddmFormRenderingContext.setDDMFormValues(ddmFormValues);
			}
		}

		return _ddmFormRenderer.render(ddmForm, ddmFormRenderingContext);
	}

	private void _setPredefinedValue(
		DDMFormField ddmFormField,
		CPDefinitionOptionRel cpDefinitionOptionRel) {

		CPDefinitionOptionValueRel preselectedCPDefinitionOptionValueRel =
			cpDefinitionOptionRel.fetchPreselectedCPDefinitionOptionValueRel();

		String predefinedValueKey = null;

		if (preselectedCPDefinitionOptionValueRel != null) {
			predefinedValueKey = preselectedCPDefinitionOptionValueRel.getKey();
		}

		if (Validator.isNull(predefinedValueKey) &&
			!cpDefinitionOptionRel.isSkuContributor()) {

			return;
		}

		ddmFormField.setPredefinedValue(
			_getDDMFormFieldPredefinedValue(
				ddmFormField.getDDMFormFieldOptions(),
				_isArrayValueCPDefinitionOptionRelFieldType(
					cpDefinitionOptionRel),
				predefinedValueKey));
	}

	private static final String[] _ARRAY_VALUE_FIELD_TYPE = {
		"select", "checkbox", "checkbox_multiple"
	};

	@Reference
	private CPDefinitionOptionValueRelLocalService
		_cpDefinitionOptionValueRelLocalService;

	@Reference
	private DDMFormRenderer _ddmFormRenderer;

	@Reference
	private DDMFormValuesHelper _ddmFormValuesHelper;

	@Reference
	private Portal _portal;

}