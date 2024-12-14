-- DROP table championship, player, team, team_participated_in;

SELECT nextval('team_id_seq');

insert into team (id, name, sponsor, world_ranking)
values (1, 'Team Liquid', 'Monster  Energy', 1);
insert into team (id, name, sponsor, world_ranking)
values (2, 'Gaimin Gladiators', null, 2);
insert into team (id, name, sponsor, world_ranking)
values (3, 'Tundra Esports', null, 3);
insert into team (id, name, sponsor, world_ranking)
values (4, 'Talon Esports', 'steelseries', 4);
insert into team (id, name, sponsor, world_ranking)
values (5, 'Team Secret', 'Predator', 5);
insert into team (id, name, sponsor, world_ranking)
values (6, 'Evil Geniuses', 'Xfinity', 6);
insert into team (id, name, sponsor, world_ranking)
values (7, 'OG', 'Red Bull', 7);
insert into team (id, name, sponsor, world_ranking)
values (8, 'Alliance', 'Monster', 8);
insert into team (id, name, sponsor, world_ranking)
values (9, 'Vici Gaming', null, 9);
insert into team (id, name, sponsor, world_ranking)
values (10, 'Fnatic', 'AMD', 10);
insert into team (id, name, sponsor, world_ranking)
values (11, 'Natus Vincere', null, 11);
insert into team (id, name, sponsor, world_ranking)
values (12, 'T1', 'Nike', 12);
insert into team (id, name, sponsor, world_ranking)
values (13, 'Invictus Gaming', null, 13);
insert into team (id, name, sponsor, world_ranking)
values (14, 'PSG.LGD', 'Nike', 14);
insert into team (id, name, sponsor, world_ranking)
values (15, 'Beastcoast', 'HyperX', 15);
insert into team (id, name, sponsor, world_ranking)
values (16, 'Team Aster', null, 16);
insert into team (id, name, sponsor, world_ranking)
values (17, 'Execration', 'Predator', 17);
insert into team (id, name, sponsor, world_ranking)
values (18, 'Team Spirit', null, 18);
insert into team (id, name, sponsor, world_ranking)
values (19, 'EHOME', 'Oppo', 19);
insert into team (id, name, sponsor, world_ranking)
values (20, 'Infamous', 'Betway', 20);
insert into team (id, name, sponsor, world_ranking)
values (21, 'BetBoom Team', 'BetBoom', 21);
insert into team (id, name, sponsor, world_ranking)
values (22, 'BOOM Esports', null, 22);
insert into team (id, name, sponsor, world_ranking)
values (23, 'TSM', null, 23);
insert into team (id, name, sponsor, world_ranking)
values (24, 'Entity', null, 24);
insert into team (id, name, sponsor, world_ranking)
values (25, 'Nigma Galaxy', 'BMW', 25);
insert into team (id, name, sponsor, world_ranking)
values (26, 'Virtus.Pro', 'Halls', 26);
insert into team (id, name, sponsor, world_ranking)
values (27, 'HellRaisers', 'Parimatch', 27);
insert into team (id, name, sponsor, world_ranking)
values (28, 'Soniqs', null, 28);
insert into team (id, name, sponsor, world_ranking)
values (29, 'Alliance.LATAM', null, 29);
insert into team (id, name, sponsor, world_ranking)
values (30, 'Royal Never Give Up', 'OMEN', 30);
insert into team (id, name, sponsor, world_ranking)
values (31, 'Thunder Awaken', null, 31);
insert into team (id, name, sponsor, world_ranking)
values (32, 'Shopify Rebellion', null, 32);

SELECT setval('team_id_seq', (SELECT MAX(id) FROM team));

insert into player(id, nickname, team_id)
values (1, 'Blitz', 1);
insert into player(id, nickname, team_id)
values (2, 'miCKe', 2);
insert into player(id, nickname, team_id)
values (3, 'Nisha', 3);
insert into player(id, nickname, team_id)
values (4, 'zai', 4);
insert into player(id, nickname, team_id)
values (5, 'Boxi', 1);
insert into player(id, nickname, team_id)
values (6, 'Insania', 2);
insert into player(id, nickname, team_id)
values (7, 'Cy-', 3);
insert into player(id, nickname, team_id)
values (8, 'V-Tune', 5);
insert into player(id, nickname, team_id)
values (9, 'Ace', 4);
insert into player(id, nickname, team_id)
values (10, 'tOfu', 1);
insert into player(id, nickname, team_id)
values (11, 'dyrachyo', 2);
insert into player(id, nickname, team_id)
values (12, 'Aui_2000', 1);
insert into player(id, nickname, team_id)
values (13, 'skiter', 1);
insert into player(id, nickname, team_id)
values (14, 'Nine', 3);
insert into player(id, nickname, team_id)
values (15, '33', 3);
insert into player(id, nickname, team_id)
values (16, 'Saksa', 4);
insert into player(id, nickname, team_id)
values (17, 'Sneyking', 5);
insert into player(id, nickname, team_id)
values (18, 'SunBhie', 5);
insert into player(id, nickname, team_id)
values (19, '23savage', 5);
insert into player(id, nickname, team_id)
values (20, 'Jabz', 5);

SELECT setval('player_id_seq', (SELECT MAX(id) FROM player));

insert into championship (id, name, prize_pool, starting_date, end_date, winner_id)
values (1, 'The International 2022', '189', '2022-10-15', '2022-10-30', 1);
insert into championship (id, name, prize_pool, starting_date, end_date, winner_id)
values (2, 'ESL One Malaysia 2022', '400', '2022-08-23', '2022-08-25', 2);
insert into championship (id, name, prize_pool, starting_date, end_date, winner_id)
values (3, 'PGL Arlington Major 2022', '500', '2022-08-04', '2022-08-14', 3);
insert into championship (id, name, prize_pool, starting_date, end_date, winner_id)
values (4, 'Riyadh Masters 2022', '400', '2022-07-20', '2022-07-24', 4);
insert into championship (id, name, prize_pool, starting_date, end_date, winner_id)
values (5, 'ESL One Stockholm Major 202', '500', '2022-05-12', '2022-05-22', 1);
insert into championship (id, name, prize_pool, starting_date, end_date, winner_id)
values (7, 'The International 2021', '400', '2021-10-07', '2021-10-17', 5);
insert into championship (id, name, prize_pool, starting_date, end_date, winner_id)
values (6, 'Kyiv Major', '300', '2017-04-24', '2017-05-30', 1);
SELECT setval('champ_id_seq', (SELECT MAX(id) FROM championship));