DROP DATABASE IF EXISTS fitmate_db;
-- MySQL Workbench Forward Engineering

-- -----------------------------------------------------
-- Schema fitmate_db
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `fitmate_db` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `fitmate_db` ;

-- -----------------------------------------------------
-- Table `fitmate_db`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `fitmate_db`.`user` (
  `u_id` VARCHAR(45) NOT NULL COMMENT '유저 아이디',
  `pw` VARCHAR(2000) NOT NULL COMMENT '비밀번호',
  `name` VARCHAR(45) NOT NULL COMMENT '이름',
  `email` VARCHAR(300) NOT NULL COMMENT '이메일',
  `gender` VARCHAR(10) NOT NULL COMMENT '성별',
  `phone_no` VARCHAR(70) NOT NULL COMMENT '전화 번호',
  `nickname` VARCHAR(40) NOT NULL COMMENT '닉네임',
  `state_message` VARCHAR(100) NULL,
  `img` VARCHAR(100) NOT NULL,
  `address` VARCHAR(100) NOT NULL,
  `x` VARCHAR(50) NOT NULL,
  `y` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`u_id`),
  UNIQUE INDEX `UQ_user_1` (`nickname` ASC) VISIBLE,
  UNIQUE INDEX `UQ_user_2` (`email` ASC) VISIBLE,
  UNIQUE INDEX `UQ_user_3` (`phone_no` ASC) VISIBLE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci
COMMENT = '유저 테이블';


-- -----------------------------------------------------
-- Table `fitmate_db`.`video`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `fitmate_db`.`video` (
  `v_id` VARCHAR(33) NOT NULL COMMENT '비디오 아이디',
  `title` VARCHAR(300) NOT NULL COMMENT '제목',
  `v_writer` VARCHAR(45) NOT NULL COMMENT '비디오 작성자',
  `reg_date` DATETIME NOT NULL COMMENT '등록 날짜',
  `view_cnt` INT NOT NULL DEFAULT '0' COMMENT '조회 수',
  `category` VARCHAR(30) NOT NULL COMMENT '카테고리',
  PRIMARY KEY (`v_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci
COMMENT = '비디오 테이블';


-- -----------------------------------------------------
-- Table `fitmate_db`.`comment`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `fitmate_db`.`comment` (
  `c_id` INT NOT NULL AUTO_INCREMENT COMMENT '댓글 아이디',
  `u_id` VARCHAR(45) NOT NULL COMMENT '유저 아이디',
  `v_id` VARCHAR(33) NOT NULL COMMENT '비디오 아이디',
  `content` VARCHAR(10000) NOT NULL COMMENT '내용',
  `reg_date` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '등록 날짜',
  PRIMARY KEY (`c_id`),
  INDEX `FK_comment_v_id_video_v_id` (`v_id` ASC) VISIBLE,
  INDEX `FK_comment_u_id_user_u_id_idx` (`u_id` ASC) VISIBLE,
  CONSTRAINT `FK_comment_u_id_user_u_id`
    FOREIGN KEY (`u_id`)
    REFERENCES `fitmate_db`.`user` (`u_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `FK_comment_v_id_video_v_id`
    FOREIGN KEY (`v_id`)
    REFERENCES `fitmate_db`.`video` (`v_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci
COMMENT = '댓글 테이블';

SELECT * FROM comment;

-- -----------------------------------------------------
-- Table `fitmate_db`.`favorite`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `fitmate_db`.`favorite` (
  `u_id` VARCHAR(45) NOT NULL COMMENT '유저 아이디',
  `v_id` VARCHAR(33) NOT NULL COMMENT '비디오 아이디',
  PRIMARY KEY (`u_id`, `v_id`),
  INDEX `FK_favorite_v_id_video_v_id` (`v_id` ASC) VISIBLE,
  CONSTRAINT `FK_favorite_u_id_user_u_id`
    FOREIGN KEY (`u_id`)
    REFERENCES `fitmate_db`.`user` (`u_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `FK_favorite_v_id_video_v_id`
    FOREIGN KEY (`v_id`)
    REFERENCES `fitmate_db`.`video` (`v_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci
COMMENT = '좋아요 테이블';


-- -----------------------------------------------------
-- Table `fitmate_db`.`follow`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `fitmate_db`.`follow` (
  `from_user` VARCHAR(45) NOT NULL COMMENT '팔로우 함',
  `to_user` VARCHAR(45) NOT NULL COMMENT '팔로우당함',
  PRIMARY KEY (`from_user`, `to_user`),
  INDEX `FK_follow_to_user_user_u_id` (`to_user` ASC) VISIBLE,
  CONSTRAINT `FK_follow_from_user_user_u_id`
    FOREIGN KEY (`from_user`)
    REFERENCES `fitmate_db`.`user` (`u_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `FK_follow_to_user_user_u_id`
    FOREIGN KEY (`to_user`)
    REFERENCES `fitmate_db`.`user` (`u_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci
COMMENT = '팔로우 테이블';

SELECT * FROM follow;
DELETE FROM follow;

-- -----------------------------------------------------
-- Table `fitmate_db`.`message`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `fitmate_db`.`message` (
  `m_id` INT NOT NULL AUTO_INCREMENT,
  `from_user` VARCHAR(45) NOT NULL COMMENT '보내는 사람',
  `to_user` VARCHAR(45) NOT NULL COMMENT '받는 사람',
  `content` VARCHAR(45) NOT NULL COMMENT '메세지 내용',
  PRIMARY KEY (`m_id`),
  INDEX `FK_message_to_user_user_u_id_idx` (`to_user` ASC) VISIBLE,
  CONSTRAINT `FK_message_from_user_user_u_id`
    FOREIGN KEY (`from_user`)
    REFERENCES `fitmate_db`.`user` (`u_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `FK_message_to_user_user_u_id`
    FOREIGN KEY (`to_user`)
    REFERENCES `fitmate_db`.`user` (`u_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci
COMMENT = '메세지 테이블';

ALTER TABLE message MODIFY COLUMN `content` VARCHAR(1000) NOT NULL COMMENT '메세지 내용';

SELECT * FROM message;