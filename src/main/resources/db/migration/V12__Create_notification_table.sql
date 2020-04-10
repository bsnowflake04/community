create table notification
(
	id BIGINT auto_increment,
	notifier bigint not null,
	receiver bigint not null,
	outer_id bigint not null,
	type int not null,
	gmt_create bigint not null,
	status int default 0 not null,
	constraint notification_pk
		primary key (id)
);

comment on column notification.outer_id is '评论或问题';

comment on column notification.type is '区分outerid';

comment on column notification.status is '已读未读';
