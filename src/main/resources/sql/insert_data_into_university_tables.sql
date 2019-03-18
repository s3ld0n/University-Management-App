
INSERT INTO public.groups (id, name) VALUES (4, 'KOT-87');
INSERT INTO public.groups (id, name) VALUES (2, 'KSP-4');
INSERT INTO public.groups (id, name) VALUES (1, 'CHEM-7');
INSERT INTO public.groups (id, name) VALUES (12, 'Math-9');

SELECT pg_catalog.setval('public.groups_id_seq', 12, true);

INSERT INTO public.lectors (id, first_name, last_name) VALUES (1, 'Ivan', 'Karamazov');
INSERT INTO public.lectors (id, first_name, last_name) VALUES (2, 'Frodo', 'Beggins');
INSERT INTO public.lectors (id, first_name, last_name) VALUES (3, 'Timur', 'Kolmogorov');
INSERT INTO public.lectors (id, first_name, last_name) VALUES (4, 'Petr', 'Voronov');


SELECT pg_catalog.setval('public.lectors_id_seq', 5, true);


INSERT INTO public.subjects (id, name) VALUES (1, 'Math-1');
INSERT INTO public.subjects (id, name) VALUES (2, 'Organic Chemistry-1');
INSERT INTO public.subjects (id, name) VALUES (3, 'Physics');
INSERT INTO public.subjects (id, name) VALUES (5, 'World History');
INSERT INTO public.subjects (id, name) VALUES (6, 'World Literature');
INSERT INTO public.subjects (id, name) VALUES (7, 'Visual Arts');
INSERT INTO public.subjects (id, name) VALUES (8, 'Pyrotechnics');
INSERT INTO public.subjects (id, name) VALUES (9, 'Anthropology');
INSERT INTO public.subjects (id, name) VALUES (10, 'Archeology');
INSERT INTO public.subjects (id, name) VALUES (12, 'Astronomy');

INSERT INTO public.lectors_subjects (lector_id, subject_id) VALUES (2, 2);
INSERT INTO public.lectors_subjects (lector_id, subject_id) VALUES (1, 2);
INSERT INTO public.lectors_subjects (lector_id, subject_id) VALUES (1, 3);
INSERT INTO public.lectors_subjects (lector_id, subject_id) VALUES (3, 5);
INSERT INTO public.lectors_subjects (lector_id, subject_id) VALUES (4, 7);
INSERT INTO public.lectors_subjects (lector_id, subject_id) VALUES (4, 5);
INSERT INTO public.lectors_subjects (lector_id, subject_id) VALUES (4, 9);


INSERT INTO public.lecture_halls (id, name) VALUES (1, '333-F');
INSERT INTO public.lecture_halls (id, name) VALUES (4, '115-B');


SELECT pg_catalog.setval('public.lecture_halls_id_seq', 4, true);


INSERT INTO public.periods (id, period_start, period_end) VALUES (1, '2019-01-02 10:00:00', '2019-01-02 11:20:00');
INSERT INTO public.periods (id, period_start, period_end) VALUES (3, '2019-04-05 12:00:00', '2019-04-05 13:30:00');
INSERT INTO public.periods (id, period_start, period_end) VALUES (4, '2019-04-12 10:20:00', '2019-04-18 18:00:00');
INSERT INTO public.periods (id, period_start, period_end) VALUES (5, '1989-12-01 12:01:00', '1990-11-12 19:01:00');
INSERT INTO public.periods (id, period_start, period_end) VALUES (6, '1989-12-01 12:01:00', '1990-11-12 19:01:00');
INSERT INTO public.periods (id, period_start, period_end) VALUES (7, '1989-12-01 12:01:00', '1990-11-12 19:01:00');

INSERT INTO public.lecture_halls_periods (lecture_hall_id, period_id) VALUES (1, 1);
INSERT INTO public.lecture_halls_periods (lecture_hall_id, period_id) VALUES (1, 3);

INSERT INTO public.lectures (id, subject_id, group_id, lector_id, lecture_hall_id, period_id) VALUES (12, 5, 2, 4, 1, 1);
INSERT INTO public.lectures (id, subject_id, group_id, lector_id, lecture_hall_id, period_id) VALUES (14, 2, 2, 4, 1, 1);

SELECT pg_catalog.setval('public.lectures_id_seq', 14, true);

SELECT pg_catalog.setval('public.period_id_seq', 7, true);

INSERT INTO public.students (id, first_name, last_name, group_id) VALUES (8, 'Aria', 'Stark', 1);
INSERT INTO public.students (id, first_name, last_name, group_id) VALUES (45, 'John', 'Johnson', 1);
INSERT INTO public.students (id, first_name, last_name, group_id) VALUES (46, 'Mike', 'Taison', 4);
INSERT INTO public.students (id, first_name, last_name, group_id) VALUES (47, 'Frodo', 'Beggins', 4);
INSERT INTO public.students (id, first_name, last_name, group_id) VALUES (17, 'John', 'Marsten', 1);
INSERT INTO public.students (id, first_name, last_name, group_id) VALUES (1, 'Gregory', 'House', 2);
INSERT INTO public.students (id, first_name, last_name, group_id) VALUES (50, 'Solod', 'Karat', 4);
INSERT INTO public.students (id, first_name, last_name, group_id) VALUES (53, 'Maria', 'Voronova', 2);
INSERT INTO public.students (id, first_name, last_name, group_id) VALUES (7, 'John', 'Snow', 4);
INSERT INTO public.students (id, first_name, last_name, group_id) VALUES (3, 'Ronald', 'Beaver', 2);
INSERT INTO public.students (id, first_name, last_name, group_id) VALUES (55, 'Nadejda', 'Kolosova', 1);
INSERT INTO public.students (id, first_name, last_name, group_id) VALUES (56, 'Jane', 'Ayer', 1);
INSERT INTO public.students (id, first_name, last_name, group_id) VALUES (57, 'Vara', 'Verihovna', 1);
INSERT INTO public.students (id, first_name, last_name, group_id) VALUES (12, 'Jourdan', 'Peterson', 4);
INSERT INTO public.students (id, first_name, last_name, group_id) VALUES (42, 'Marlin', 'Darvin', 1);
INSERT INTO public.students (id, first_name, last_name, group_id) VALUES (10, 'Tirion', 'Lanister', 1);
INSERT INTO public.students (id, first_name, last_name, group_id) VALUES (9, 'Eddard', 'Stark', 2);
INSERT INTO public.students (id, first_name, last_name, group_id) VALUES (11, 'Konan', 'Warrior', 2);
INSERT INTO public.students (id, first_name, last_name, group_id) VALUES (18, 'Mirok', 'Lobutov', 1);

SELECT pg_catalog.setval('public.students_id_seq', 57, true);

SELECT pg_catalog.setval('public.subjects_id_seq', 12, true);
