CREATE TABLE IF NOT EXISTS groups (
	id SERIAL PRIMARY KEY,
	name VARCHAR(20) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS periods (
	id SERIAL PRIMARY KEY,
	period_start TIMESTAMP NOT NULL,
	period_end TIMESTAMP NOT NULL
);

CREATE TABLE IF NOT EXISTS students (
	id SERIAL PRIMARY KEY,
	first_name VARCHAR(30) NOT NULL,
	last_name VARCHAR(30) NOT NULL,
	group_id INT REFERENCES groups(id)
);

CREATE TABLE IF NOT EXISTS subjects (
	id SERIAL PRIMARY KEY,
	name VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS lectors (
	id SERIAL PRIMARY KEY,
	first_name VARCHAR(30) NOT NULL,
	last_name VARCHAR(30) NOT NULL	
);

CREATE TABLE IF NOT EXISTS lectors_subjects (
	lector_id INT NOT NULL REFERENCES lectors(id),
	subject_id INT NOT NULL REFERENCES subjects(id),
	CONSTRAINT pk_lectors_subjects PRIMARY KEY(lector_id, subject_id)
);

CREATE TABLE IF NOT EXISTS lecture_halls (
	id SERIAL PRIMARY KEY,
	name VARCHAR(20) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS lecture_halls_periods (
	lecture_hall_id INT NOT NULL REFERENCES lecture_halls(id),
	period_id INT NOT NULL REFERENCES periods(id),
	CONSTRAINT unique_lecture_hall_period UNIQUE(lecture_hall_id, period_id)
);

CREATE TABLE IF NOT EXISTS lectures (
	id SERIAL PRIMARY KEY,
	subject_id INT NOT NULL REFERENCES subjects(id),
	group_id INT NOT NULL REFERENCES groups(id),
	lector_id INT NOT NULL REFERENCES lectors(id),
	lecture_hall_id INT NOT NULL REFERENCES lecture_halls(id),
	period_id INT NOT NULL REFERENCES periods(id)
);
