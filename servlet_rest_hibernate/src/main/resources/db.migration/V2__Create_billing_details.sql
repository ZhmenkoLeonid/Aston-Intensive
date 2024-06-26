DROP TABLE IF EXISTS billing_details;
DROP TABLE IF EXISTS credit_card;
DROP TABLE IF EXISTS bank_account;

DROP SEQUENCE IF EXISTS billing_id_seq;

CREATE SEQUENCE billing_id_seq START WITH 1;

CREATE  TABLE billing_details (
                                           id                   bigint DEFAULT nextval('billing_id_seq') NOT NULL ,
                                           "owner"              varchar(100)  NOT NULL ,
                                           user_id              integer  NOT NULL ,
                                           CONSTRAINT pk_bank_account_id_0 PRIMARY KEY ( id )
);

CREATE  TABLE credit_card (
                                       id                   bigint DEFAULT nextval('billing_id_seq') NOT NULL ,
                                       card_number          varchar(20)  NOT NULL ,
                                       exp_month            varchar  NOT NULL ,
                                       exp_year             varchar  NOT NULL ,
                                       CONSTRAINT pk_bank_account_id_1 PRIMARY KEY ( id )
);

CREATE  TABLE bank_account (
                                        id                   bigint DEFAULT nextval('billing_id_seq') NOT NULL ,
                                        bank_name            varchar(100)  NOT NULL ,
                                        account              bigint  NOT NULL ,
                                        CONSTRAINT pk_bank_account_id PRIMARY KEY ( id )
);


ALTER TABLE billing_details ADD CONSTRAINT fk_billing_details_users FOREIGN KEY ( user_id ) REFERENCES users( id );
ALTER TABLE bank_account ADD CONSTRAINT fk_bank_account FOREIGN KEY ( id ) REFERENCES billing_details( id );
ALTER TABLE credit_card ADD CONSTRAINT fk_bank_account_0 FOREIGN KEY ( id ) REFERENCES billing_details( id );