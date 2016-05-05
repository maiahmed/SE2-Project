-- phpMyAdmin SQL Dump
-- version 4.1.14
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Apr 28, 2016 at 02:45 PM
-- Server version: 5.6.17
-- PHP Version: 5.5.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `se2firstapp`
--

-- --------------------------------------------------------

--
-- Table structure for table `actions`
--

CREATE TABLE IF NOT EXISTS `actions` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `pid` int(11) NOT NULL,
  `type` int(11) NOT NULL,
  `checkin` int(11) DEFAULT NULL,
  `CheckInName` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=7 ;

--
-- Dumping data for table `actions`
--

INSERT INTO `actions` (`ID`, `pid`, `type`, `checkin`, `CheckInName`) VALUES
(1, 6, 3, 1, 'giza'),
(2, 6, 3, 2, 'cairo'),
(3, 6, 1, 2, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `checkin`
--

CREATE TABLE IF NOT EXISTS `checkin` (
  `PlaceName` varchar(50) NOT NULL,
  `UserID` int(11) NOT NULL,
  `CheckInID` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`CheckInID`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=4 ;

--
-- Dumping data for table `checkin`
--

INSERT INTO `checkin` (`PlaceName`, `UserID`, `CheckInID`) VALUES
('giza', 6, 1),
('cairo', 6, 2),
('mac', 3, 3);

-- --------------------------------------------------------

--
-- Table structure for table `comments`
--

CREATE TABLE IF NOT EXISTS `comments` (
  `commentID` int(11) NOT NULL AUTO_INCREMENT,
  `checkinID` int(11) NOT NULL,
  `userID` int(11) NOT NULL,
  `commentText` varchar(100) NOT NULL,
  PRIMARY KEY (`commentID`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=12 ;

--
-- Dumping data for table `comments`
--

INSERT INTO `comments` (`commentID`, `checkinID`, `userID`, `commentText`) VALUES
(11, 2, 3, 'nice');

-- --------------------------------------------------------

--
-- Table structure for table `follower`
--

CREATE TABLE IF NOT EXISTS `follower` (
  `idFollower1` int(11) NOT NULL,
  `idFollower2` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `follower`
--

INSERT INTO `follower` (`idFollower1`, `idFollower2`) VALUES
(3, 1),
(3, 4),
(3, 5),
(3, 6),
(3, 7),
(5, 7),
(1, 2),
(1, 2),
(2, 4),
(6, 3);

-- --------------------------------------------------------

--
-- Table structure for table `likes`
--

CREATE TABLE IF NOT EXISTS `likes` (
  `userid` int(11) NOT NULL,
  `checkid` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `likes`
--

INSERT INTO `likes` (`userid`, `checkid`) VALUES
(6, 2);

-- --------------------------------------------------------

--
-- Table structure for table `notifications`
--

CREATE TABLE IF NOT EXISTS `notifications` (
  `notificationID` int(11) NOT NULL AUTO_INCREMENT,
  `performerID` int(11) NOT NULL,
  `receiverID` int(11) NOT NULL,
  `notificationType` int(11) NOT NULL,
  `checkInID` int(11) NOT NULL,
  PRIMARY KEY (`notificationID`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=13 ;

--
-- Dumping data for table `notifications`
--

INSERT INTO `notifications` (`notificationID`, `performerID`, `receiverID`, `notificationType`, `checkInID`) VALUES
(1, 6, 6, 1, 2),
(6, 3, 6, 2, 2);

-- --------------------------------------------------------

--
-- Table structure for table `notifiedusers`
--

CREATE TABLE IF NOT EXISTS `notifiedusers` (
  `checkInID` int(11) NOT NULL,
  `UserID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `notifiedusers`
--

INSERT INTO `notifiedusers` (`checkInID`, `UserID`) VALUES
(2, 6),
(2, 3);

-- --------------------------------------------------------

--
-- Table structure for table `places`
--

CREATE TABLE IF NOT EXISTS `places` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(300) NOT NULL,
  `description` text NOT NULL,
  `lat` double NOT NULL,
  `long` double NOT NULL,
  `ownerID` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=4 ;

--
-- Dumping data for table `places`
--

INSERT INTO `places` (`id`, `name`, `description`, `lat`, `long`, `ownerID`) VALUES
(1, 'giza', 'good', 10, 20, 2),
(2, 'cairo', 'nice', 11, 111, 6),
(3, 'mac', 'woooow', 0, 0, 3);

-- --------------------------------------------------------

--
-- Table structure for table `savedplaces`
--

CREATE TABLE IF NOT EXISTS `savedplaces` (
  `userID` int(11) NOT NULL,
  `placeID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `savedplaces`
--

INSERT INTO `savedplaces` (`userID`, `placeID`) VALUES
(6, 2),
(6, 2),
(6, 1);

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE IF NOT EXISTS `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(300) NOT NULL,
  `email` varchar(500) NOT NULL,
  `password` varchar(300) NOT NULL,
  `lat` double DEFAULT NULL,
  `lang` double DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `last_place_id` (`lat`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=10 ;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `name`, `email`, `password`, `lat`, `lang`) VALUES
(1, 'mohamed', 'mhmdsamir@gmail.com', '123', 30.0310036, 31.2127736),
(2, 'mohamed', 'mhmdsamir1@gmail.com', '123', 0, NULL),
(3, 'mohamed', 'mhmdsamir91@gmail.com', '123456789', NULL, NULL),
(4, 'mohamed', 'mhmdsamir92@gmail.com', '123456789', NULL, NULL),
(5, 'mohamed', 'm.samir', '123456789', 30, 31),
(6, 'Omar', 'omar', '123', NULL, NULL),
(7, 'mai', 'mai@gmail.com', '123', NULL, NULL),
(8, 'ahmed', 'ahmed@gmail.com', '123', NULL, NULL),
(9, 'amira', 'amira@gmail.com', '12', NULL, NULL);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
