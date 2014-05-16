drop table staff;
drop table customerbooking;
drop table room;
drop table roomtype;
drop table hotel;

CREATE TABLE hotel(
	id int not null generated always as identity(start with 1, increment by 1),
	name varchar(30) not null,
	city varchar(30) not null,
	primary key(id)
	);
	
create table roomtype(
	id int not null generated always as identity(start with 1, increment by 1),
	name varchar(30) not null,
	cost int not null,
	primary key(id),
	constraint chk_name check (name = 'SINGLE' or name = 'DOUBLE' or name = 'QUEEN' or name = 'EXECUTIVE' or name = 'SUITE'),
	constraint chk_cost check (cost >= 0)
	);

create table room(
	id int not null generated always as identity(start with 1, increment by 1),
	roomnumber int not null,
	roomtypeid int not null,
	availability varchar(30) default 'available' not null,
	hotelid int not null,
	primary key(id),
	foreign key (hotelid) references hotel(id),
	foreign key (roomtypeid) references roomtype(id),
	constraint chk_availability check (availability = 'available' or availability = 'maintenance' or availability = 'occupied')
	);

create table customerbooking(
	id int not null generated always as identity(start with 1, increment by 1),
	hotelid int not null,
	roomtypeid int not null,
	startdate date not null,
	enddate date not null,
	firstname varchar(30) not null,
	lastname varchar(30) not null,
	uniquestring varchar(30) not null unique,
	pin varchar(30) not null,
	primary key(id),
	foreign key (hotelid) references hotel(id),
	foreign key (roomtypeid) references roomtype(id),
	constraint chk_valid_date check (startdate<enddate)
	);
	
create table staff(
	id int not null generated always as identity(start with 1, increment by 1),
	firstname varchar(30) not null,
	lastname varchar(30) not null,
	password varchar(30)not null,
	access varchar(30) not null,
	primary key(id),
	constraint chk_access check (access = 'reception' or access = 'owner')
);
