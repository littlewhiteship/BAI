<<<<<<< HEAD
/****
database for BAIsystem
**/

CREATE DATABASE IF NOT EXISTS bai_db;

CREATE TABLE t_userinfo
(
    uiaccount VARCHAR(32),
    uipassword VARCHAR(16),
    uinickname VARCHAR(32),
    uiimage TEXT,
    uiidenfication INTEGER,
    uiid INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY
);

CREATE TABLE t_merchantextrainfo
(
    memerchandisetype VARCHAR(16),
    meshoplink TEXT,
    meshopcredit FLOAT,
    meshopplatform VARCHAR(16),
    meid INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
    meuseraccount VARCHAR(32),
    memaxprice FLOAT,
    meminprice FLOAT
);

CREATE TABLE t_customerfavorite
(
    cffavid INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
    cftime DATETIME,
    cfimage TEXT,
    cfmname VARCHAR(64),
    cfmurl TEXT,
    cfmprice FLOAT,
    cfmplatform VARCHAR(16),
    cfuseraccount VARCHAR(32)
);

CREATE TABLE t_postfavorite
(
    pffavid INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
    pfid INTEGER,
    pftime DATETIME,
    pfaccount VARCHAR(32)
);

CREATE TABLE t_mminfo
(   
    mmiid INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
    mmiurl TEXT,
    mmiprice FLOAT,
    mminame VARCHAR(64),
    mmisalesvolume INTEGER,
    mmishopid INTEGER,
    mmiimg TEXT
);


CREATE TABLE t_msstoresales
(
    msssid INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
    msssurl TEXT,
    msssdescriptionpoints FLOAT,
    mssslogisticsspeedpoints FLOAT,
    msssservepoints FLOAT,
    msssshopname VARCHAR(16),
    msssshopid INTEGER,
    mssstype VARCHAR(16),
    mssssalesscore FLOAT,
    msssimage TEXT
);

CREATE TABLE t_msstorecredit
(
    msscid INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
    msscurl TEXT,
    msscdescriptionpoints FLOAT,
    mssclogisticsspeedpoints FLOAT,
    msscservepoints FLOAT,
    msscshopname VARCHAR(16),
    msscshopid INTEGER,
    mssctype VARCHAR(16),
    mssccreditscore FLOAT,
    msscimage TEXT
);

CREATE TABLE t_mmdetailedinfo
(
    mmdiid INTEGER  NOT NULL AUTO_INCREMENT PRIMARY KEY,
    mmdiname VARCHAR(64),
    mmdidate DATE,
    mmdiprice FLOAT,
    mmdisalesvolume INTEGER,
    mmdimmid INTEGER
);

CREATE TABLE t_allmcredit
(
    amcname VARCHAR(64),
    price FLOAT,
    link TEXT,
    monthlysales INTEGER,
    platform VARCHAR(16),
    amcimage TEXT,
    creditscore FLOAT,
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY
);

CREATE TABLE t_allmsales
(
    amsname VARCHAR(64),
    price FLOAT,
    link TEXT,
    monthlysales INTEGER,
    platform VARCHAR(16),
    amsimage TEXT,
    salesscore FLOAT,
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY
);

CREATE TABLE t_allmprice
(
    ampname VARCHAR(64),
    ampprice FLOAT,
    link TEXT,
    monthlysales INTEGER,
    platform VARCHAR(16),
    ampimage TEXT,
    pricescore FLOAT,
    ampid INT NOT NULL AUTO_INCREMENT PRIMARY KEY
);

CREATE TABLE t_reply
(
    postkey INTEGER,
    commentkey INTEGER,
    replieraccount VARCHAR(32),
    repliernickname VARCHAR(32),
    replierimage TEXT,
    replycontent TEXT,
    replytime DATETIME,
    replykey INT NOT NULL AUTO_INCREMENT PRIMARY KEY
);

CREATE TABLE t_comment
(
    postkey INTEGER,
    commentatoraccount VARCHAR(32),
    commentatornickname VARCHAR(32),
    commentatorimage TEXT,
    commentcontent TEXT,
    commenttime DATETIME,
    commentkey INT NOT NULL AUTO_INCREMENT PRIMARY KEY 
);

CREATE TABLE t_post
(
    postaccount VARCHAR(32),
    postnickname VARCHAR(32),
    postimage TEXT,
    likenum INTEGER,
    commentnum INTEGER,
    complaintnum INTEGER,
    merchandisename VARCHAR(64),
    postcontent TEXT,
    merchandiselink TEXT,
    posttime DATETIME,
    merchandisepicpath TEXT,
	postkey INT NOT NULL AUTO_INCREMENT PRIMARY KEY
);

CREATE TABLE t_postnotice
(
	pntype VARCHAR(8),
	usernickname VARCHAR(32),
	receiveraccount VARCHAR(32),
	noticetime DATETIME,
	content TEXT,
	noticekey INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY
);

CREATE TABLE t_livenotice
(
	noticekey INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
	livetime DATETIME,
	livername VARCHAR(16),
	merchandisetype VARCHAR(8),
	url TEXT,
	noticetime DATETIME
);

