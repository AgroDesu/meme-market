
drop table Patrons CASCADE CONSTRAINTS;
drop table Userz CASCADE CONSTRAINTS;
drop table Rarity CASCADE CONSTRAINTS;
drop table Tags CASCADE CONSTRAINTS;
drop table Cards CASCADE CONSTRAINTS;
drop table CardTags CASCADE CONSTRAINTS;
drop table OwnedCards CASCADE CONSTRAINTS;
drop table TradeStatus CASCADE CONSTRAINTS;
drop table Trades CASCADE CONSTRAINTS;
drop table TradeOwnedCards CASCADE CONSTRAINTS;
drop table PackTiers CASCADE CONSTRAINTS;
drop table PackTiersRarity CASCADE CONSTRAINTS;
drop table Microtransactions CASCADE CONSTRAINTS;
drop table PurchaseLevels CASCADE CONSTRAINTS;
drop table Purchases CASCADE CONSTRAINTS;
drop table Comments CASCADE CONSTRAINTS;

drop sequence Patrons_seq;
drop sequence Userz_seq;
drop sequence Rarity_seq;
drop sequence Tags_seq;
drop sequence Cards_seq;
drop sequence OwnedCards_seq;
drop sequence TradeStatus_seq;
drop sequence Trades_seq;
drop sequence PackTiers_seq;
drop sequence Microtransactions_seq;
drop sequence PurchaseLevels_seq;
drop sequence Purchases_seq;
drop sequence Comments_seq;

create table Patrons(
    id number(10) primary key,
    stonks number(8)
);

create table Userz(
    id number(10) primary key,
    username varchar2(20) unique not null,
    pass varchar2(20) not null,
    patronID number(10),
    constraint fk_Userz_Patrons foreign key (patronID) references Patrons(id)
);

create table Rarity(
    id number(1) primary key,
    rarityName varchar2(20),
    rarityWeight number(2)
);

create table Tags(
    id number(2) primary key,
    tagName varchar2(20) not null
);

create table Cards(
    id number(10) primary key,
    cardImage varchar2(100) not null,
    cardText varchar2(50),
    memeText varchar2(400),
    rarityId number(1),
    constraint fk_Cards_Rarity foreign key (rarityID) references Rarity(id)
);

create table CardTags(
    cardId number(10) not null,
    tagId number(2) not null,
    constraint fk_CardTags_Cards foreign key (cardId) references Cards(id),
    constraint fk_CardTags_Tags foreign key (tagId) references Tags(id)
);

create table OwnedCards(
    id number(10) primary key,
    patronId number(10) not null,
    cardId number(10) not null,
    showcased number(1),
    constraint fk_PatronCards_Patrons foreign key (patronId) references Patrons(id),
    constraint fk_PatronCards_Cards foreign key (cardId) references Cards(id)
);

create table TradeStatus(
    id number(1) primary key,
    statusName varchar(20) not null
);

create table Trades(
    id number(10) primary key,
    tradeStatusId number(10),
    patronOneId number(10),
    patronTwoId number(10),
    constraint fk_Trades_TradeStatus foreign key (tradeStatusId) references TradeStatus(id),
    constraint fk_Trades_Patrons_One foreign key (patronOneId) references Patrons(id),
    constraint fk_Trades_Patrons_Two foreign key (patronTwoId) references Patrons(id)
);

create table TradeOwnedCards(
    tradeId number(10) not null,
    ownedCardsId number(10) not null,
    constraint fk_TradeCardPatron_Trades foreign key (tradeId) references Trades(id),
    constraint fk_TradeCardPatron_OwnedCards foreign key (ownedCardsId) references OwnedCards(id)
);

create table PackTiers(
    id number(1) primary key,
    tierName varchar2(20) not null,
    tierPrice number(8) not null,
    numOfCards number(1) default 3 not null
);

create table PackTiersRarity(
    packTiersId number(1) not null,
    rarityId number(1) not null,
    constraint pk_PackTiersRarity primary key (packTiersId, rarityId),
    constraint fk_PackTiersRarity_PackTiers foreign key (packTiersId) references PackTiers(id),
    constraint fk_PackTiersRarity_Rarity foreign key (rarityId) references Rarity(id)
);

