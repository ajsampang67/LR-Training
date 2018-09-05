create table Event_Event (
	uuid_ VARCHAR(75) null,
	EventId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	eventDate VARCHAR(75) null,
	screenName VARCHAR(75) null,
	userId LONG,
	ipAddress VARCHAR(75) null,
	eventType VARCHAR(75) null
);