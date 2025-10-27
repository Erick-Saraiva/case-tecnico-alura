CREATE TABLE task (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    statement VARCHAR(255) NOT NULL,
    position INT NOT NULL,
    type VARCHAR(50) NOT NULL,
    course_id BIGINT NOT NULL,
    CONSTRAINT fk_task_course FOREIGN KEY (course_id) REFERENCES course(id)
);