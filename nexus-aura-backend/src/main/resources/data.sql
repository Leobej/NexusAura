CREATE TABLE users (
    id UUID PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password_hash TEXT NOT NULL,
    full_name VARCHAR(100),
    bio TEXT,
    profile_picture_url TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE posts (
    id UUID PRIMARY KEY,
    user_id UUID REFERENCES users(id),
    content TEXT,
    media_url TEXT,
    media_type VARCHAR(10), -- e.g., 'image', 'video'
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE comments (
    id UUID PRIMARY KEY,
    post_id UUID REFERENCES posts(id),
    user_id UUID REFERENCES users(id),
    content TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE likes (
    user_id UUID REFERENCES users(id),
    post_id UUID REFERENCES posts(id),
    liked_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (user_id, post_id)
);

CREATE TABLE follows (
    follower_id UUID REFERENCES users(id),
    followee_id UUID REFERENCES users(id),
    followed_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (follower_id, followee_id)
);

INSERT INTO users (id, username, email, password_hash, full_name, bio, profile_picture_url)
VALUES
  ('11111111-1111-1111-1111-111111111111', 'alice', 'alice@example.com', 'hashed_password1', 'Alice Smith', 'Bio for Alice', 'http://example.com/alice.jpg'),
  ('22222222-2222-2222-2222-222222222222', 'bob', 'bob@example.com', 'hashed_password2', 'Bob Johnson', 'Bio for Bob', 'http://example.com/bob.jpg'),
  ('33333333-3333-3333-3333-333333333333', 'leo', 'leonardmihaibejgu@yahoo.com', 'hashed_password3', 'Leonard Bej', 'Bio for Leo', 'http://example.com/leo.jpg');

-- Insert default posts
INSERT INTO posts (id, user_id, content, media_url, media_type)
VALUES
  ('33333333-3333-3333-3333-333333333333', '11111111-1111-1111-1111-111111111111', 'Alice''s first post', 'http://example.com/media1.jpg', 'image'),
  ('44444444-4444-4444-4444-444444444444', '22222222-2222-2222-2222-222222222222', 'Bob''s first post', 'http://example.com/media2.jpg', 'image');

-- Insert default comments
INSERT INTO comments (id, post_id, user_id, content)
VALUES
  ('55555555-5555-5555-5555-555555555555', '33333333-3333-3333-3333-333333333333', '22222222-2222-2222-2222-222222222222', 'Nice post, Alice!'),
  ('66666666-6666-6666-6666-666666666666', '44444444-4444-4444-4444-444444444444', '11111111-1111-1111-1111-111111111111', 'Thanks, Bob!');

-- Insert default likes
INSERT INTO likes (user_id, post_id)
VALUES
  ('11111111-1111-1111-1111-111111111111', '44444444-4444-4444-4444-444444444444'),
  ('22222222-2222-2222-2222-222222222222', '33333333-3333-3333-3333-333333333333');

-- Insert default follows
INSERT INTO follows (follower_id, followee_id)
VALUES
  ('11111111-1111-1111-1111-111111111111', '22222222-2222-2222-2222-222222222222'),
  ('22222222-2222-2222-2222-222222222222', '11111111-1111-1111-1111-111111111111');
