alter table COMMENT
	add like_count int default 0;

alter table COMMENT
	add content varchar(256) not null;