create table Microtransactions( -- Using stonks to buy packs.
    id number(10) primary key,
    patronId number(10) not null,
    packTierId number(1) not null,
    transDate timestamp default systimestamp,
    constraint fk_Microtransactions_Packs foreign key (packTierId) references PackTiers(id),
    constraint fk_Microtransactions_Patrons foreign key (patronId) references Patrons(id)
);

create table PurchaseLevels(
    id number(1) primary key,
    levelName varchar2(20) not null,
    stonkAmount number(5) not null,
    levelCost number(5,2) not null
);

create table Purchases( -- Using real money to buy stonks.
    id number(10) primary key,
    patronId number(10) not null,
    purchaseLevelId number(1) not null,
    purchaseDate timestamp default systimestamp,
    constraint fk_Purchase_PurchaseLevels foreign key (purchaseLevelId) references PurchaseLevels(id),
    constraint fk_Purchase_Patrons foreign key (patronId) references Patrons(id)
);

create table Comments(
    id number(10) primary key,
    cardId number(10) not null,
    username varchar2(20) not null,
    commentText varchar2(500) not null,
    timeOfComment timestamp default systimestamp not null,
    constraint fk_Comments_Cards foreign key (cardId) references Cards(id),
    constraint fk_Comments_Patrons foreign key (username) references Userz(username)
);

create sequence Patrons_seq start with 3 nocache;
create sequence Userz_seq start with 6 nocache;
create sequence Rarity_seq nocache;
create sequence Tags_seq nocache;
create sequence Cards_seq start with 34 nocache;
create sequence OwnedCards_seq start with 4 nocache;
create sequence TradeStatus_seq nocache;
create sequence Trades_seq nocache;
create sequence PackTiers_seq nocache;
create sequence Microtransactions_seq nocache;
create sequence PurchaseLevels_seq nocache;
create sequence Purchases_seq nocache;
create sequence Comments_seq nocache;

insert into Rarity (id, rarityName, rarityWeight) values (1, 'Well-Done', 50);
insert into Rarity (id, rarityName, rarityWeight) values (2, 'Medium-Well', 24);
insert into Rarity (id, rarityName, rarityWeight) values (3, 'Medium', 10);
insert into Rarity (id, rarityName, rarityWeight) values (4, 'Medium-Rare', 5);
insert into Rarity (id, rarityName, rarityWeight) values (5, 'Rare', 1);

INSERT INTO TradeStatus (id, statusname) VALUES (1, 'Pending');
INSERT INTO TradeStatus (id, statusname) VALUES (2, 'Denied');
INSERT INTO TradeStatus (id, statusname) VALUES (3, 'Complete');

insert into Userz (id, username, pass) VALUES (1, 'alexkam123', 'Al23ex23!');
insert into Userz (id, username, pass) VALUES (2, 'jdelgado', 'pass');
insert into Userz (id, username, pass) VALUES (3, 'Chris', 'pass');

insert into PackTiers (id, tierName, tierPrice) values (1, 'Tier 1', '100');
insert into PackTiers (id, tierName, tierPrice) values (2, 'Tier 2', '50');
insert into PackTiers (id, tierName, tierPrice) values (3, 'Tier 3', '20');

insert into Tags (id, tagName) values (1, 'spicy');
insert into Tags (id, tagName) values (2, 'cats');

insert into PackTiersRarity(packTiersId, RarityId) values (1, 3);
insert into PackTiersRarity(packTiersId, RarityId) values (1, 4);
insert into PackTiersRarity(packTiersId, RarityId) values (1, 5);

insert into PackTiersRarity(packTiersId, RarityId) values (2, 2);
insert into PackTiersRarity(packTiersId, RarityId) values (2, 3);
insert into PackTiersRarity(packTiersId, RarityId) values (2, 4);
insert into PackTiersRarity(packTiersId, RarityId) values (2, 5);

