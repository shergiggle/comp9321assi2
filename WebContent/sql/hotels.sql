drop table discount;
drop table staff;
drop table booking;
drop table roomavailability;
drop table customerbooking;
drop table customer;
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
	number int not null,
	typeid int not null,
	availability varchar(30) default 'available' not null,
	hotelid int not null,
	primary key(id),
	foreign key (hotelid) references hotel(id),
	foreign key (roomtypeid) references roomtype(id),
	constraint chk_availability check (availability = 'available' or availability = 'maintenance' or availability = 'occupied')
	);

create table customer (
	id int not null generated always as identity,
	first_name varchar(20) not null,
	last_name varchar(20) not null,
	primary key (id)
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
	
create table roomavailability (
	id int not null generated always as identity,
	roomid int,
	customerbookingid int not null,
	roomtypeid int not null,
	extrabed int not null,
	primary key (id),
	foreign key (roomid) references room(id),
	foreign key (customerbookingid) references customerbooking(id),
	foreign key (roomtypeid) references roomtype(id),
	constraint chk_boolean_extra_bed check (extra_bed=0 or extra_bed=1)
);
	
create table booking (
	id int not null generated always as identity,
	code varchar(30) not null unique,
	customerbookingid int not null unique,
	primary key(id),
	foreign key (customerbookingid) references customerbooking(id)
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

create table discount (
	id int not null generated always as identity,
	roomtypeid int not null,
	startdate date not null,
	enddate date not null,
	discountedprice int not null,
	hotelid int not null,
	primary key (id),
	foreign key (roomtypeid) references roomtype(id),
	foreign key (hotelid) references hotel(id)
	constraint chk_discountedprice check (discountedprice>=0),
);