CREATE TABLE users_entity (
    id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL,
    role VARCHAR(20) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE user_entity (
    userID INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    name VARCHAR(100),
    bio TEXT,
    profilePicture VARCHAR(255)
);

CREATE TABLE follows (
    following_user_id INT,
    followed_user_id INT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (following_user_id, followed_user_id),
    FOREIGN KEY (following_user_id) REFERENCES user_entity(userID),
    FOREIGN KEY (followed_user_id) REFERENCES user_entity(userID)
);

CREATE TABLE Post (
    postID INT PRIMARY KEY AUTO_INCREMENT,
    userID INT NOT NULL,
    content TEXT,
    mediaType VARCHAR(20),
    mediaURL VARCHAR(255),
    timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (userID) REFERENCES user_entity(userID)
);

CREATE TABLE Comment (
    commentID INT PRIMARY KEY AUTO_INCREMENT,
    postID INT NOT NULL,
    userID INT NOT NULL,
    content TEXT NOT NULL,
    timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (postID) REFERENCES Post(postID),
    FOREIGN KEY (userID) REFERENCES user_entity(userID)
);

CREATE TABLE Likes (
    likeID INT PRIMARY KEY AUTO_INCREMENT,
    postID INT,
    commentID INT,
    userID INT NOT NULL,
    timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (postID) REFERENCES Post(postID),
    FOREIGN KEY (commentID) REFERENCES Comment(commentID),
    FOREIGN KEY (userID) REFERENCES user_entity(userID)
);

CREATE TABLE Message (
    messageID INT PRIMARY KEY AUTO_INCREMENT,
    senderID INT NOT NULL,
    receiverID INT NOT NULL,
    content TEXT NOT NULL,
    timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (senderID) REFERENCES user_entity(userID),
    FOREIGN KEY (receiverID) REFERENCES user_entity(userID)
);

CREATE TABLE Friendship (
    friendshipID INT PRIMARY KEY AUTO_INCREMENT,
    userID1 INT NOT NULL,
    userID2 INT NOT NULL,
    timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (userID1) REFERENCES user_entity(userID),
    FOREIGN KEY (userID2) REFERENCES user_entity(userID)
);

INSERT INTO users_entity (id, username, role, created_at)
VALUES
(1, 'adminUser', 'admin', '2025-01-01 10:00:00'),
(2, 'moderatorUser', 'moderator', '2025-01-01 11:00:00'),
(3, 'regularUser1', 'user', '2025-01-01 12:00:00'),
(4, 'regularUser2', 'user', '2025-01-01 12:30:00');

INSERT INTO user_entity (userID, username, email, password, name, bio, profilePicture)
VALUES
(1, 'adminUser', 'admin@example.com', 'securepassword', 'Admin User', 'I manage the platform.', 'admin_pic.png'),
(2, 'moderatorUser', 'mod@example.com', 'modpassword', 'Moderator User', 'I moderate content here.', 'mod_pic.png'),
(3, 'regularUser1', 'user1@example.com', 'user1password', 'User One', 'I love posting pictures.', 'user1_pic.png'),
(4, 'regularUser2', 'user2@example.com', 'user2password', 'User Two', 'Sharing my life here.', 'user2_pic.png');

INSERT INTO follows (following_user_id, followed_user_id, created_at)
VALUES
(3, 1, '2025-01-01 13:00:00'),
(3, 2, '2025-01-01 13:05:00'),
(4, 3, '2025-01-01 13:10:00'),
(1, 4, '2025-01-01 13:15:00');

INSERT INTO Post (postID, userID, content, mediaType, mediaURL, timestamp)
VALUES
(1, 3, 'Check out this amazing sunset!', 'image', 'sunset.jpg', '2025-01-01 14:00:00'),
(2, 4, 'My first post on this platform!', 'text', NULL, '2025-01-01 14:30:00'),
(3, 1, 'Platform updates: New features coming soon!', 'text', NULL, '2025-01-01 15:00:00');

INSERT INTO Comment (commentID, postID, userID, content, timestamp)
VALUES
(1, 1, 4, 'Wow, this is beautiful!', '2025-01-01 14:10:00'),
(2, 2, 3, 'Welcome! Great to have you here.', '2025-01-01 14:40:00'),
(3, 3, 2, 'Looking forward to the updates!', '2025-01-01 15:10:00');

INSERT INTO Likes (likeID, postID, commentID, userID, timestamp)
VALUES
(1, 1, NULL, 4, '2025-01-01 14:15:00'), -- User 4 liked Post 1
(2, NULL, 1, 3, '2025-01-01 14:20:00'), -- User 3 liked Comment 1
(3, 2, NULL, 1, '2025-01-01 14:50:00'); -- Admin liked Post 2

INSERT INTO Message (messageID, senderID, receiverID, content, timestamp)
VALUES
(1, 3, 4, 'Hey, nice to meet you!', '2025-01-01 13:30:00'),
(2, 4, 3, 'Thanks! Nice to meet you too.', '2025-01-01 13:40:00'),
(3, 1, 3, 'Please follow community guidelines.', '2025-01-01 15:30:00');

INSERT INTO Friendship (friendshipID, userID1, userID2, timestamp)
VALUES
(1, 3, 4, '2025-01-01 13:50:00'),
(2, 1, 2, '2025-01-01 13:55:00');

