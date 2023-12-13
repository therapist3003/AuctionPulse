create database AuctionPulse;
use AuctionPulse;

CREATE TABLE Admin (
    Name VARCHAR(20),
    Nickname VARCHAR(20) PRIMARY KEY,
    Password VARCHAR(20)
);

INSERT INTO Admin VALUES('Admin1', 'adm1', 'adm1@123');

CREATE TABLE Bidder (
    Id INT PRIMARY KEY AUTO_INCREMENT,
    Name VARCHAR(30),
    Nickname VARCHAR(20) UNIQUE,
    City VARCHAR(20),
    DOB DATE,
    Mob BIGINT,
    Password VARCHAR(30)
);

CREATE TABLE Bid (
    ID INT PRIMARY KEY,
    Bidder_Name VARCHAR(30),
    Bid_Price INT,
    Bidder_ID INT,
    FOREIGN KEY (Bidder_ID) REFERENCES Bidder(Id)
);

CREATE TABLE Player (
    ID INT PRIMARY KEY AUTO_INCREMENT,
    Name VARCHAR(30),
    Reserve_Price INT,
    Image BLOB
);

CREATE TABLE SoldPlayer (
    ID INT PRIMARY KEY,
    Name VARCHAR(30),
    Reserve_Price INT,
    Image BLOB,
    Hammer_Price INT,
    Bidder_ID INT,
    Bidder_Name VARCHAR(30),
    FOREIGN KEY (Bidder_ID) REFERENCES Bidder(Id)
);

CREATE TABLE Status (
    Auction VARCHAR(20),
    ID INT,
    Time VARCHAR(10)
);