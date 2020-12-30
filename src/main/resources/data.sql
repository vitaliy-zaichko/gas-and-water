drop table IF EXISTS users cascade;

create table users(
   id uuid PRIMARY KEY,
   fio varchar(255) not null unique
);

drop table IF EXISTS measurement;

create TABLE measurement (
  id uuid  PRIMARY KEY,
  user_id uuid not null,
  measurement_date date not null,
  hot_water int not null,
  cold_water int not null,
  gas int not null,
  foreign key (user_id) references users(id)
);

insert into users (id, fio) values ('1a1407a6-ed6e-4f35-ad3d-840c9b9335d6', 'John Silver');
insert into users (id, fio) values ('ec782576-fe38-47fa-9437-03b6cf0ad5a9', 'Sherlock Holmes');

insert into measurement (id, user_id, measurement_date, hot_water, cold_water, gas) values
('ed2963a7-6dec-429a-8d85-f67a949692db', '1a1407a6-ed6e-4f35-ad3d-840c9b9335d6', now(), 20, 20, 20);
insert into measurement (id, user_id, measurement_date, hot_water, cold_water, gas) values
('107c9bb3-e376-4d8a-a74f-692f2ccd039c', '1a1407a6-ed6e-4f35-ad3d-840c9b9335d6', now() - INTERVAL '1' MONTH, 10, 10, 10);