insert into PackTiersRarity(packTiersId, RarityId) values (3, 1);
insert into PackTiersRarity(packTiersId, RarityId) values (3, 2);
insert into PackTiersRarity(packTiersId, RarityId) values (3, 3);
insert into PackTiersRarity(packTiersId, RarityId) values (3, 4);
insert into PackTiersRarity(packTiersId, RarityId) values (3, 5);

insert into Cards (id, cardImage, cardText, memeText, rarityId) 
    values (1, 'https://i.imgur.com/WnUkxTb.jpg', 'cardText', 'Do you even lift?<br><br>Me:<br>[Image: Hand lifting open Oreo package]', 1);
    
insert into Cards (id, cardImage, cardText, memeText, rarityId) 
    values (2, 'https://i.imgur.com/Ka9P227.png', 'cardText', 'Nobody:<br>Gaming laptops after 15 minutes on battery:<br>[Image: Bernie Sanders]<br>I am once again asking<br>for your electrical support', 2);
    
insert into Cards (id, cardImage, cardText, memeText, rarityId) 
    values (3, 'https://i.imgur.com/7GdHhvJ.jpg', 'cardText', 'me: my back hurts so bad what the hell<br><br>also me:<br>[Drawing: Man aggressively slouching]', 3);

insert into Cards (id, cardImage, cardText, memeText, rarityId)
    values (4, 'https://i.imgur.com/9oviAaW.jpg', 'cardText', 'me: *bites into burger*<br><br>everything on the opposite<br>end of the burger:<br>[Image: Man jumping out the back of a plane]', 4);

insert into Cards (id, cardImage, cardText, memeText, rarityId)
    values (5, 'https://i.imgur.com/ImUOhmH.jpg', 'cardText', '[Image: Task Manager (Not Responding)]<br>YOU WERE SUPPOSED<br>TO DESTROY THEM<br>[Image: Obi Wan Kenobi yelling on Mustafar]<br>NOT JOIN THEM', 5);

insert into Cards (id, cardImage, cardText, memeText, rarityId)
    values (6, 'https://i.imgur.com/zQKqevE.jpg', 'cardText', '[Image: Dog sitting below white board with message<br>(If you ever find<br>yourself cringing<br>at something you<br>did in the past, it<br>it means you have<br>grown as a<br>person)]', 1);

insert into Cards (id, cardImage, cardText, memeText, rarityId)
    values (7, 'https://i.imgur.com/61qz2CY.jpg', 'cardText', '[Image: Giant dust clowd with a dog face (gangnam style) enveloping a city (society in 2012)]', 1);

insert into Cards (id, cardImage, cardText, memeText, rarityId)
    values (8, 'https://i.imgur.com/mMsHS2E.jpg', 'cardText', 'Me thinking about all<br>the half price<br>Reeses on Feb 15th<br>[Image: Man peering around a tree licking his lips and rubbing his hands together]', 1);

insert into Cards (id, cardImage, cardText, memeText, rarityId)
    values (9, 'https://i.imgur.com/CJggtL7.jpg', 'cardText', 'Me hists my elbow on desk<br><br>My entire nervous system:<br>', 1);

insert into Cards (id, cardImage, cardText, memeText, rarityId)
    values (10, 'https://i.imgur.com/f5rGuZ1.jpg', 'cardText', '[Image: Indiana Jones (Me) staring at a golden idol (My Mom''s<br>charger]<br>[Image: Indiana Jones (Me) replacing a golden diol (My Mom''s<br>charger) with a bag of sand (damaged<br>charger)]', 1);

insert into Cards (id, cardImage, cardText, memeText, rarityId)
    values (11, 'https://i.imgur.com/8z3KRzV.jpg', 'cardText', '[Drawing: Two puppies carrying a banner with a message<br>(Google is better than<br>Reddit to search for<br>something on Reddit)]', 1);

insert into Cards (id, cardImage, cardText, memeText, rarityId)
    values (12, 'https://i.imgur.com/LGQ4akJ.jpg', 'cardText', 'Me going into WWIII knowing<br>how to use the Pythagorean<br>Theorem<br>[Drawing: Extremely muscular shirtless man in a fighting stance]', 1);

