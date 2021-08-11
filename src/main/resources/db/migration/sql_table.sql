DROP TABLE IF EXISTS employee;

CREATE TABLE employee (
  id int NOT NULL,
  full_name varchar(250) not null,
  leader int,
  branch_name varchar (250) not null,
  PRIMARY KEY (id), foreign key(leader) REFERENCES employee(id)
);

DROP TABLE IF EXISTS employee;

CREATE TABLE tasks (
  id int NOT NULL,
  priority int,
  description varchar(2000),
  employee_id int,
  PRIMARY KEY (id), foreign key(employee_id) REFERENCES employee(id)
);

