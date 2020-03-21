SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for about
-- ----------------------------
DROP TABLE IF EXISTS `about`;
CREATE TABLE `about` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `text` varchar(20000) DEFAULT NULL,
  `name` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for achievement
-- ----------------------------
DROP TABLE IF EXISTS `achievement`;
CREATE TABLE `achievement` (
  `aid` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(32) DEFAULT NULL,
  `desciption` varchar(255) DEFAULT NULL,
  `status` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`aid`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for achievementlist
-- ----------------------------
DROP TABLE IF EXISTS `achievementlist`;
CREATE TABLE `achievementlist` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uid` int(11) NOT NULL,
  `aid` int(11) NOT NULL,
  `date` datetime DEFAULT NULL,
  `validtime` int(11) DEFAULT NULL COMMENT '有效期：天，-1则为永久',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for blog
-- ----------------------------
DROP TABLE IF EXISTS `blog`;
CREATE TABLE `blog` (
  `bid` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(64) CHARACTER SET utf8 DEFAULT NULL,
  `uid` int(11) NOT NULL,
  `date` datetime DEFAULT NULL,
  `text` varchar(16000) DEFAULT NULL,
  `up_vote` int(11) DEFAULT '0',
  `down_vote` int(11) DEFAULT '0',
  `num` int(11) DEFAULT '0',
  `state` int(11) DEFAULT '0',
  PRIMARY KEY (`bid`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for blogcollection
-- ----------------------------
DROP TABLE IF EXISTS `blogcollection`;
CREATE TABLE `blogcollection` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `bid` int(11) NOT NULL,
  `uid` int(11) DEFAULT NULL,
  `date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for blogcomments
-- ----------------------------
DROP TABLE IF EXISTS `blogcomments`;
CREATE TABLE `blogcomments` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `bid` int(11) NOT NULL,
  `uid` int(11) NOT NULL,
  `preid` int(11) DEFAULT NULL,
  `text` varchar(5550) DEFAULT NULL,
  `date` datetime DEFAULT CURRENT_TIMESTAMP,
  `up_vote` int(11) DEFAULT '0',
  `down_vote` int(11) DEFAULT '0',
  `state` int(11) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for blogvote
-- ----------------------------
DROP TABLE IF EXISTS `blogvote`;
CREATE TABLE `blogvote` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `bid` int(11) NOT NULL,
  `uid` int(11) NOT NULL,
  `commentid` int(11) DEFAULT '0' COMMENT '如果不为0则代表给本博文下面某条评论投票',
  `vote` int(11) DEFAULT NULL,
  `createtime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for code
-- ----------------------------
DROP TABLE IF EXISTS `code`;
CREATE TABLE `code` (
  `sid` int(11) NOT NULL,
  `text` varchar(20000) DEFAULT NULL,
  PRIMARY KEY (`sid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for comment
-- ----------------------------
DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment` (
  `cid` int(11) NOT NULL AUTO_INCREMENT,
  `uid` int(11) NOT NULL,
  `content` varchar(2550) DEFAULT NULL,
  `date` datetime DEFAULT NULL,
  `preuid` int(11) DEFAULT NULL,
  `up_vote` int(11) DEFAULT '0',
  `down_vote` int(11) DEFAULT '0',
  `pid` int(11) NOT NULL,
  PRIMARY KEY (`cid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for contest
-- ----------------------------
DROP TABLE IF EXISTS `contest`;
CREATE TABLE `contest` (
  `cid` int(11) NOT NULL AUTO_INCREMENT,
  `uid` int(11) NOT NULL DEFAULT '-1',
  `starttime` datetime DEFAULT NULL,
  `endstime` datetime DEFAULT NULL,
  `sealtime` int(11) DEFAULT '0',
  `status` int(11) NOT NULL DEFAULT '0',
  `password` varchar(32) DEFAULT NULL,
  `title` varchar(64) DEFAULT NULL,
  `description` varchar(1024) DEFAULT NULL,
  `up_vote` int(11) NOT NULL DEFAULT '0',
  `down_vote` int(11) NOT NULL DEFAULT '0',
  `level` int(11) NOT NULL,
  `type` int(11) DEFAULT NULL,
  PRIMARY KEY (`cid`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for contestnotice
-- ----------------------------
DROP TABLE IF EXISTS `contestnotice`;
CREATE TABLE `contestnotice` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `cid` int(11) NOT NULL,
  `pid` int(11) NOT NULL,
  `uid` int(11) NOT NULL,
  `question` varchar(255) DEFAULT NULL,
  `answer` varchar(255) DEFAULT NULL,
  `qdate` datetime DEFAULT NULL,
  `adate` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for contestproblems
-- ----------------------------
DROP TABLE IF EXISTS `contestproblems`;
CREATE TABLE `contestproblems` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `cid` int(11) NOT NULL,
  `pid` int(11) NOT NULL,
  `num` int(11) NOT NULL,
  `ac_cnt` int(11) NOT NULL DEFAULT '0',
  `all_cnt` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for contestregister
-- ----------------------------
DROP TABLE IF EXISTS `contestregister`;
CREATE TABLE `contestregister` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uid` int(11) NOT NULL,
  `cid` int(11) NOT NULL,
  `date` datetime DEFAULT CURRENT_TIMESTAMP,
  `rank` int(11) DEFAULT NULL,
  `status` int(11) NOT NULL DEFAULT '0',
  `lastrating` int(11) NOT NULL DEFAULT '0',
  `role` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for contestsubmitinfo
-- ----------------------------
DROP TABLE IF EXISTS `contestsubmitinfo`;
CREATE TABLE `contestsubmitinfo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `cid` int(11) NOT NULL,
  `pid` int(11) NOT NULL,
  `uid` int(11) DEFAULT NULL,
  `cnt` int(11) NOT NULL DEFAULT '0',
  `result` int(11) DEFAULT NULL,
  `ac_time` datetime DEFAULT NULL,
  `score` double(8,2) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `uid` (`uid`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for contestvote
-- ----------------------------
DROP TABLE IF EXISTS `contestvote`;
CREATE TABLE `contestvote` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uid` int(11) DEFAULT NULL,
  `cid` int(11) DEFAULT NULL,
  `votetime` datetime DEFAULT NULL,
  `vote` int(2) unsigned zerofill DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for exlang
-- ----------------------------
DROP TABLE IF EXISTS `exlang`;
CREATE TABLE `exlang` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pid` int(11) NOT NULL,
  `exlang` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for goods
-- ----------------------------
DROP TABLE IF EXISTS `goods`;
CREATE TABLE `goods` (
  `gid` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(32) CHARACTER SET utf8mb4 DEFAULT NULL,
  `desciption` varchar(2550) CHARACTER SET utf8mb4 DEFAULT NULL,
  `image` varchar(255) DEFAULT NULL,
  `price` int(9) DEFAULT NULL,
  `count` int(11) DEFAULT NULL,
  `status` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`gid`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for goodsorder
-- ----------------------------
DROP TABLE IF EXISTS `goodsorder`;
CREATE TABLE `goodsorder` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `gid` int(11) NOT NULL,
  `uid` int(11) NOT NULL,
  `date` datetime DEFAULT NULL,
  `status` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for hack
-- ----------------------------
DROP TABLE IF EXISTS `hack`;
CREATE TABLE `hack` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sid` int(11) NOT NULL,
  `uid` int(11) NOT NULL,
  `filename` varchar(255) NOT NULL,
  `data` varchar(256) DEFAULT NULL,
  `date` datetime DEFAULT NULL,
  `result` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for invitation
-- ----------------------------
DROP TABLE IF EXISTS `invitation`;
CREATE TABLE `invitation` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `invitecode` varchar(255) NOT NULL,
  `state` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for judgeuseranswer
-- ----------------------------
DROP TABLE IF EXISTS `judgeuseranswer`;
CREATE TABLE `judgeuseranswer` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sid` varchar(255) NOT NULL,
  `uid` int(11) NOT NULL,
  `pos` int(11) NOT NULL,
  `answer` varchar(10000) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for message
-- ----------------------------
DROP TABLE IF EXISTS `message`;
CREATE TABLE `message` (
  `mid` int(11) NOT NULL AUTO_INCREMENT,
  `senduid` int(11) NOT NULL,
  `targetuid` int(11) NOT NULL,
  `date` datetime DEFAULT CURRENT_TIMESTAMP,
  `content` varchar(512) DEFAULT NULL,
  `status` int(11) NOT NULL DEFAULT '0',
  `title` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`mid`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for paste
-- ----------------------------
DROP TABLE IF EXISTS `paste`;
CREATE TABLE `paste` (
  `id` varchar(32) NOT NULL,
  `uid` int(11) NOT NULL,
  `code` varchar(15000) DEFAULT NULL,
  `date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for problem
-- ----------------------------
DROP TABLE IF EXISTS `problem`;
CREATE TABLE `problem` (
  `pid` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(32) NOT NULL,
  `description` varchar(4096) DEFAULT NULL,
  `input` varchar(2048) DEFAULT NULL,
  `output` varchar(2048) DEFAULT NULL,
  `sampleinput` varchar(512) DEFAULT NULL,
  `sampleoutput` varchar(512) CHARACTER SET utf8 DEFAULT NULL,
  `timelimit` int(11) NOT NULL DEFAULT '1000' COMMENT '单位：ms',
  `memorylimit` int(11) NOT NULL DEFAULT '32' COMMENT '单位：mb',
  `type` int(11) NOT NULL DEFAULT '0',
  `uid` int(11) NOT NULL,
  `status` int(11) NOT NULL DEFAULT '0',
  `special` int(11) DEFAULT '0',
  `hint` varchar(2048) DEFAULT NULL,
  PRIMARY KEY (`pid`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for problemcollection
-- ----------------------------
DROP TABLE IF EXISTS `problemcollection`;
CREATE TABLE `problemcollection` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pid` int(11) NOT NULL,
  `uid` int(11) NOT NULL,
  `date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for probleminfo
-- ----------------------------
DROP TABLE IF EXISTS `probleminfo`;
CREATE TABLE `probleminfo` (
  `pid` int(11) NOT NULL,
  `ac_cnt` int(11) NOT NULL DEFAULT '0',
  `pe_cnt` int(11) NOT NULL DEFAULT '0',
  `wa_cnt` int(11) NOT NULL DEFAULT '0',
  `tle_cnt` int(11) NOT NULL DEFAULT '0',
  `mle_cnt` int(11) NOT NULL DEFAULT '0',
  `ce_cnt` int(11) NOT NULL DEFAULT '0',
  `re_cnt` int(11) NOT NULL DEFAULT '0',
  `ole_cnt` int(11) NOT NULL DEFAULT '0',
  `se_cnt` int(11) NOT NULL DEFAULT '0',
  `up_vote` int(11) NOT NULL DEFAULT '0',
  `down_vote` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`pid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for problemstar
-- ----------------------------
DROP TABLE IF EXISTS `problemstar`;
CREATE TABLE `problemstar` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uid` int(11) DEFAULT NULL,
  `pid` int(11) DEFAULT NULL,
  `time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for problemtag
-- ----------------------------
DROP TABLE IF EXISTS `problemtag`;
CREATE TABLE `problemtag` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pid` int(11) NOT NULL,
  `uid` int(11) NOT NULL,
  `tid` int(11) NOT NULL,
  `date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for problemvote
-- ----------------------------
DROP TABLE IF EXISTS `problemvote`;
CREATE TABLE `problemvote` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pid` int(11) DEFAULT NULL,
  `uid` int(11) DEFAULT NULL,
  `vote` int(11) DEFAULT NULL,
  `createtime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for submitcollection
-- ----------------------------
DROP TABLE IF EXISTS `submitcollection`;
CREATE TABLE `submitcollection` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sid` int(11) NOT NULL,
  `uid` int(11) NOT NULL,
  `date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for submitlist
-- ----------------------------
DROP TABLE IF EXISTS `submitlist`;
CREATE TABLE `submitlist` (
  `sid` int(11) NOT NULL AUTO_INCREMENT,
  `date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `pid` int(11) NOT NULL,
  `cid` int(11) NOT NULL DEFAULT '-1',
  `uid` int(11) NOT NULL,
  `lang` varchar(32) NOT NULL,
  `result` int(11) DEFAULT NULL,
  `time_used` int(11) DEFAULT NULL,
  `memory_used` int(11) DEFAULT NULL,
  `state` int(11) NOT NULL DEFAULT '0',
  `score` double(11,2) NOT NULL DEFAULT '0.00',
  `pos` int(11) DEFAULT NULL,
  `msg` varchar(1024) DEFAULT NULL,
  `size` double DEFAULT NULL,
  PRIMARY KEY (`sid`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tags
-- ----------------------------
DROP TABLE IF EXISTS `tags`;
CREATE TABLE `tags` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for test
-- ----------------------------
DROP TABLE IF EXISTS `test`;
CREATE TABLE `test` (
  `username` varchar(255) NOT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `uid` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(32) NOT NULL,
  `password` varchar(64) NOT NULL,
  `invitecode` varchar(255) NOT NULL,
  `note` varchar(128) DEFAULT NULL,
  `image` varchar(64) DEFAULT NULL,
  `coins` int(11) NOT NULL DEFAULT '0',
  `school` varchar(64) NOT NULL,
  `email` varchar(32) DEFAULT NULL,
  `level` int(11) NOT NULL DEFAULT '0',
  `status` int(11) NOT NULL DEFAULT '0',
  `rating` int(11) NOT NULL DEFAULT '0',
  `registerdate` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `maxscore` int(11) NOT NULL DEFAULT '0',
  `lastvisit` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `role` int(11) NOT NULL DEFAULT '1',
  `liveness` int(11) NOT NULL DEFAULT '500',
  `preaid` int(11) NOT NULL DEFAULT '-1',
  `sufaid` int(11) NOT NULL DEFAULT '-1',
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for wiki
-- ----------------------------
DROP TABLE IF EXISTS `wiki`;
CREATE TABLE `wiki` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(32) NOT NULL,
  `title` varchar(255) DEFAULT NULL,
  `descr` varchar(255) DEFAULT NULL,
  `bid` int(11) NOT NULL DEFAULT '-1',
  `preid` int(11) NOT NULL DEFAULT '-1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;
