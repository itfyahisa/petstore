INSERT INTO pet(category_id, name, status) VALUES
	(1, "test1", "available"),
	(2, "test2", "sold"),
	(2, "test3", "pending");
	
INSERT INTO category(name) VALUES
	("test1"),
	("test2");
	
INSERT INTO photo(pet_id, url) VALUES
	(1, "test1"),
	(1, "test2"),
	(2, "test3"),
	(2, "test4"),
	(3, "test5"),
	(3, "test6");

INSERT INTO tag(name) VALUES
	("test1"),
	("test2"),
	("test3"),
	("test4"),
	("test5"),
	("test6");

INSERT INTO pet_tag(pet_id, tag_id) VALUES
	(1, 1),
	(2, 2),
	(3, 3);
