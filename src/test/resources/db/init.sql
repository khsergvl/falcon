CREATE USER FALCON IDENTIFIED BY test123;
GRANT CREATE SESSION TO FALCON container = all;
GRANT CONNECT TO FALCON container = all;
GRANT UNLIMITED TABLESPACE TO FALCON container = all;
GRANT CREATE TABLE TO FALCON container = all;
GRANT Aq_administrator_role TO FALCON container = all;
GRANT ALL PRIVILEGES TO FALCON container = all;

ALTER SESSION SET CONTAINER=XEPDB1;

/
BEGIN
    DBMS_AQADM.CREATE_QUEUE_TABLE(
            queue_table => 'FALCON.target_queue_table',
            queue_payload_type => 'SYS.AQ$_JMS_TEXT_MESSAGE');

    DBMS_AQADM.CREATE_QUEUE(
            queue_name => 'FALCON.TARGET',
            queue_table => 'FALCON.target_queue_table');

    DBMS_AQADM.START_QUEUE(
            queue_name => 'FALCON.TARGET');
END;
/
