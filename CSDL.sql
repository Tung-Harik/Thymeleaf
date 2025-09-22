CREATE DATABASE CategoryDB;
GO
USE CategoryDB;
GO


CREATE TABLE Categories (
id INT IDENTITY(1,1) PRIMARY KEY,
name NVARCHAR(100) NOT NULL,
description NVARCHAR(500) NULL,
created_at DATETIME2 DEFAULT SYSDATETIME(),
updated_at DATETIME2 NULL
);


INSERT INTO Categories(name, description)
VALUES (N'Skincare', N'Sản phẩm chăm sóc da'),
(N'Makeup', N'Trang điểm'),
(N'Haircare', N'Chăm sóc tóc');