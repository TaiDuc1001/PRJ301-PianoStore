-- Force drop the database if it exists
USE master;
GO
IF EXISTS (SELECT * FROM sys.databases WHERE name = 'DucPT_SP25')
BEGIN
    ALTER DATABASE DucPT_SP25 SET SINGLE_USER WITH ROLLBACK IMMEDIATE;
    DROP DATABASE DucPT_SP25;
END;
GO

-- Create the database
CREATE DATABASE DucPT_SP25;
GO

-- Use the database
USE DucPT_SP25;
GO

-- Create the Users table (fixed schema)
CREATE TABLE Users (
    id INT IDENTITY(1,1) PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    first_name NVARCHAR(50),
    last_name NVARCHAR(50) NOT NULL,
    is_active BIT DEFAULT 1,
    role VARCHAR(20) DEFAULT 'user' CHECK (role IN ('user', 'admin', 'moderator')),
    isAdmin BIT DEFAULT 0
);
GO

-- Insert sample users
INSERT INTO users (username, password, first_name, last_name, is_active, role, isAdmin) VALUES
('admin', 'admin', 'John', 'Doe', 1, 'admin', 1),
('jane.smith', 'jane', 'Jane', 'Smith', 1, 'user', 0),
('michael_j', 'michael', 'Michael', 'Johnson', 1, 'user', 0),
('alice_w', 'alice', 'Alice', 'Williams', 1, 'user', 0),
('bob_c', 'bob', 'Bob', 'Carter', 1, 'user', 0),
('charlie_b', 'charlie', 'Charlie', 'Brown', 1, 'moderator', 0),
('admin_user_01', 'admin', 'Admin', 'User01', 1, 'admin', 1),
('admin_user_02', 'admin', 'Admin', 'User02', 1, 'admin', 1),
('mod_user_01', 'mod', 'Mod', 'User01', 1, 'moderator', 0),
('mod_user_02', 'mod', 'Mod', 'User02', 1, 'moderator', 0);
GO

-- Select all users
SELECT * FROM users;
GO

-- Create the Courses table
CREATE TABLE Courses (
    name VARCHAR(100) NOT NULL PRIMARY KEY,
    description TEXT,
    tuitionFee DECIMAL(10,2),
    startDate DATE,
    endDate DATE,
    category VARCHAR(50) CHECK(category IN ('Piano', 'Guitar', 'Drawing')),
    quantity INT DEFAULT 10,
    status VARCHAR(10) DEFAULT 'active'
);

-- Insert initial courses
INSERT INTO Courses (name, description, tuitionFee, startDate, endDate, category, quantity)
VALUES
('Piano Basics', 'Introduction to piano playing for beginners.', 500.00, '2024-04-01', '2024-06-30', 'Piano', 10),
('Guitar Fundamentals', 'Learn basic guitar chords and strumming techniques.', 450.00, '2024-05-01', '2024-07-31', 'Guitar', 10),
('Drawing Essentials', 'Discover the basics of drawing and sketching.', 300.00, '2024-03-01', '2024-05-31', 'Drawing', 10),
('Piano Intermediate', 'Build on your piano skills with more complex pieces.', 600.00, '2024-07-01', '2024-09-30', 'Piano', 10),
('Guitar Advanced', 'Improve your guitar skills with advanced techniques.', 700.00, '2024-08-01', '2024-10-31', 'Guitar', 10),
('Drawing Portraits', 'Learn to draw realistic portraits.', 550.00, '2024-06-01', '2024-08-31', 'Drawing', 10);

-- Insert additional 20 courses
INSERT INTO Courses (name, description, tuitionFee, startDate, endDate, category, quantity)
VALUES
('Jazz Piano', 'Explore jazz improvisation techniques on the piano.', 650.00, '2024-09-01', '2024-11-30', 'Piano', 8),
('Rock Guitar', 'Learn to play rock and metal guitar solos.', 600.00, '2024-07-15', '2024-09-15', 'Guitar', 12),
('Digital Art', 'Use digital tools to create stunning artwork.', 750.00, '2024-10-01', '2024-12-31', 'Drawing', 9),
('Classical Piano', 'Master classical pieces from Mozart to Chopin.', 900.00, '2024-06-01', '2024-09-30', 'Piano', 6),
('Fingerstyle Guitar', 'Learn intricate fingerpicking techniques.', 700.00, '2024-08-10', '2024-10-10', 'Guitar', 10),
('Landscape Sketching', 'Develop skills in outdoor and nature drawing.', 450.00, '2024-05-01', '2024-07-30', 'Drawing', 0),
('Pop Piano Hits', 'Play popular songs with simple techniques.', 500.00, '2024-07-01', '2024-09-30', 'Piano', 10),
('Blues Guitar', 'Learn blues scales and improvisation.', 650.00, '2024-09-01', '2024-11-30', 'Guitar', 8),
('Cartoon Illustration', 'Create expressive and fun cartoon drawings.', 600.00, '2024-06-01', '2024-08-31', 'Drawing', 10),
('Advanced Jazz Piano', 'Dive deeper into jazz harmony and improvisation.', 800.00, '2024-10-01', '2025-01-01', 'Piano', 5),
('Electric Guitar Mastery', 'Master the electric guitar and effects.', 850.00, '2024-11-01', '2025-01-31', 'Guitar', 10),
('Figure Drawing', 'Study human anatomy for realistic figure drawing.', 700.00, '2024-07-01', '2024-09-30', 'Drawing', 10),
('Piano for Songwriting', 'Use the piano for composing original music.', 600.00, '2024-06-15', '2024-08-15', 'Piano', 10),
('Country Guitar', 'Learn country-style guitar techniques.', 500.00, '2024-07-01', '2024-09-30', 'Guitar', 10),
('Urban Sketching', 'Capture the essence of cityscapes.', 400.00, '2024-08-01', '2024-10-31', 'Drawing', 8),
('Synth Piano Techniques', 'Explore electronic music and synth piano.', 750.00, '2024-09-01', '2024-11-30', 'Piano', 7),
('Acoustic Guitar Strumming', 'Master different strumming patterns.', 450.00, '2024-06-01', '2024-08-31', 'Guitar', 0),
('Character Design', 'Develop unique characters for games and comics.', 800.00, '2024-07-15', '2024-09-15', 'Drawing', 10);

-- Select all courses
SELECT * FROM Courses;
GO
