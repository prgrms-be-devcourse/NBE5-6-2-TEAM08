DROP TABLE IF EXISTS `region`;
DROP TABLE IF EXISTS `user_like`;
DROP TABLE IF EXISTS `course_review`;
DROP TABLE IF EXISTS `picture`;
DROP TABLE IF EXISTS `place`;
DROP TABLE IF EXISTS `recommend_courses`;
DROP TABLE IF EXISTS `editor_courses`;
DROP TABLE IF EXISTS `my_courses`;
DROP TABLE IF EXISTS `user`;


CREATE TABLE `user` (
                        `id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT '회원번호',
                        `user_id` VARCHAR(255) NOT NULL COMMENT '회원 ID',
                        `user_pw` VARCHAR(255) NOT NULL COMMENT '비밀번호',
                        `user_email` VARCHAR(255) NOT NULL COMMENT '이메일',
                        `user_name` VARCHAR(255) NOT NULL COMMENT '이름',
                        `user_nickname` VARCHAR(255) NOT NULL COMMENT '닉네임',
                        `user_phone` VARCHAR(255) NOT NULL COMMENT '전화번호',
                        `user_role` ENUM('ROLE_USER', 'ROLE_ADMIN') NOT NULL DEFAULT 'ROLE_USER' COMMENT '회원 권한',
                        `user_leaved` BOOLEAN NOT NULL DEFAULT TRUE COMMENT '탈퇴여부'

);

CREATE TABLE `my_courses` (
                                  `course_id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT '코스 ID',
                                  `id` INT NOT NULL COMMENT '회원번호',
                                  `title` VARCHAR(255) NOT NULL COMMENT '코스 제목',
                                  `description` VARCHAR(255) NOT NULL COMMENT '코스 설명',

    -- 외래키 추가
                                  CONSTRAINT `FK_my_course_user` FOREIGN KEY (`id`) REFERENCES `user`(`id`) ON DELETE CASCADE
);

CREATE TABLE `editor_courses` (
                                  `editor_course_id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT '에디터 코스 ID',
                                  `id` INT NOT NULL COMMENT '회원 번호',
                                  `title` VARCHAR(255) NOT NULL COMMENT '코스 제목',
                                  `description` VARCHAR(255) NOT NULL COMMENT '코스 설명',
                                  `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성 시각',

    -- 외래키 설정
                                  CONSTRAINT `FK_editor_user` FOREIGN KEY (`id`) REFERENCES `user`(`id`) ON DELETE CASCADE
);

CREATE TABLE `recommend_courses` (
                                    `recommend_id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT '추천 코스 ID',
                                    `course_id` INT NOT NULL COMMENT '코스 ID',
                                    `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성 시각',

    -- 외래키 제약
                                    CONSTRAINT `FK_recommend_my_course` FOREIGN KEY (`course_id`) REFERENCES `my_courses`(`course_id`) ON DELETE CASCADE
);

CREATE TABLE `region` (
                          `region_id` INT NOT NULL PRIMARY KEY COMMENT '지역 ID',
                          `city` VARCHAR(10) NOT NULL COMMENT '시',
                          `gu` VARCHAR(10) NOT NULL COMMENT '구',
                          `dong` VARCHAR(10) NOT NULL COMMENT '동'
);

CREATE TABLE `place` (
                         `place_id` INT NOT NULL PRIMARY KEY COMMENT '장소 ID',
                         `course_id` INT NULL COMMENT '코스 ID',
                         `editor_course_id` INT NULL COMMENT '에디터 코스 ID',
                         `region_id` INT NOT NULL COMMENT '지역 ID',
                         `place_name` VARCHAR(255) NOT NULL COMMENT '장소명',
                         `place_address` VARCHAR(255) NOT NULL COMMENT '주소',
                         `place_url` VARCHAR(255) NOT NULL COMMENT '장소 URL',
                         `latitude` DOUBLE COMMENT '위도',
                         `longitude` DOUBLE COMMENT '경도',

    -- FK 제약조건
                         CONSTRAINT `FK_place_my_course` FOREIGN KEY (`course_id`) REFERENCES `my_courses`(`course_id`),
                         CONSTRAINT `FK_place_editor_course` FOREIGN KEY (`editor_course_id`) REFERENCES `editor_courses`(`editor_course_id`),
                         CONSTRAINT `FK_place_region` FOREIGN KEY (`region_id`) REFERENCES `region`(`region_id`)
);

CREATE TABLE `picture` (
                           `picture_id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT '사진 ID',
                           `recommend_id` INT NOT NULL COMMENT '추천 코스 ID',
                           `editor_course_id` INT NOT NULL COMMENT '에디터 코스 ID',
                           `origin_file_name` VARCHAR(255) NOT NULL COMMENT '원본 파일명',
                           `rename_file_name` VARCHAR(255) NOT NULL COMMENT '새 파일명',
                           `save_path` VARCHAR(255) NOT NULL COMMENT '저장 경로',
                           `type` VARCHAR(255) COMMENT '사진 유형',
                           `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '업로드 시각',

    -- 외래키 설정
                           CONSTRAINT `FK_picture_recommend` FOREIGN KEY (`recommend_id`) REFERENCES `recommend_courses`(`recommend_id`) ON DELETE CASCADE,
                           CONSTRAINT `FK_picture_editor_course` FOREIGN KEY (`editor_course_id`) REFERENCES `editor_courses`(`editor_course_id`) ON DELETE CASCADE

);

CREATE TABLE course_review (
                               review_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT '리뷰 ID',
                               recommend_id INT NOT NULL COMMENT '추천 코스 ID',
                               id INT NOT NULL COMMENT '회원번호',
                               review_text VARCHAR(255) COMMENT '리뷰 내용',
                               review_star INT CHECK (review_star BETWEEN 1 AND 5) COMMENT '별점 (1~5)',
                               created_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '작성시각',

                               CONSTRAINT FK_review_recommend FOREIGN KEY (recommend_id) REFERENCES recommend_courses(recommend_id) ON DELETE CASCADE,
                               CONSTRAINT FK_review_user FOREIGN KEY (id) REFERENCES user(id) ON DELETE CASCADE
);



CREATE TABLE `user_like` (
                             `like_id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT '찜 코스 ID',
                             `recommend_id` INT NOT NULL COMMENT '추천 코스 ID',
                             `id` INT NOT NULL COMMENT '회원번호',

    -- 중복 방지 제약조건
                             UNIQUE KEY `UNQ_user_like_once` (`recommend_id`, `id`),

    -- 외래키 제약조건
                             CONSTRAINT `FK_like_recommend` FOREIGN KEY (`recommend_id`) REFERENCES `recommend_courses`(`recommend_id`) ON DELETE CASCADE,
                             CONSTRAINT `FK_like_user` FOREIGN KEY (`id`) REFERENCES `user`(`id`) ON DELETE CASCADE
);


