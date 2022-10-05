CREATE TABLE spies (
	spy_id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
	name VARCHAR(50)
);

CREATE TABLE missions (
	mission_id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
	mission_name VARCHAR(50),
	gadget_1 VARCHAR(50),
	gadget_2 VARCHAR(50),
	spy_id INT REFERENCES spies(spy_id)
);
