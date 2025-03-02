insert into users(email, password, nickname, phone_number, profile_image, followers_count, followings_count)
values ('email@localhost', 'password', 'nickname', '010-1234-5678', 'profile_image', 0, 0);


insert into user_roles(user_id, roles)
values (1, 'ROLE_USER');