CREATE TABLE t_blogger
(
    id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
    url TEXT,
    name VARCHAR(16),
    fansnum  VARCHAR(16),
    blognum  VARCHAR(16),
    type VARCHAR(16),
    img TEXT
=======
/****
database for BAIsystem
**/

CREATE DATABASE IF NOT EXISTS bai_db;

CREATE TABLE t_userinfo
(
    uiaccount VARCHAR(32),
    uipassword VARCHAR(16),
    uinickname VARCHAR(32),
    uiimage TEXT,
    uiidenfication INTEGER,
    uiid INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY
);

CREATE TABLE t_merchantextrainfo
(
    memerchandisetype VARCHAR(16),
    meshoplink TEXT,
    meshopcredit FLOAT,
    meshopplatform VARCHAR(16),
    meid INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
    meuseraccount VARCHAR(32),
    memaxprice FLOAT,
    meminprice FLOAT
);

CREATE TABLE t_customerfavorite
(
    cffavid INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
    cftime DATETIME,
    cfimage TEXT,
    cfmname VARCHAR(64),
    cfmurl TEXT,
    cfmprice FLOAT,
    cfmplatform VARCHAR(16),
    cfuseraccount VARCHAR(32)
);

CREATE TABLE t_postfavorite
(
    pffavid INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
    pfid INTEGER,
    pftime DATETIME,
    pfaccount VARCHAR(32)
);

CREATE TABLE t_mminfo
(   
    mmiid INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
    mmiurl TEXT,
    mmiprice FLOAT,
    mminame VARCHAR(64),
    mmisalesvolume INTEGER,
    mmishopid INTEGER,
    mmiimg TEXT
);


CREATE TABLE t_msstoresales
(
    msssid INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
    msssurl TEXT,
    msssdescriptionpoints FLOAT,
    mssslogisticsspeedpoints FLOAT,
    msssservepoints FLOAT,
    msssshopname VARCHAR(16),
    msssshopid INTEGER,
    mssstype VARCHAR(16),
    mssssalesscore FLOAT,
    msssimage TEXT
);

CREATE TABLE t_msstorecredit
(
    msscid INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
    msscurl TEXT,
    msscdescriptionpoints FLOAT,
    mssclogisticsspeedpoints FLOAT,
    msscservepoints FLOAT,
    msscshopname VARCHAR(16),
    msscshopid INTEGER,
    mssctype VARCHAR(16),
    mssccreditscore FLOAT,
    msscimage TEXT
);

CREATE TABLE t_mmdetailedinfo
(
    mmdiid INTEGER  NOT NULL AUTO_INCREMENT PRIMARY KEY,
    mmdiname VARCHAR(64),
    mmdidate DATE,
    mmdiprice FLOAT,
    mmdisalesvolume INTEGER,
    mmdimmid INTEGER
);

CREATE TABLE t_allmcredit
(
    amcname VARCHAR(64),
    price FLOAT,
    link TEXT,
    monthlysales INTEGER,
    platform VARCHAR(16),
    amcimage TEXT,
    creditscore FLOAT,
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY
);

CREATE TABLE t_allmsales
(
    amsname VARCHAR(64),
    price FLOAT,
    link TEXT,
    monthlysales INTEGER,
    platform VARCHAR(16),
    amsimage TEXT,
    salesscore FLOAT,
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY
);

CREATE TABLE t_allmprice
(
    ampname VARCHAR(64),
    ampprice FLOAT,
    link TEXT,
    monthlysales INTEGER,
    platform VARCHAR(16),
    ampimage TEXT,
    pricescore FLOAT,
    ampid INT NOT NULL AUTO_INCREMENT PRIMARY KEY
);

CREATE TABLE t_reply
(
    postkey INTEGER,
    commentkey INTEGER,
    replieraccount VARCHAR(32),
    repliernickname VARCHAR(32),
    replierimage TEXT,
    replycontent TEXT,
    replytime DATETIME,
    replykey INT NOT NULL AUTO_INCREMENT PRIMARY KEY
);

CREATE TABLE t_comment
(
    postkey INTEGER,
    commentatoraccount VARCHAR(32),
    commentatornickname VARCHAR(32),
    commentatorimage TEXT,
    commentcontent TEXT,
    commenttime DATETIME,
    commentkey INT NOT NULL AUTO_INCREMENT PRIMARY KEY 
);

CREATE TABLE t_post
(
    postaccount VARCHAR(32),
    postnickname VARCHAR(32),
    postimage TEXT,
    likenum INTEGER,
    commentnum INTEGER,
    complaintnum INTEGER,
    merchandisename VARCHAR(64),
    postcontent TEXT,
    merchandiselink TEXT,
    posttime DATETIME,
    merchandisepicpath TEXT,
	postkey INT NOT NULL AUTO_INCREMENT PRIMARY KEY
);

CREATE TABLE t_postnotice
(
	pntype VARCHAR(8),
	usernickname VARCHAR(32),
	receiveraccount VARCHAR(32),
	noticetime DATETIME,
	content TEXT,
	noticekey INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY
);

CREATE TABLE t_livenotice
(
	noticekey INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
	livetime DATETIME,
	livername VARCHAR(16),
	merchandisetype VARCHAR(8),
	url TEXT,
	noticetime DATETIME
);

CREATE TABLE t_blogger
(
    id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
    url TEXT,
    name VARCHAR(16),
    fansnum  VARCHAR(16),
    blognum  VARCHAR(16),
    type VARCHAR(16),
    img TEXT
>>>>>>> 20dc426af24886845667c109154d3cf1f49f1d28
);