insert into Cards (id, cardImage, cardText, memeText, rarityId)
    values (13, 'https://i.imgur.com/BLQgDFl.jpg', 'cardText', '[Image: Man holding up spraycan with speach bubble (Is this good<br>for wasps?) facing a cashier with speach bubble (No, it kills<br>them.)]', 1);

insert into Cards (id, cardImage, cardText, memeText, rarityId)
    values (14, 'https://i.imgur.com/OwMcxw4.jpg', 'cardText', 'Teacher: You will be grading each<br>ohter''s tests<br>Students:<br>[Image: Russion general amoung rows of men in generic warm clothing]<br>I serve the Soviet Union', 1);

insert into Cards (id, cardImage, cardText, memeText, rarityId)
    values (16, 'https://i.imgur.com/LPWuB3z.jpg', 'cardText', 'girl: wears ripped jeans<br>girl''s knee:<br>[Image: Jim from The Office, looking through window blinds]', 2);

insert into Cards (id, cardImage, cardText, memeText, rarityId)
    values (17, 'https://i.imgur.com/WcIkbRJ.jpg', 'cardText', '[Image: Text message conversation<br>(>> So can we talk?<br><br>Talk about what? <<<br><br>>> Us<br>Why you wanna talk <<<br>about the United States)<br>with United States distorted]', 2);

insert into Cards (id, cardImage, cardText, memeText, rarityId)
    values (18, 'https://i.imgur.com/1KmXSxb.jpg', 'cardText', 'Me and the boys at our Halloween party<br>[Image: Four guys in bad costumes posing for a picture]', 2);

insert into Cards (id, cardImage, cardText, memeText, rarityId)
    values (19, 'https://i.imgur.com/4ZBQ29h.jpg', 'cardText', '[Image: Palpatine disfigured and distressed (Phone charger at any angle)]<br>I''m too weak.<br>[Image: Palpatine agressively shooting lightning (Phone charger at one specific angle)]<br>UnlimitedPower!', 2);

insert into Cards (id, cardImage, cardText, memeText, rarityId)
    values (20, 'https://i.imgur.com/trGI8WL.jpg', 'cardText', 'Media complaining<br>about violent video<br>games<br>[Image: An enraged women being held back while pointing and shouting at a cat in other image]<br>Me, too afraid to pick<br>rude dialogue options<br>thinking it would make<br>the characters sad<br>[Image: Keyboard superimposed over a hissing cat]', 3);

insert into Cards (id, cardImage, cardText, memeText, rarityId)
    values (21, 'https://i.imgur.com/QwdHWda.jpg', 'cardText', '[Image: Flat screen LCD TV/monitor]<br>*Puts down gently in fear of breaking the TV*<br>[Image: Old CRT TV]<br>*Puts down gently in fear of breaking the table and your fingers*', 3);

insert into Cards (id, cardImage, cardText, memeText, rarityId)
    values (22, 'https://i.imgur.com/eYFMWKZ.jpg', 'cardText', 'The yougest picture of you is the oldest picture of you<br>[Image: Perplexed man takes off his glasses]', 3);

insert into Cards (id, cardImage, cardText, memeText, rarityId)
    values (23, 'https://i.imgur.com/vNEy1rQ.jpg', 'cardText', '2050 is as far away as 1990<br>[Image: Michael from The Office]<br>[softly] Don''t.', 3);

insert into Cards (id, cardImage, cardText, memeText, rarityId)
    values (24, 'https://i.imgur.com/h55lscO.jpg', 'cardText', 'Me and my grandpa when there''s<br>nobody at our fishing spot<br>[Image: Man peering around a tree licking his lips and rubbing his hands together<br>with the image mirrored so that an aged version of the same man is peering around the other side of the tree]', 3);

insert into Cards (id, cardImage, cardText, memeText, rarityId)
    values (25, 'https://i.imgur.com/YzJAQ4W.jpg', 'cardText', 'Me: *opens 3 chrome tabs*<br><br>My $2000 laptop:<br>[Image: The turbine engine of a plane]', 3);

