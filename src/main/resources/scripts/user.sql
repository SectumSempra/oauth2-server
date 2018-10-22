CREATE TABLE IF NOT EXISTS  roles (
  role_id int4 NOT NULL,
  role varchar(255) DEFAULT NULL,
  PRIMARY KEY (role_id)
);

CREATE TABLE  IF NOT EXISTS user_role (
  user_id int4 NOT NULL,
  role_id int4 NOT NULL,
  PRIMARY KEY (user_id,role_id),
); 

CREATE TABLE IF NOT EXISTS  users (
  user_id int4 NOT NULL,
  active int4 DEFAULT NULL,
  email varchar(255) DEFAULT NULL,
  last_name varchar(255) DEFAULT NULL,
  name varchar(255) DEFAULT NULL,
  password varchar(255) DEFAULT NULL,
  PRIMARY KEY (user_id)
);

TRUNCATE TABLE user_role;
TRUNCATE TABLE roles;
TRUNCATE TABLE users;



insert into roles
values(1,'ADMIN');

insert into roles
values(2,'STANDARD');

insert into users
values(1,1,'b@b.com','b','b','$2a$10$NNrk/tkac02cwdLVCM98SOoqpCNNyTymcaaQvXpuuOEhIlb890slK');

insert into user_role
values(1,1);

commit;