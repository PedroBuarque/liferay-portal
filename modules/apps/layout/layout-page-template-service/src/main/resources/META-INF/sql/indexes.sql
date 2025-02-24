create index IX_E7DECBD1 on LayoutPageTemplateCollection (groupId, ctCollectionId);
create unique index IX_8AA52116 on LayoutPageTemplateCollection (groupId, lptCollectionKey[$COLUMN_LENGTH:75$], ctCollectionId);
create unique index IX_AA989B50 on LayoutPageTemplateCollection (groupId, name[$COLUMN_LENGTH:75$], ctCollectionId);
create index IX_20C22DA9 on LayoutPageTemplateCollection (uuid_[$COLUMN_LENGTH:75$], companyId, ctCollectionId);
create index IX_3076CB1B on LayoutPageTemplateCollection (uuid_[$COLUMN_LENGTH:75$], ctCollectionId);
create unique index IX_12B4C6EB on LayoutPageTemplateCollection (uuid_[$COLUMN_LENGTH:75$], groupId, ctCollectionId);

create index IX_C4FE9CD5 on LayoutPageTemplateEntry (groupId, classNameId, classTypeId, defaultTemplate, ctCollectionId);
create index IX_4447E8BB on LayoutPageTemplateEntry (groupId, classNameId, classTypeId, defaultTemplate, status, ctCollectionId);
create index IX_67D695C0 on LayoutPageTemplateEntry (groupId, classNameId, classTypeId, name[$COLUMN_LENGTH:75$], type_, ctCollectionId);
create index IX_8679C6A6 on LayoutPageTemplateEntry (groupId, classNameId, classTypeId, name[$COLUMN_LENGTH:75$], type_, status, ctCollectionId);
create index IX_E86F3D5F on LayoutPageTemplateEntry (groupId, classNameId, classTypeId, type_, ctCollectionId);
create index IX_A9FC5F45 on LayoutPageTemplateEntry (groupId, classNameId, classTypeId, type_, status, ctCollectionId);
create index IX_807E573D on LayoutPageTemplateEntry (groupId, classNameId, type_, defaultTemplate, ctCollectionId);
create index IX_7475C703 on LayoutPageTemplateEntry (groupId, ctCollectionId);
create index IX_B42D81E3 on LayoutPageTemplateEntry (groupId, layoutPageTemplateCollectionId, ctCollectionId);
create index IX_EAB6FBE2 on LayoutPageTemplateEntry (groupId, layoutPageTemplateCollectionId, name[$COLUMN_LENGTH:75$], ctCollectionId);
create index IX_1EAAEAC8 on LayoutPageTemplateEntry (groupId, layoutPageTemplateCollectionId, name[$COLUMN_LENGTH:75$], status, ctCollectionId);
create index IX_787FDFC9 on LayoutPageTemplateEntry (groupId, layoutPageTemplateCollectionId, status, ctCollectionId);
create index IX_8FF63B0E on LayoutPageTemplateEntry (groupId, layoutPageTemplateCollectionId, type_, ctCollectionId);
create unique index IX_ECAFF217 on LayoutPageTemplateEntry (groupId, layoutPageTemplateEntryKey[$COLUMN_LENGTH:75$], ctCollectionId);
create index IX_FFBEE902 on LayoutPageTemplateEntry (groupId, name[$COLUMN_LENGTH:75$], ctCollectionId);
create unique index IX_7C97630F on LayoutPageTemplateEntry (groupId, name[$COLUMN_LENGTH:75$], type_, ctCollectionId);
create index IX_693BD4F5 on LayoutPageTemplateEntry (groupId, name[$COLUMN_LENGTH:75$], type_, status, ctCollectionId);
create index IX_1BEBF1EE on LayoutPageTemplateEntry (groupId, type_, ctCollectionId);
create index IX_A3189AFF on LayoutPageTemplateEntry (groupId, type_, defaultTemplate, status, ctCollectionId);
create index IX_91BF14D4 on LayoutPageTemplateEntry (groupId, type_, status, ctCollectionId);
create index IX_9615FDC on LayoutPageTemplateEntry (layoutPrototypeId, ctCollectionId);
create unique index IX_87D3788E on LayoutPageTemplateEntry (plid, ctCollectionId);
create index IX_152DAAB7 on LayoutPageTemplateEntry (uuid_[$COLUMN_LENGTH:75$], companyId, ctCollectionId);
create index IX_4496EACD on LayoutPageTemplateEntry (uuid_[$COLUMN_LENGTH:75$], ctCollectionId);
create unique index IX_62582F79 on LayoutPageTemplateEntry (uuid_[$COLUMN_LENGTH:75$], groupId, ctCollectionId);

create index IX_7443F7E2 on LayoutPageTemplateStructure (groupId, ctCollectionId);
create unique index IX_D822FD2D on LayoutPageTemplateStructure (groupId, plid, ctCollectionId);
create index IX_8E4F04B8 on LayoutPageTemplateStructure (uuid_[$COLUMN_LENGTH:75$], companyId, ctCollectionId);
create index IX_3DA9C76C on LayoutPageTemplateStructure (uuid_[$COLUMN_LENGTH:75$], ctCollectionId);
create unique index IX_545A15BA on LayoutPageTemplateStructure (uuid_[$COLUMN_LENGTH:75$], groupId, ctCollectionId);

create index IX_CC9B240A on LayoutPageTemplateStructureRel (layoutPageTemplateStructureId, ctCollectionId);
create unique index IX_843407A3 on LayoutPageTemplateStructureRel (layoutPageTemplateStructureId, segmentsExperienceId, ctCollectionId);
create index IX_34E1EF96 on LayoutPageTemplateStructureRel (segmentsExperienceId, ctCollectionId);
create index IX_CA98471 on LayoutPageTemplateStructureRel (uuid_[$COLUMN_LENGTH:75$], companyId, ctCollectionId);
create index IX_6AC1E153 on LayoutPageTemplateStructureRel (uuid_[$COLUMN_LENGTH:75$], ctCollectionId);
create unique index IX_FC932FB3 on LayoutPageTemplateStructureRel (uuid_[$COLUMN_LENGTH:75$], groupId, ctCollectionId);