insert into Cards (id, cardImage, cardText, memeText, rarityId)
    values (26, 'https://i.imgur.com/lz3sPzV.jpg', 'cardText', 'This collab makes so much sense<br>[Image: Package of Scott toilet paper with Taco Bell Logo on it called Fiesta Strangth]', 3);

insert into Cards (id, cardImage, cardText, memeText, rarityId)
    values (27, 'https://i.imgur.com/QeYNaFT.png', 'cardText', '[Image: Colossusv creature wielding a sword<br>(People who use etc. becasue there<br>are too many examples)<br>facing up with an ordanary small man also wielding a sword (People who use etc. becasue they<br>can''t think of anymore examples)]', 4);

insert into Cards (id, cardImage, cardText, memeText, rarityId)
    values (28, 'https://i.imgur.com/axN4jli.jpg', 'cardText', '[Image: A Roman soldure (my brain)<br>blowing a horn (the fact that ther are more nipples than humans)<br>at a young boy in bed screeming (me at 3am)]', 4);

insert into Cards (id, cardImage, cardText, memeText, rarityId)
    values (29, 'https://i.imgur.com/6uiy42x.jpg', 'cardText', 'I''m gonna get close to this group of<br>humans. What''s the worst that can<br>happen?<br>[Image: A wolf out in the wild]<br>Thousands of years later:<br>[Image: A sad puppy pug with a birthday cap on<br>amungst an assortment of pink birthday party favors]', 4);

insert into Cards (id, cardImage, cardText, memeText, rarityId)
    values (30, 'https://i.imgur.com/mtSil7y.jpg', 'cardText', '[Image: The torso of a man buttening up a nice suit<br>(DRESS FOR THE JOB YOU WANT, NBOT THE JOB YOU HAVE)<br>[Image: Man sitting in an office cubical in front of a computer<br>dressed like an F1 racecard driver]', 4);

insert into Cards (id, cardImage, cardText, memeText, rarityId)
    values (32, 'https://i.imgur.com/s5CS8qI.jpg', 'cardText', '7 year old me in a corner of the pool<br>[Image: Spiderman clinging to a glass panel on the side of a skyscraper with one hand and both feet against the panel', 5);

insert into Cards (id, cardImage, cardText, memeText, rarityId)
    values (33, 'https://i.imgur.com/PuYaKg8.jpg', 'cardText', '"Video Games Cause violence"<br>[Image: An enraged women being held back while pointing and shouting at a cat in other image]<br>Me taking care of my fields<br>[Image: Hissing cat driving a tractor through an animated field]', 5);

insert into Patrons (id, stonks) values (1, 100);
insert into Patrons (id, stonks) values (2, 125);

insert into Userz (id, username, pass, patronId) values (4, 'RDetrich', 'Rpass', 1);
insert into Userz (id, username, pass, patronId) values (5, 'LLynch', 'Lpass', 2);

insert into OwnedCards (id, patronId, cardId, showcased) values (1, 1, 1, null);
insert into OwnedCards (id, patronId, cardId, showcased) values (2, 1, 2, null);
insert into OwnedCards (id, patronId, cardId, showcased) values (3, 2, 3, null);

--create table OwnedCards
--    id number(10) primary key,
--    patronId number(10) not null,
--    cardId number(10) not null,
--    showcased number(1),

create view tradeCardView as SELECT tradeid, ownedcardsid, tradestatusid FROM TradeOwnedCards toc, Trades t, OwnedCards oc  WHERE toc.tradeid = t.id AND toc.ownedcardsid=oc.id;

commit;

select from trades join tradeownedcards where 







/*
create table Patrons
create table Rarity
create table Tags
create table Cards
create table CardTags
create table PatronCards
create table TradeStatus
create table Trades
create table TradePatronCards
create table PackTiers
create table PackTiersRarity
create table Microtransactions
create table PurchaseLevel
create table Purchase
create table Comments

*/














