INSERT INTO users (nickname, email)
    VALUES ('jcosgrove0', 'dmccoole0@networksolutions.com');
INSERT INTO users (nickname, email)
    VALUES ('dtomkinson1', 'aheaffey1@goodreads.com');
INSERT INTO users (nickname, email)
    VALUES ('roclery2', 'nhaine2@slideshare.net');
INSERT INTO users (nickname, email)
    VALUES ('alunbech3', 'dmeadows3@admin.ch');
INSERT INTO users (nickname, email)
    VALUES ('tjantot4', 'eregnard4@spotify.com');
INSERT INTO users (nickname, email)
    VALUES ('asiddons5', 'gnuccitelli5@intel.com');
INSERT INTO users (nickname, email)
    VALUES ('dyuranovev6', 'mrandal6@ted.com');
INSERT INTO users (nickname, email)
    VALUES ('echildes7', 'apizzey7@pbs.org');
INSERT INTO users (nickname, email)
    VALUES ('rgammack8', 'hhanse8@sohu.com');
INSERT INTO users (nickname, email)
    VALUES ('kmccomiskey9', 'ishelmerdine9@mtv.com');

INSERT INTO passwords (password, user_id) VALUES ('SA5h2ReE', 1);
INSERT INTO passwords (password, user_id) VALUES ('qn9EzfuYyL3r', 2);
INSERT INTO passwords (password, user_id) VALUES ('e5mQfDCG', 3);
INSERT INTO passwords (password, user_id) VALUES ('XuoYBVstxJ5', 4);
INSERT INTO passwords (password, user_id) VALUES ('ObL2KqV', 5);
INSERT INTO passwords (password, user_id) VALUES ('GeVveY5R', 6);
INSERT INTO passwords (password, user_id) VALUES ('KqQevJ', 7);
INSERT INTO passwords (password, user_id) VALUES ('apxFcU', 8);
INSERT INTO passwords (password, user_id) VALUES ('icRs83aqEEac', 9);
INSERT INTO passwords (password, user_id) VALUES ('cP950MG', 10);

INSERT INTO trails () VALUES();
INSERT INTO trails () VALUES();
INSERT INTO trails () VALUES();
INSERT INTO trails () VALUES();
INSERT INTO trails () VALUES();
INSERT INTO trails () VALUES();
INSERT INTO trails () VALUES();
INSERT INTO trails () VALUES();
INSERT INTO trails () VALUES();
INSERT INTO trails () VALUES();

INSERT INTO roles (name, user_id) VALUES ('USER', 1);
INSERT INTO roles (name, user_id) VALUES ('PREMIUM_USER', 2);
INSERT INTO roles (name, user_id) VALUES ('USER', 3);
INSERT INTO roles (name, user_id) VALUES ('PREMIUM_USER', 4);
INSERT INTO roles (name, user_id) VALUES ('PREMIUM_USER', 5);
INSERT INTO roles (name, user_id) VALUES ('USER', 6);
INSERT INTO roles (name, user_id) VALUES ('PREMIUM_USER', 7);
INSERT INTO roles (name, user_id) VALUES ('PREMIUM_USER', 8);
INSERT INTO roles (name, user_id) VALUES ('USER', 9);
INSERT INTO roles (name, user_id) VALUES ('PREMIUM_USER', 10);

INSERT INTO users_workouts (user_id, type, date, note, distance, trail_id)
    VALUES (1, 'OTHER', '2020-10-05 22:50:01', 'nibh ligula nec sem duis aliquam convallis nunc proin at', 4231.2, 1);
INSERT INTO users_workouts (user_id, type, date, note, distance, trail_id)
    VALUES (2, 'OTHER', '2015-07-11 03:14:41', NULL, 2118.61, 2);
INSERT INTO users_workouts (user_id, type, date, note, distance, trail_id)
    VALUES (3, 'RUNNING', '2021-02-13 01:55:12', NULL, 4834.44, 3);
