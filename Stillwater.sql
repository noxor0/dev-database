
CREATE TABLE ItemCategory (
	category VARCHAR(25) PRIMARY KEY
);

CREATE TABLE Item (
	itemId INT(11) auto_increment PRIMARY KEY,
	`name` VARCHAR(25) NOT NULL,
    descriptioarg0n VARCHAR(100),
    price DECIMAL(8, 2),
    `condition` VARCHAR(100),
    category VARCHAR(25) references ItemCategory(category)
);

CREATE TABLE `Client` (
	clientId INT(11) auto_increment PRIMARY KEY,
	lastName VARCHAR(25) NOT NULL,
    firstName VARCHAR(25) NOT NULL,
    middleInitial CHAR(1),
    streetAddress VARCHAR(50) NOT NULL,
    city VARCHAR(25) NOT NULL,
    state CHAR(2) NOT NULL,
    zipcode CHAR(5) NOT NULL
);

CREATE TABLE Sale (
	saleId INT(11) auto_increment PRIMARY KEY,
	commissionPaid DECIMAL(8, 2) NOT NULL,
    price DECIMAL(8, 2) NOT NULL,
    clientId INT(11) REFERENCES `Client`(clientId),
    itemId INT(11) REFERENCES Item(itemId)
);

CREATE TABLE Purchase (
	purchaseId INT(11) auto_increment PRIMARY KEY,
    cost DECIMAL(8,2) NOT NULL,
    `condition` VARCHAR(100),
	clientId INT(11) REFERENCES `Client`(clientId),
    itemId INT(11) REFERENCES Item(itemId)
);

INSERT INTO ItemCategory
VALUES 
('Books') ,
('Furniture') , 
('Jewellery');