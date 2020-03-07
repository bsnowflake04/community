alter table USER alter column ACCOUNT_ID set not null;

alter table USER alter column GMT_CREATE set not null;

alter table USER alter column GMT_MODIFIED set not null;

alter table QUESTION alter column GMT_CREATE set not null;

alter table QUESTION alter column GMT_MODIFIED set not null;

alter table QUESTION alter column CREATOR set not null;