INSERT INTO users_workouts (user_id, type, date, note, distance, trail_id)
    VALUES (4, 'WALKING', '2017-01-31 13:37:10', NULL, 2246.45, 4);
INSERT INTO users_workouts (user_id, type, date, note, distance, trail_id)
    VALUES (5, 'OTHER', '2015-01-24 16:26:05', NULL, 1283.56, 5);
INSERT INTO users_workouts (user_id, type, date, note, distance, trail_id)
    VALUES (6, 'WALKING', '2017-05-23 23:56:33', NULL, 1759.38, 6);
INSERT INTO users_workouts (user_id, type, date, note, distance, trail_id)
    VALUES (7, 'HITCH_HIKING', '2017-11-05 05:24:44', 'luctus et ultrices posuere cubilia curae mauris viverra diam', 2785.91, 7);
INSERT INTO users_workouts (user_id, type, date, note, distance, trail_id)
    VALUES (8, 'OTHER', '2019-04-14 00:03:06', 'convallis nulla neque libero convallis eget', 2476.83, 8);
INSERT INTO users_workouts (user_id, type, date, note, distance, trail_id)
    VALUES (9, 'CYCLING', '2012-09-16 13:37:13', 'quam suspendisse potenti nullam porttitor lacus at', 4557.57, 9);
INSERT INTO users_workouts (user_id, type, date, note, distance, trail_id)
    VALUES (10, 'OTHER', '2019-10-27 07:06:39', 'lacus morbi sem mauris laoreet ut rhoncus aliquet', 1714.14, 10);

INSERT INTO trail_coords (latitude, longitude, trail_id)
    VALUES (25.415248, 49.679652, 1);
INSERT INTO trail_coords (latitude, longitude, trail_id)
    VALUES (39.9225394, 65.9214676, 1);
INSERT INTO trail_coords (latitude, longitude, trail_id)
    VALUES (41.4410475, 22.0126949, 3);
INSERT INTO trail_coords (latitude, longitude, trail_id)
    VALUES (42.214405, -8.7357274, 3);
INSERT INTO trail_coords (latitude, longitude, trail_id)
    VALUES (13.5712781, -15.3262905, 3);
INSERT INTO trail_coords (latitude, longitude, trail_id)
    VALUES (27.602752, 107.709916, 3);
INSERT INTO trail_coords (latitude, longitude, trail_id)
    VALUES (40.7388893, 43.9061272, 3);
INSERT INTO trail_coords (latitude, longitude, trail_id)
    VALUES (-7.1108, 113.5265, 3);
INSERT INTO trail_coords (latitude, longitude, trail_id)
    VALUES (16.7742464, 121.0882915, 2);
INSERT INTO trail_coords (latitude, longitude, trail_id)
    VALUES (61.4559668, 23.7382315, 3);
INSERT INTO trail_coords (latitude, longitude, trail_id)
    VALUES (48.6366985, 2.7912279, 2);
INSERT INTO trail_coords (latitude, longitude, trail_id)
    VALUES (6.83249, 124.454363, 1);
INSERT INTO trail_coords (latitude, longitude, trail_id)
    VALUES (5.3523231, 100.3573891, 3);
INSERT INTO trail_coords (latitude, longitude, trail_id)
    VALUES (57.857193, 11.91635, 2);
INSERT INTO trail_coords (latitude, longitude, trail_id)
    VALUES (46.8235919, 16.2881649, 1);
INSERT INTO trail_coords (latitude, longitude, trail_id)
    VALUES (-17.308611, -48.278112, 3);
INSERT INTO trail_coords (latitude, longitude, trail_id)
    VALUES (27.5800122, -109.9297789, 2);
INSERT INTO trail_coords (latitude, longitude, trail_id)
    VALUES (-7.7218793, 113.4785837, 3);
INSERT INTO trail_coords (latitude, longitude, trail_id)
    VALUES (35.9676772, 126.7366293, 1);
