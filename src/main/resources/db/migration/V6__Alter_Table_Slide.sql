ALTER TABLE slide ADD COLUMN sub_title VARCHAR(255) AFTER title;
ALTER TABLE slide ADD COLUMN author VARCHAR(255) AFTER sub_title;
ALTER TABLE slide ADD COLUMN presented_at VARCHAR(255) AFTER author;
ALTER TABLE slide ADD COLUMN deleted_at VARCHAR(255) AFTER updated_at;

