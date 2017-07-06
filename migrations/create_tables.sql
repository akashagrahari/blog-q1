DROP TABLE IF EXISTS blog_parts;

CREATE TABLE blog_parts (
  id int(11) unsigned NOT NULL AUTO_INCREMENT,
  content text,
  blog_id int(11) DEFAULT NULL,
  PRIMARY KEY (id)
);

DROP TABLE IF EXISTS blogs;

CREATE TABLE blogs (
  id int(11) unsigned NOT NULL AUTO_INCREMENT,
  uniq_id varchar(36) DEFAULT NULL,
  title varchar(255) DEFAULT NULL,
  created_at timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (id)
);

DROP TABLE IF EXISTS comments;

CREATE TABLE comments (
  id int(11) unsigned NOT NULL AUTO_INCREMENT,
  content text,
  created_at timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  blog_part_id int(11) DEFAULT NULL,
  PRIMARY KEY (id)
);