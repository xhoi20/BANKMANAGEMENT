
CREATE TABLE Customer (
                          id BIGINT IDENTITY(1,1) PRIMARY KEY,
                          name NVARCHAR(255) NOT NULL,
                          email NVARCHAR(255) NOT NULL UNIQUE,
                          password NVARCHAR(255) NOT NULL
);

CREATE TABLE BankAccount (
                             id BIGINT IDENTITY(1,1) PRIMARY KEY,
                             accountNumber NVARCHAR(50) NOT NULL UNIQUE,
                             customer_id BIGINT NOT NULL,
                             CONSTRAINT FK_BankAccount_Customer FOREIGN KEY (customer_id)
                                 REFERENCES Customer(id)
                                 ON DELETE CASCADE
);

CREATE TABLE [Transaction] (
                               id BIGINT IDENTITY(1,1) PRIMARY KEY,
                               type NVARCHAR(50) NOT NULL,
                               amount DECIMAL(19,4) NOT NULL,
                               order_date DATETIME2 NOT NULL,
                               fromAccount NVARCHAR(50),
                               toAccount NVARCHAR(50),
                               bank_account_id BIGINT NOT NULL,
                               CONSTRAINT FK_Transaction_BankAccount FOREIGN KEY (bank_account_id)
                                   REFERENCES BankAccount(id)
);

CREATE TABLE BankAccount (
                             id BIGINT IDENTITY(1,1) PRIMARY KEY,
                             accountNumber NVARCHAR(50) NOT NULL UNIQUE,
                             accountType NVARCHAR(50) NOT NULL,
                             balance DECIMAL(19,4) NOT NULL,
                             initialDeposit DECIMAL(19,4) NOT NULL,
                             customer_id BIGINT NOT NULL,
                             CONSTRAINT FK_BankAccount_Customer FOREIGN KEY (customer_id)
                                 REFERENCES Customer(id)
                                 ON DELETE CASCADE
);

CREATE INDEX IX_BankAccount_CustomerId ON BankAccount(customer_id);
CREATE INDEX IX_Transaction_BankAccountId ON [Transaction](bank_account_id);
CREATE INDEX IX_BankAccount_CustomerId ON BankAccount(customer_id);