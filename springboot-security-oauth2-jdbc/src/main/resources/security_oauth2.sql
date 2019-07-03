/*
SQLyog ‰ºÅ‰∏öÁâà - MySQL GUI v8.14 
MySQL - 5.7.24 : Database - security_oauth2
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`security_oauth2` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_german2_ci */;

USE `security_oauth2`;

/*Table structure for table `b_account` */

CREATE TABLE `b_account` (
  `id` varchar(255) NOT NULL,
  `account_type` int(11) NOT NULL,
  `create_date` datetime NOT NULL,
  `modify_date` datetime DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `status` int(11) NOT NULL,
  `user_name` varchar(255) DEFAULT NULL,
  `last_password_reset_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Data for the table `b_account` */

insert  into `b_account`(`id`,`account_type`,`create_date`,`modify_date`,`password`,`status`,`user_name`,`last_password_reset_date`) values ('1',0,'2019-05-23 00:00:00',NULL,'e10adc3949ba59abbe56e057f20f883e',1,'user','2019-06-03 00:00:00'),('2',0,'2019-05-23 00:00:00',NULL,'e10adc3949ba59abbe56e057f20f883e',1,'admin',NULL),('3',0,'2019-05-23 00:00:00',NULL,'e10adc3949ba59abbe56e057f20f883e',1,'user1',NULL);

/*Table structure for table `b_account_operator` */

CREATE TABLE `b_account_operator` (
  `id` varchar(255) NOT NULL,
  `account_id` varchar(255) NOT NULL,
  `operator_id` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK18uevf3xnbb28w8neb1yv6nf4` (`account_id`),
  KEY `FKcvhp9a6efk3iu32kaetfe91b0` (`operator_id`),
  CONSTRAINT `FK18uevf3xnbb28w8neb1yv6nf4` FOREIGN KEY (`account_id`) REFERENCES `b_account` (`id`),
  CONSTRAINT `FKcvhp9a6efk3iu32kaetfe91b0` FOREIGN KEY (`operator_id`) REFERENCES `operator` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Data for the table `b_account_operator` */

insert  into `b_account_operator`(`id`,`account_id`,`operator_id`) values ('1','2','1');

/*Table structure for table `b_account_user` */

CREATE TABLE `b_account_user` (
  `id` varchar(255) NOT NULL,
  `account_id` varchar(255) NOT NULL,
  `user_id` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK1utji0q0iovims3kgeruh24be` (`account_id`),
  KEY `FK1in1x24oloy1mih2qdt9k70ua` (`user_id`),
  CONSTRAINT `FK1in1x24oloy1mih2qdt9k70ua` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  CONSTRAINT `FK1utji0q0iovims3kgeruh24be` FOREIGN KEY (`account_id`) REFERENCES `b_account` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Data for the table `b_account_user` */

insert  into `b_account_user`(`id`,`account_id`,`user_id`) values ('1','1','1'),('2','3','2');

/*Table structure for table `clientdetails` */

CREATE TABLE `clientdetails` (
  `appId` varchar(256) NOT NULL,
  `resourceIds` varchar(256) DEFAULT NULL,
  `appSecret` varchar(256) DEFAULT NULL,
  `scope` varchar(256) DEFAULT NULL,
  `grantTypes` varchar(256) DEFAULT NULL,
  `redirectUrl` varchar(256) DEFAULT NULL,
  `authorities` varchar(256) DEFAULT NULL,
  `access_token_validity` int(11) DEFAULT NULL,
  `refresh_token_validity` int(11) DEFAULT NULL,
  `additionalInformation` varchar(4096) DEFAULT NULL,
  `autoApproveScopes` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`appId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `clientdetails` */

/*Table structure for table `competence` */

CREATE TABLE `competence` (
  `id` varchar(255) NOT NULL,
  `comment` varchar(128) NOT NULL,
  `create_date` datetime NOT NULL,
  `level` int(11) DEFAULT NULL,
  `loading_mode` int(11) DEFAULT NULL,
  `methods` varchar(255) NOT NULL,
  `modify_date` datetime DEFAULT NULL,
  `order_index` bigint(20) DEFAULT NULL,
  `resource` varchar(256) NOT NULL,
  `status` int(11) NOT NULL,
  `creator_id` varchar(255) NOT NULL,
  `modifier_id` varchar(255) DEFAULT NULL,
  `parent_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_ougxbma0caesf3wqsyp2rmase` (`comment`),
  KEY `FKr11qmcuu9pqucw5116uhrfrex` (`creator_id`),
  KEY `FKn0asq7nq6d5t515lr87vajq2v` (`modifier_id`),
  KEY `FKj0vq22fyidvia0wvaljg3j2mn` (`parent_id`),
  CONSTRAINT `FKj0vq22fyidvia0wvaljg3j2mn` FOREIGN KEY (`parent_id`) REFERENCES `competence` (`id`),
  CONSTRAINT `FKn0asq7nq6d5t515lr87vajq2v` FOREIGN KEY (`modifier_id`) REFERENCES `operator` (`id`),
  CONSTRAINT `FKr11qmcuu9pqucw5116uhrfrex` FOREIGN KEY (`creator_id`) REFERENCES `operator` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Data for the table `competence` */

/*Table structure for table `oauth_access_token` */

CREATE TABLE `oauth_access_token` (
  `token_id` varchar(256) DEFAULT NULL,
  `token` blob,
  `authentication_id` varchar(256) NOT NULL,
  `user_name` varchar(256) DEFAULT NULL,
  `client_id` varchar(256) DEFAULT NULL,
  `authentication` blob,
  `refresh_token` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`authentication_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `oauth_access_token` */

insert  into `oauth_access_token`(`token_id`,`token`,`authentication_id`,`user_name`,`client_id`,`authentication`,`refresh_token`) values ('03631fba0a912a46386f5431527e2915','¨Ì\0sr\0Corg.springframework.security.oauth2.common.DefaultOAuth2AccessToken≤û6$˙Œ\0L\0additionalInformationt\0Ljava/util/Map;L\0\nexpirationt\0Ljava/util/Date;L\0refreshTokent\0?Lorg/springframework/security/oauth2/common/OAuth2RefreshToken;L\0scopet\0Ljava/util/Set;L\0	tokenTypet\0Ljava/lang/String;L\0valueq\0~\0xpsr\0java.util.Collections$EmptyMapY6ÖZ‹Á–\0\0xpsr\0java.util.DatehjÅKYt\0\0xpw\0\0k∂NΩxsr\0Lorg.springframework.security.oauth2.common.DefaultExpiringOAuth2RefreshToken/ﬂGcù–…∑\0L\0\nexpirationq\0~\0xr\0Dorg.springframework.security.oauth2.common.DefaultOAuth2RefreshTokens·\ncT‘^\0L\0valueq\0~\0xpt\0$9b45e794-1e13-47a8-9597-55b79e18557fsq\0~\0	w\0\0k∂Y€4xsr\0%java.util.Collections$UnmodifiableSetÄí—èõÄU\0\0xr\0,java.util.Collections$UnmodifiableCollectionB\0ÄÀ^˜\0L\0ct\0Ljava/util/Collection;xpsr\0java.util.LinkedHashSetÿl◊Zï›*\0\0xr\0java.util.HashSet∫DÖïñ∏∑4\0\0xpw\0\0\0?@\0\0\0\0\0t\0appxt\0bearert\0$4562b82d-4c29-4eb7-ab2d-921dd2874460','287b1b4095d75bc94942ea499ad78a0c','user','client','¨Ì\0sr\0Aorg.springframework.security.oauth2.provider.OAuth2AuthenticationΩ@bR\0L\0\rstoredRequestt\0<Lorg/springframework/security/oauth2/provider/OAuth2Request;L\0userAuthenticationt\02Lorg/springframework/security/core/Authentication;xr\0Gorg.springframework.security.authentication.AbstractAuthenticationToken”™(~nGd\0Z\0\rauthenticatedL\0authoritiest\0Ljava/util/Collection;L\0detailst\0Ljava/lang/Object;xp\0sr\0&java.util.Collections$UnmodifiableList¸%1µÏé\0L\0listt\0Ljava/util/List;xr\0,java.util.Collections$UnmodifiableCollectionB\0ÄÀ^˜\0L\0cq\0~\0xpsr\0java.util.ArrayListxÅ“ô«aù\0I\0sizexp\0\0\0w\0\0\0sr\0Borg.springframework.security.core.authority.SimpleGrantedAuthority\0\0\0\0\0\0ö\0L\0rolet\0Ljava/lang/String;xpt\0ROLE_ANONYMOUSxq\0~\0psr\0:org.springframework.security.oauth2.provider.OAuth2Request\0\0\0\0\0\0\0\0Z\0approvedL\0authoritiesq\0~\0L\0\nextensionst\0Ljava/util/Map;L\0redirectUriq\0~\0L\0refresht\0;Lorg/springframework/security/oauth2/provider/TokenRequest;L\0resourceIdst\0Ljava/util/Set;L\0\rresponseTypesq\0~\0xr\08org.springframework.security.oauth2.provider.BaseRequest6(z>£qiΩ\0L\0clientIdq\0~\0L\0requestParametersq\0~\0L\0scopeq\0~\0xpt\0clientsr\0%java.util.Collections$UnmodifiableMapÒ•®˛tıB\0L\0mq\0~\0xpsr\0java.util.HashMap⁄¡√`—\0F\0\nloadFactorI\0	thresholdxp?@\0\0\0\0\0w\0\0\0\0\0\0t\0\ngrant_typet\0passwordt\0	client_idt\0clientt\0usernamet\0userxsr\0%java.util.Collections$UnmodifiableSetÄí—èõÄU\0\0xq\0~\0	sr\0java.util.LinkedHashSetÿl◊Zï›*\0\0xr\0java.util.HashSet∫DÖïñ∏∑4\0\0xpw\0\0\0?@\0\0\0\0\0t\0appxsq\0~\0%w\0\0\0?@\0\0\0\0\0sq\0~\0\rt\0clientxsq\0~\0\Z?@\0\0\0\0\0\0w\0\0\0\0\0\0\0xpsr\09org.springframework.security.oauth2.provider.TokenRequest÷*Ñ∏œ8¯\0L\0	grantTypeq\0~\0xq\0~\0t\0clientsq\0~\0sq\0~\0\Z?@\0\0\0\0\0w\0\0\0\0\0\0t\0\rrefresh_tokent\0$9b45e794-1e13-47a8-9597-55b79e18557ft\0\rclient_secrett\0secrett\0\ngrant_typet\0\rrefresh_tokent\0	client_idq\0~\0.xsq\0~\0\"sq\0~\0$w\0\0\0?@\0\0\0\0\0\0xq\0~\06sq\0~\0%w\0\0\0?@\0\0\0\0\0t\0testxsq\0~\0%w\0\0\0?@\0\0\0\0\0\0xsr\0[org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken\0\0\0\0\0\0ö\0L\0credentialsq\0~\0L\0	principalq\0~\0xq\0~\0sq\0~\0sq\0~\0\0\0\0w\0\0\0q\0~\0xq\0~\0@pt\0\0sr\02org.springframework.security.core.userdetails.User\0\0\0\0\0\0ö\0Z\0accountNonExpiredZ\0accountNonLockedZ\0credentialsNonExpiredZ\0enabledL\0authoritiesq\0~\0L\0passwordq\0~\0L\0usernameq\0~\0xpsq\0~\0\"sr\0java.util.TreeSet›òPìïÌá[\0\0xpsr\0Forg.springframework.security.core.userdetails.User$AuthorityComparator\0\0\0\0\0\0ö\0\0xpw\0\0\0q\0~\0xpq\0~\0!','b9dc66d324807a730162338bb75714f2'),('2af154aa7474c0af4e3abe1cacc6e6fc','¨Ì\0sr\0Corg.springframework.security.oauth2.common.DefaultOAuth2AccessToken≤û6$˙Œ\0L\0additionalInformationt\0Ljava/util/Map;L\0\nexpirationt\0Ljava/util/Date;L\0refreshTokent\0?Lorg/springframework/security/oauth2/common/OAuth2RefreshToken;L\0scopet\0Ljava/util/Set;L\0	tokenTypet\0Ljava/lang/String;L\0valueq\0~\0xpsr\0java.util.Collections$EmptyMapY6ÖZ‹Á–\0\0xpsr\0java.util.DatehjÅKYt\0\0xpw\0\0k∂\rÖDxsr\0Lorg.springframework.security.oauth2.common.DefaultExpiringOAuth2RefreshToken/ﬂGcù–…∑\0L\0\nexpirationq\0~\0xr\0Dorg.springframework.security.oauth2.common.DefaultOAuth2RefreshTokens·\ncT‘^\0L\0valueq\0~\0xpt\0$41fce3e9-f44f-4ce6-a24b-7186df9a826dsq\0~\0	w\0\0k∂V√Bxsr\0%java.util.Collections$UnmodifiableSetÄí—èõÄU\0\0xr\0,java.util.Collections$UnmodifiableCollectionB\0ÄÀ^˜\0L\0ct\0Ljava/util/Collection;xpsr\0java.util.LinkedHashSetÿl◊Zï›*\0\0xr\0java.util.HashSet∫DÖïñ∏∑4\0\0xpw\0\0\0?@\0\0\0\0\0t\0appxt\0bearert\0$5ec7a7d4-9088-4d34-9c6d-a333bc013a0f','8502edc3f4c61f1000b1932e6a3756f1','admin','client','¨Ì\0sr\0Aorg.springframework.security.oauth2.provider.OAuth2AuthenticationΩ@bR\0L\0\rstoredRequestt\0<Lorg/springframework/security/oauth2/provider/OAuth2Request;L\0userAuthenticationt\02Lorg/springframework/security/core/Authentication;xr\0Gorg.springframework.security.authentication.AbstractAuthenticationToken”™(~nGd\0Z\0\rauthenticatedL\0authoritiest\0Ljava/util/Collection;L\0detailst\0Ljava/lang/Object;xp\0sr\0&java.util.Collections$UnmodifiableList¸%1µÏé\0L\0listt\0Ljava/util/List;xr\0,java.util.Collections$UnmodifiableCollectionB\0ÄÀ^˜\0L\0cq\0~\0xpsr\0java.util.ArrayListxÅ“ô«aù\0I\0sizexp\0\0\0w\0\0\0sr\0Borg.springframework.security.core.authority.SimpleGrantedAuthority\0\0\0\0\0\0ö\0L\0rolet\0Ljava/lang/String;xpt\0ADMINxq\0~\0psr\0:org.springframework.security.oauth2.provider.OAuth2Request\0\0\0\0\0\0\0\0Z\0approvedL\0authoritiesq\0~\0L\0\nextensionst\0Ljava/util/Map;L\0redirectUriq\0~\0L\0refresht\0;Lorg/springframework/security/oauth2/provider/TokenRequest;L\0resourceIdst\0Ljava/util/Set;L\0\rresponseTypesq\0~\0xr\08org.springframework.security.oauth2.provider.BaseRequest6(z>£qiΩ\0L\0clientIdq\0~\0L\0requestParametersq\0~\0L\0scopeq\0~\0xpt\0clientsr\0%java.util.Collections$UnmodifiableMapÒ•®˛tıB\0L\0mq\0~\0xpsr\0java.util.HashMap⁄¡√`—\0F\0\nloadFactorI\0	thresholdxp?@\0\0\0\0\0w\0\0\0\0\0\0t\0\ngrant_typet\0passwordt\0	client_idt\0clientt\0usernamet\0adminxsr\0%java.util.Collections$UnmodifiableSetÄí—èõÄU\0\0xq\0~\0	sr\0java.util.LinkedHashSetÿl◊Zï›*\0\0xr\0java.util.HashSet∫DÖïñ∏∑4\0\0xpw\0\0\0?@\0\0\0\0\0t\0appxsq\0~\0%w\0\0\0?@\0\0\0\0\0sq\0~\0\rt\0clientxsq\0~\0\Z?@\0\0\0\0\0\0w\0\0\0\0\0\0\0xppsq\0~\0%w\0\0\0?@\0\0\0\0\0t\0testxsq\0~\0%w\0\0\0?@\0\0\0\0\0\0xsr\0Oorg.springframework.security.authentication.UsernamePasswordAuthenticationToken\0\0\0\0\0\0ö\0L\0credentialsq\0~\0L\0	principalq\0~\0xq\0~\0sq\0~\0sq\0~\0\0\0\0w\0\0\0q\0~\0xq\0~\02sr\0java.util.LinkedHashMap4¿N\\l¿˚\0Z\0accessOrderxq\0~\0\Z?@\0\0\0\0\0w\0\0\0\0\0\0t\0\rclient_secrett\0secretq\0~\0q\0~\0q\0~\0q\0~\0q\0~\0 q\0~\0!x\0psr\02org.springframework.security.core.userdetails.User\0\0\0\0\0\0ö\0Z\0accountNonExpiredZ\0accountNonLockedZ\0credentialsNonExpiredZ\0enabledL\0authoritiesq\0~\0L\0passwordq\0~\0L\0usernameq\0~\0xpsq\0~\0\"sr\0java.util.TreeSet›òPìïÌá[\0\0xpsr\0Forg.springframework.security.core.userdetails.User$AuthorityComparator\0\0\0\0\0\0ö\0\0xpw\0\0\0q\0~\0xpq\0~\0!','58983714f7a2b3e82203b46d6c81d1d8');

/*Table structure for table `oauth_approvals` */

CREATE TABLE `oauth_approvals` (
  `userId` varchar(256) DEFAULT NULL,
  `clientId` varchar(256) DEFAULT NULL,
  `scope` varchar(256) DEFAULT NULL,
  `status` varchar(10) DEFAULT NULL,
  `expiresAt` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `lastModifiedAt` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `oauth_approvals` */

/*Table structure for table `oauth_client_details` */

CREATE TABLE `oauth_client_details` (
  `client_id` varchar(256) NOT NULL,
  `resource_ids` varchar(256) DEFAULT NULL,
  `client_secret` varchar(256) DEFAULT NULL,
  `scope` varchar(256) DEFAULT NULL,
  `authorized_grant_types` varchar(256) DEFAULT NULL,
  `web_server_redirect_uri` varchar(256) DEFAULT NULL,
  `authorities` varchar(256) DEFAULT NULL,
  `access_token_validity` int(11) DEFAULT NULL,
  `refresh_token_validity` int(11) DEFAULT NULL,
  `additional_information` varchar(4096) DEFAULT NULL,
  `autoapprove` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`client_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `oauth_client_details` */

insert  into `oauth_client_details`(`client_id`,`resource_ids`,`client_secret`,`scope`,`authorized_grant_types`,`web_server_redirect_uri`,`authorities`,`access_token_validity`,`refresh_token_validity`,`additional_information`,`autoapprove`) values ('client','test','secret','app','password,refresh_token',NULL,'client',1200,6000,NULL,'false'),('client1','test','secret1','app1','client_credentials,refresh_token',NULL,'CLIENT1,ROLE_CLIENT',120,600,NULL,'false'),('dev','test','dev','app','password,client_credentials,authorization_code,refresh_token','http://www.baidu.com','',3600,3600,'{\"country\":\"CN\",\"country_code\":\"086\"}','false');

/*Table structure for table `oauth_client_token` */

CREATE TABLE `oauth_client_token` (
  `token_id` varchar(256) DEFAULT NULL,
  `token` blob,
  `authentication_id` varchar(256) NOT NULL,
  `user_name` varchar(256) DEFAULT NULL,
  `client_id` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`authentication_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `oauth_client_token` */

/*Table structure for table `oauth_code` */

CREATE TABLE `oauth_code` (
  `code` varchar(256) DEFAULT NULL,
  `authentication` blob
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `oauth_code` */

/*Table structure for table `oauth_refresh_token` */

CREATE TABLE `oauth_refresh_token` (
  `token_id` varchar(256) DEFAULT NULL,
  `token` blob,
  `authentication` blob
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `oauth_refresh_token` */

insert  into `oauth_refresh_token`(`token_id`,`token`,`authentication`) values ('58983714f7a2b3e82203b46d6c81d1d8','¨Ì\0sr\0Lorg.springframework.security.oauth2.common.DefaultExpiringOAuth2RefreshToken/ﬂGcù–…∑\0L\0\nexpirationt\0Ljava/util/Date;xr\0Dorg.springframework.security.oauth2.common.DefaultOAuth2RefreshTokens·\ncT‘^\0L\0valuet\0Ljava/lang/String;xpt\0$41fce3e9-f44f-4ce6-a24b-7186df9a826dsr\0java.util.DatehjÅKYt\0\0xpw\0\0k∂V√Bx','¨Ì\0sr\0Aorg.springframework.security.oauth2.provider.OAuth2AuthenticationΩ@bR\0L\0\rstoredRequestt\0<Lorg/springframework/security/oauth2/provider/OAuth2Request;L\0userAuthenticationt\02Lorg/springframework/security/core/Authentication;xr\0Gorg.springframework.security.authentication.AbstractAuthenticationToken”™(~nGd\0Z\0\rauthenticatedL\0authoritiest\0Ljava/util/Collection;L\0detailst\0Ljava/lang/Object;xp\0sr\0&java.util.Collections$UnmodifiableList¸%1µÏé\0L\0listt\0Ljava/util/List;xr\0,java.util.Collections$UnmodifiableCollectionB\0ÄÀ^˜\0L\0cq\0~\0xpsr\0java.util.ArrayListxÅ“ô«aù\0I\0sizexp\0\0\0w\0\0\0sr\0Borg.springframework.security.core.authority.SimpleGrantedAuthority\0\0\0\0\0\0ö\0L\0rolet\0Ljava/lang/String;xpt\0ADMINxq\0~\0psr\0:org.springframework.security.oauth2.provider.OAuth2Request\0\0\0\0\0\0\0\0Z\0approvedL\0authoritiesq\0~\0L\0\nextensionst\0Ljava/util/Map;L\0redirectUriq\0~\0L\0refresht\0;Lorg/springframework/security/oauth2/provider/TokenRequest;L\0resourceIdst\0Ljava/util/Set;L\0\rresponseTypesq\0~\0xr\08org.springframework.security.oauth2.provider.BaseRequest6(z>£qiΩ\0L\0clientIdq\0~\0L\0requestParametersq\0~\0L\0scopeq\0~\0xpt\0clientsr\0%java.util.Collections$UnmodifiableMapÒ•®˛tıB\0L\0mq\0~\0xpsr\0java.util.HashMap⁄¡√`—\0F\0\nloadFactorI\0	thresholdxp?@\0\0\0\0\0w\0\0\0\0\0\0t\0\ngrant_typet\0passwordt\0	client_idt\0clientt\0usernamet\0adminxsr\0%java.util.Collections$UnmodifiableSetÄí—èõÄU\0\0xq\0~\0	sr\0java.util.LinkedHashSetÿl◊Zï›*\0\0xr\0java.util.HashSet∫DÖïñ∏∑4\0\0xpw\0\0\0?@\0\0\0\0\0t\0appxsq\0~\0%w\0\0\0?@\0\0\0\0\0sq\0~\0\rt\0clientxsq\0~\0\Z?@\0\0\0\0\0\0w\0\0\0\0\0\0\0xppsq\0~\0%w\0\0\0?@\0\0\0\0\0t\0testxsq\0~\0%w\0\0\0?@\0\0\0\0\0\0xsr\0Oorg.springframework.security.authentication.UsernamePasswordAuthenticationToken\0\0\0\0\0\0ö\0L\0credentialsq\0~\0L\0	principalq\0~\0xq\0~\0sq\0~\0sq\0~\0\0\0\0w\0\0\0q\0~\0xq\0~\02sr\0java.util.LinkedHashMap4¿N\\l¿˚\0Z\0accessOrderxq\0~\0\Z?@\0\0\0\0\0w\0\0\0\0\0\0t\0\rclient_secrett\0secretq\0~\0q\0~\0q\0~\0q\0~\0q\0~\0 q\0~\0!x\0psr\02org.springframework.security.core.userdetails.User\0\0\0\0\0\0ö\0Z\0accountNonExpiredZ\0accountNonLockedZ\0credentialsNonExpiredZ\0enabledL\0authoritiesq\0~\0L\0passwordq\0~\0L\0usernameq\0~\0xpsq\0~\0\"sr\0java.util.TreeSet›òPìïÌá[\0\0xpsr\0Forg.springframework.security.core.userdetails.User$AuthorityComparator\0\0\0\0\0\0ö\0\0xpw\0\0\0q\0~\0xpq\0~\0!'),('b9dc66d324807a730162338bb75714f2','¨Ì\0sr\0Lorg.springframework.security.oauth2.common.DefaultExpiringOAuth2RefreshToken/ﬂGcù–…∑\0L\0\nexpirationt\0Ljava/util/Date;xr\0Dorg.springframework.security.oauth2.common.DefaultOAuth2RefreshTokens·\ncT‘^\0L\0valuet\0Ljava/lang/String;xpt\0$9b45e794-1e13-47a8-9597-55b79e18557fsr\0java.util.DatehjÅKYt\0\0xpw\0\0k∂Y€4x','¨Ì\0sr\0Aorg.springframework.security.oauth2.provider.OAuth2AuthenticationΩ@bR\0L\0\rstoredRequestt\0<Lorg/springframework/security/oauth2/provider/OAuth2Request;L\0userAuthenticationt\02Lorg/springframework/security/core/Authentication;xr\0Gorg.springframework.security.authentication.AbstractAuthenticationToken”™(~nGd\0Z\0\rauthenticatedL\0authoritiest\0Ljava/util/Collection;L\0detailst\0Ljava/lang/Object;xp\0sr\0&java.util.Collections$UnmodifiableList¸%1µÏé\0L\0listt\0Ljava/util/List;xr\0,java.util.Collections$UnmodifiableCollectionB\0ÄÀ^˜\0L\0cq\0~\0xpsr\0java.util.ArrayListxÅ“ô«aù\0I\0sizexp\0\0\0w\0\0\0sr\0Borg.springframework.security.core.authority.SimpleGrantedAuthority\0\0\0\0\0\0ö\0L\0rolet\0Ljava/lang/String;xpt\0ROLE_ANONYMOUSxq\0~\0psr\0:org.springframework.security.oauth2.provider.OAuth2Request\0\0\0\0\0\0\0\0Z\0approvedL\0authoritiesq\0~\0L\0\nextensionst\0Ljava/util/Map;L\0redirectUriq\0~\0L\0refresht\0;Lorg/springframework/security/oauth2/provider/TokenRequest;L\0resourceIdst\0Ljava/util/Set;L\0\rresponseTypesq\0~\0xr\08org.springframework.security.oauth2.provider.BaseRequest6(z>£qiΩ\0L\0clientIdq\0~\0L\0requestParametersq\0~\0L\0scopeq\0~\0xpt\0clientsr\0%java.util.Collections$UnmodifiableMapÒ•®˛tıB\0L\0mq\0~\0xpsr\0java.util.HashMap⁄¡√`—\0F\0\nloadFactorI\0	thresholdxp?@\0\0\0\0\0w\0\0\0\0\0\0t\0\ngrant_typet\0passwordt\0	client_idt\0clientt\0usernamet\0userxsr\0%java.util.Collections$UnmodifiableSetÄí—èõÄU\0\0xq\0~\0	sr\0java.util.LinkedHashSetÿl◊Zï›*\0\0xr\0java.util.HashSet∫DÖïñ∏∑4\0\0xpw\0\0\0?@\0\0\0\0\0t\0appxsq\0~\0%w\0\0\0?@\0\0\0\0\0sq\0~\0\rt\0clientxsq\0~\0\Z?@\0\0\0\0\0\0w\0\0\0\0\0\0\0xppsq\0~\0%w\0\0\0?@\0\0\0\0\0t\0testxsq\0~\0%w\0\0\0?@\0\0\0\0\0\0xsr\0Oorg.springframework.security.authentication.UsernamePasswordAuthenticationToken\0\0\0\0\0\0ö\0L\0credentialsq\0~\0L\0	principalq\0~\0xq\0~\0sq\0~\0sq\0~\0\0\0\0w\0\0\0q\0~\0xq\0~\02sr\0java.util.LinkedHashMap4¿N\\l¿˚\0Z\0accessOrderxq\0~\0\Z?@\0\0\0\0\0w\0\0\0\0\0\0t\0\rclient_secrett\0secretq\0~\0q\0~\0q\0~\0q\0~\0q\0~\0 q\0~\0!x\0psr\02org.springframework.security.core.userdetails.User\0\0\0\0\0\0ö\0Z\0accountNonExpiredZ\0accountNonLockedZ\0credentialsNonExpiredZ\0enabledL\0authoritiesq\0~\0L\0passwordq\0~\0L\0usernameq\0~\0xpsq\0~\0\"sr\0java.util.TreeSet›òPìïÌá[\0\0xpsr\0Forg.springframework.security.core.userdetails.User$AuthorityComparator\0\0\0\0\0\0ö\0\0xpw\0\0\0q\0~\0xpq\0~\0!');

/*Table structure for table `operator` */

CREATE TABLE `operator` (
  `id` varchar(255) NOT NULL,
  `account` varchar(255) NOT NULL,
  `create_date` datetime NOT NULL,
  `email` varchar(64) DEFAULT NULL,
  `imagepath` varchar(256) DEFAULT NULL,
  `is_del` bit(1) DEFAULT NULL,
  `last_login_time` datetime DEFAULT NULL,
  `modify_date` datetime DEFAULT NULL,
  `name` varchar(64) NOT NULL,
  `password` varchar(128) NOT NULL,
  `realname` varchar(64) NOT NULL,
  `status` int(11) NOT NULL,
  `create_user` varchar(255) DEFAULT NULL,
  `modify_user` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_tbxaw5ob54pp6xhikgxa9lig1` (`account`),
  KEY `FKcmtdwox3fmssv2g8vpwuo74ga` (`create_user`),
  KEY `FKmhgoypmwjt5i2s0f35q0rjsfb` (`modify_user`),
  CONSTRAINT `FKcmtdwox3fmssv2g8vpwuo74ga` FOREIGN KEY (`create_user`) REFERENCES `operator` (`id`),
  CONSTRAINT `FKmhgoypmwjt5i2s0f35q0rjsfb` FOREIGN KEY (`modify_user`) REFERENCES `operator` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Data for the table `operator` */

insert  into `operator`(`id`,`account`,`create_date`,`email`,`imagepath`,`is_del`,`last_login_time`,`modify_date`,`name`,`password`,`realname`,`status`,`create_user`,`modify_user`) values ('1','admin','2018-09-01 00:00:00','1','1','','2019-02-19 17:53:50',NULL,'admin','e10adc3949ba59abbe56e057f20f883e','admin',1,NULL,NULL);

/*Table structure for table `role` */

CREATE TABLE `role` (
  `id` varchar(255) NOT NULL,
  `comment` varchar(64) DEFAULT NULL,
  `create_date` datetime NOT NULL,
  `modify_date` datetime DEFAULT NULL,
  `name` varchar(64) NOT NULL,
  `status` int(11) NOT NULL,
  `creator_id` varchar(255) DEFAULT NULL,
  `modifier_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_8sewwnpamngi6b1dwaa88askk` (`name`),
  KEY `FK9o2atbwcomyd6i5lqlp6chjyf` (`creator_id`),
  KEY `FKqiyx74wskvksalihy4x58ksd` (`modifier_id`),
  CONSTRAINT `FK9o2atbwcomyd6i5lqlp6chjyf` FOREIGN KEY (`creator_id`) REFERENCES `operator` (`id`),
  CONSTRAINT `FKqiyx74wskvksalihy4x58ksd` FOREIGN KEY (`modifier_id`) REFERENCES `operator` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Data for the table `role` */

insert  into `role`(`id`,`comment`,`create_date`,`modify_date`,`name`,`status`,`creator_id`,`modifier_id`) values ('1','ADMIN','2018-09-01 00:00:00',NULL,'ADMIN',1,NULL,NULL);

/*Table structure for table `role_competence` */

CREATE TABLE `role_competence` (
  `role_id` varchar(255) NOT NULL,
  `competence_id` varchar(255) NOT NULL,
  PRIMARY KEY (`role_id`,`competence_id`),
  KEY `FKppksjo26gyn15cnj2h77o2r7n` (`competence_id`),
  CONSTRAINT `FKmm4utkh72hy032gpqtukj5dh9` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`),
  CONSTRAINT `FKppksjo26gyn15cnj2h77o2r7n` FOREIGN KEY (`competence_id`) REFERENCES `competence` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Data for the table `role_competence` */

/*Table structure for table `role_operator` */

CREATE TABLE `role_operator` (
  `role_id` varchar(255) NOT NULL,
  `operator_id` varchar(255) NOT NULL,
  PRIMARY KEY (`role_id`,`operator_id`),
  KEY `FKdem52q5rygapsevyiqgp86ywa` (`operator_id`),
  CONSTRAINT `FKdem52q5rygapsevyiqgp86ywa` FOREIGN KEY (`operator_id`) REFERENCES `operator` (`id`),
  CONSTRAINT `FKqkf6cps6w7d76istve3qdnqoq` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Data for the table `role_operator` */

insert  into `role_operator`(`role_id`,`operator_id`) values ('1','1');

/*Table structure for table `users` */

CREATE TABLE `users` (
  `id` varchar(255) NOT NULL,
  `city` varchar(64) DEFAULT NULL,
  `cover` varchar(256) DEFAULT NULL,
  `create_date` datetime NOT NULL,
  `last_login_date` datetime DEFAULT NULL,
  `modify_date` datetime DEFAULT NULL,
  `nick_name` varchar(64) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `sex` int(11) DEFAULT NULL,
  `user_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Data for the table `users` */

insert  into `users`(`id`,`city`,`cover`,`create_date`,`last_login_date`,`modify_date`,`nick_name`,`password`,`sex`,`user_name`) values ('1','1','1','2018-09-01 00:00:00',NULL,NULL,'user','$2a$10$LquzXaFfLUI/vJ4E.sL7xeQrX1PHEfBWPSnl8zHLRmLzCaUppgopq',1,'user'),('2','2','2','2019-05-23 00:00:00',NULL,NULL,'user1','1111',1,'user1');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