INSERT INTO trail_coords (latitude, longitude, trail_id)
    VALUES (29.356803, 113.12873, 3);
INSERT INTO trail_coords (latitude, longitude, trail_id)
    VALUES (33.6909232, 36.3687677, 3);
INSERT INTO trail_coords (latitude, longitude, trail_id)
    VALUES (43.0429124, 1.9038837, 3);
INSERT INTO trail_coords (latitude, longitude, trail_id)
    VALUES (15.3047612, -88.6639228, 2);
INSERT INTO trail_coords (latitude, longitude, trail_id)
    VALUES (34.199479, 119.578364, 2);
INSERT INTO trail_coords (latitude, longitude, trail_id)
    VALUES (58.7075528, 14.1266322, 2);
INSERT INTO trail_coords (latitude, longitude, trail_id)
    VALUES (30.372807, -91.1833043, 3);
INSERT INTO trail_coords (latitude, longitude, trail_id)
    VALUES (53.3527892, 83.67298, 1);
INSERT INTO trail_coords (latitude, longitude, trail_id)
    VALUES (53.1859207, 22.0862303, 3);
INSERT INTO trail_coords (latitude, longitude, trail_id)
    VALUES (14.7172639, 121.0413488, 2);
INSERT INTO trail_coords (latitude, longitude, trail_id)
    VALUES (43.4945737, 5.8978018, 3);
INSERT INTO trail_coords (latitude, longitude, trail_id)
    VALUES (53.2252531, 33.7298819, 3);
INSERT INTO trail_coords (latitude, longitude, trail_id)
    VALUES (10.2453984, -74.041805, 2);
INSERT INTO trail_coords (latitude, longitude, trail_id)
    VALUES (15.5356992, 120.5285998, 1);
INSERT INTO trail_coords (latitude, longitude, trail_id)
    VALUES (-7.0655466, -35.3246769, 3);
INSERT INTO trail_coords (latitude, longitude, trail_id)
    VALUES (53.0662784, 19.2876039, 1);
INSERT INTO trail_coords (latitude, longitude, trail_id)
    VALUES (44.1675867, 2.0308233, 2);
INSERT INTO trail_coords (latitude, longitude, trail_id)
    VALUES (49.47512, -123.75903, 1);
INSERT INTO trail_coords (latitude, longitude, trail_id)
    VALUES (44.7051702, 18.3104025, 2);
INSERT INTO trail_coords (latitude, longitude, trail_id)
    VALUES (49.6109539, 6.0689379, 2);
INSERT INTO trail_coords (latitude, longitude, trail_id)
    VALUES (36.2656673, 139.250061, 3);
INSERT INTO trail_coords (latitude, longitude, trail_id)
    VALUES (46.3194856, -0.4546217, 3);
INSERT INTO trail_coords (latitude, longitude, trail_id)
    VALUES (34.8643622, 138.315371, 3);
INSERT INTO trail_coords (latitude, longitude, trail_id)
    VALUES (7.2675115, 28.678979, 2);
INSERT INTO trail_coords (latitude, longitude, trail_id)
    VALUES (24.0288453, 120.5624474, 1);
INSERT INTO trail_coords (latitude, longitude, trail_id)
    VALUES (9.9122584, -68.2743064, 2);
INSERT INTO trail_coords (latitude, longitude, trail_id)
    VALUES (34.509325, 119.751715, 1);
INSERT INTO trail_coords (latitude, longitude, trail_id)
    VALUES (52.55694, 17.0351289, 2);
INSERT INTO trail_coords (latitude, longitude, trail_id)
    VALUES (-7.0976672, 109.0215438, 1);
INSERT INTO trail_coords (latitude, longitude, trail_id)
    VALUES (17.0437082, 95.6431662, 3);
INSERT INTO trail_coords (latitude, longitude, trail_id)
    VALUES (49.0285391, 2.3236877, 1);