-- Gerado por Oracle SQL Developer Data Modeler 24.3.1.351.0831
--   em:        2026-11-12 05:17:11 BRT
--   site:      Oracle Database 11g
--   tipo:      Oracle Database 11g



-- predefined type, no DDL - MDSYS.SDO_GEOMETRY

-- predefined type, no DDL - XMLTYPE

CREATE TABLE ATENDIMENTOS 
    ( 
     id_atendimento                      INTEGER  NOT NULL , 
     status                              VARCHAR2 (14)  NOT NULL , 
     KITS_PULSO_id_kit                   INTEGER  NOT NULL , 
--  ERROR: Column name length exceeds maximum allowed length(30) 
     ESTABELECIMENTOS_id_estabelecimento INTEGER  NOT NULL 
    ) 
;

ALTER TABLE ATENDIMENTOS 
    ADD CONSTRAINT ATENDIMENTOS_PK PRIMARY KEY ( id_atendimento ) ;

CREATE TABLE CHAMADAS 
    ( 
     id_chamada                  INTEGER  NOT NULL , 
     data_hora_disparo           DATE  NOT NULL , 
     data_hora_confirmacao       DATE  NOT NULL , 
     status                      VARCHAR2 (20)  NOT NULL , 
     ATENDIMENTOS_id_atendimento INTEGER  NOT NULL 
    ) 
;

ALTER TABLE CHAMADAS 
    ADD CONSTRAINT CHAMADAS_PK PRIMARY KEY ( id_chamada ) ;

CREATE TABLE ESTABELECIMENTOS 
    ( 
     id_estabelecimento INTEGER  NOT NULL , 
     nome               VARCHAR2 (50)  NOT NULL , 
     tipo               VARCHAR2 (30)  NOT NULL , 
     cidade             VARCHAR2 (40)  NOT NULL , 
     uf                 VARCHAR2 (2)  NOT NULL 
    ) 
;

ALTER TABLE ESTABELECIMENTOS 
    ADD CONSTRAINT ESTABELECIMENTOS_PK PRIMARY KEY ( id_estabelecimento ) ;

CREATE TABLE KITS_PULSO 
    ( 
     id_kit                       INTEGER  NOT NULL , 
     dt_aquisicao                 DATE  NOT NULL , 
     status                       VARCHAR2 (20)  NOT NULL , 
     PULSEIRAS_id_pulseira        INTEGER  NOT NULL , 
     TRANSMISSORES_id_transmissor INTEGER  NOT NULL , 
     PESSOAS_USUARIAS_id_usuario  INTEGER  NOT NULL 
    ) 
;
CREATE UNIQUE INDEX KITS_PULSO__IDX ON KITS_PULSO 
    ( 
     PULSEIRAS_id_pulseira ASC 
    ) 
;
CREATE UNIQUE INDEX KITS_PULSO__IDXv1 ON KITS_PULSO 
    ( 
     TRANSMISSORES_id_transmissor ASC 
    ) 
;

ALTER TABLE KITS_PULSO 
    ADD CONSTRAINT KITS_PULSO_PK PRIMARY KEY ( id_kit ) ;

CREATE TABLE PESSOAS_USUARIAS 
    ( 
     id_usuario   INTEGER  NOT NULL , 
     nome         VARCHAR2 (50)  NOT NULL , 
     usa_vibracao CHAR (1) DEFAULT 'Verdadeiro'  NOT NULL , 
     usa_luz      CHAR (1)  NOT NULL , 
     usa_audio    CHAR (1)  NOT NULL , 
     dt_cadastro  DATE  NOT NULL 
    ) 
;

ALTER TABLE PESSOAS_USUARIAS 
    ADD 
    CHECK (usa_vibracao IN ('Falso', 'Verdadeiro')) 
;

ALTER TABLE PESSOAS_USUARIAS 
    ADD CONSTRAINT PESSOAS_USUARIAS_PK PRIMARY KEY ( id_usuario ) ;

CREATE TABLE PULSEIRAS 
    ( 
     id_pulseira       INTEGER  NOT NULL , 
     mac_pulseira      VARCHAR2 (17)  NOT NULL , 
     nivel_bateria     NUMBER (3)  NOT NULL , 
     status            VARCHAR2 (20)  NOT NULL , 
     KITS_PULSO_id_kit INTEGER  NOT NULL 
    ) 
;
CREATE UNIQUE INDEX PULSEIRAS__IDX ON PULSEIRAS 
    ( 
     KITS_PULSO_id_kit ASC 
    ) 
;

ALTER TABLE PULSEIRAS 
    ADD CONSTRAINT PULSEIRAS_PK PRIMARY KEY ( id_pulseira ) ;

ALTER TABLE PULSEIRAS 
    ADD CONSTRAINT PULSEIRAS_mac_pulseira_UN UNIQUE ( mac_pulseira ) ;

CREATE TABLE TRANSMISSORES 
    ( 
     id_transmissor    INTEGER  NOT NULL , 
     mac_transmissor   VARCHAR2 (17)  NOT NULL , 
     nivel_bateria     NUMBER (3)  NOT NULL , 
     status            VARCHAR2 (20)  NOT NULL , 
     KITS_PULSO_id_kit INTEGER  NOT NULL 
    ) 
;
CREATE UNIQUE INDEX TRANSMISSORES__IDX ON TRANSMISSORES 
    ( 
     KITS_PULSO_id_kit ASC 
    ) 
;

ALTER TABLE TRANSMISSORES 
    ADD CONSTRAINT TRANSMISSORES_PK PRIMARY KEY ( id_transmissor ) ;


--  ERROR: UK name length exceeds maximum allowed length(30) 
ALTER TABLE TRANSMISSORES 
    ADD CONSTRAINT TRANSMISSORES_mac_transmissor_UN UNIQUE ( mac_transmissor ) ;

--  ERROR: FK name length exceeds maximum allowed length(30) 
ALTER TABLE ATENDIMENTOS 
    ADD CONSTRAINT ATENDIMENTOS_ESTABELECIMENTOS_FK FOREIGN KEY 
    ( 
     ESTABELECIMENTOS_id_estabelecimento
    ) 
    REFERENCES ESTABELECIMENTOS 
    ( 
     id_estabelecimento
    ) 
;

ALTER TABLE ATENDIMENTOS 
    ADD CONSTRAINT ATENDIMENTOS_KITS_PULSO_FK FOREIGN KEY 
    ( 
     KITS_PULSO_id_kit
    ) 
    REFERENCES KITS_PULSO 
    ( 
     id_kit
    ) 
;

ALTER TABLE CHAMADAS 
    ADD CONSTRAINT CHAMADAS_ATENDIMENTOS_FK FOREIGN KEY 
    ( 
     ATENDIMENTOS_id_atendimento
    ) 
    REFERENCES ATENDIMENTOS 
    ( 
     id_atendimento
    ) 
;

ALTER TABLE KITS_PULSO 
    ADD CONSTRAINT KITS_PULSO_PESSOAS_USUARIAS_FK FOREIGN KEY 
    ( 
     PESSOAS_USUARIAS_id_usuario
    ) 
    REFERENCES PESSOAS_USUARIAS 
    ( 
     id_usuario
    ) 
;

ALTER TABLE KITS_PULSO 
    ADD CONSTRAINT KITS_PULSO_PULSEIRAS_FK FOREIGN KEY 
    ( 
     PULSEIRAS_id_pulseira
    ) 
    REFERENCES PULSEIRAS 
    ( 
     id_pulseira
    ) 
;

ALTER TABLE KITS_PULSO 
    ADD CONSTRAINT KITS_PULSO_TRANSMISSORES_FK FOREIGN KEY 
    ( 
     TRANSMISSORES_id_transmissor
    ) 
    REFERENCES TRANSMISSORES 
    ( 
     id_transmissor
    ) 
;

ALTER TABLE PULSEIRAS 
    ADD CONSTRAINT PULSEIRAS_KITS_PULSO_FK FOREIGN KEY 
    ( 
     KITS_PULSO_id_kit
    ) 
    REFERENCES KITS_PULSO 
    ( 
     id_kit
    ) 
;

ALTER TABLE TRANSMISSORES 
    ADD CONSTRAINT TRANSMISSORES_KITS_PULSO_FK FOREIGN KEY 
    ( 
     KITS_PULSO_id_kit
    ) 
    REFERENCES KITS_PULSO 
    ( 
     id_kit
    ) 
;

CREATE OR REPLACE TRIGGER FKNTM_KITS_PULSO 
BEFORE UPDATE OF PULSEIRAS_id_pulseira, TRANSMISSORES_id_transmissor 
ON KITS_PULSO 
BEGIN 
  raise_application_error(-20225,'Non Transferable FK constraint  on table KITS_PULSO is violated'); 
END; 
/

CREATE OR REPLACE TRIGGER FKNTM_PULSEIRAS 
BEFORE UPDATE OF KITS_PULSO_id_kit 
ON PULSEIRAS 
BEGIN 
  raise_application_error(-20225,'Non Transferable FK constraint  on table PULSEIRAS is violated'); 
END; 
/

CREATE OR REPLACE TRIGGER FKNTM_TRANSMISSORES 
BEFORE UPDATE OF KITS_PULSO_id_kit 
ON TRANSMISSORES 
BEGIN 
  raise_application_error(-20225,'Non Transferable FK constraint  on table TRANSMISSORES is violated'); 
END; 
/



-- Relatório do Resumo do Oracle SQL Developer Data Modeler: 
-- 
-- CREATE TABLE                             7
-- CREATE INDEX                             4
-- ALTER TABLE                             18
-- CREATE VIEW                              0
-- ALTER VIEW                               0
-- CREATE PACKAGE                           0
-- CREATE PACKAGE BODY                      0
-- CREATE PROCEDURE                         0
-- CREATE FUNCTION                          0
-- CREATE TRIGGER                           3
-- ALTER TRIGGER                            0
-- CREATE COLLECTION TYPE                   0
-- CREATE STRUCTURED TYPE                   0
-- CREATE STRUCTURED TYPE BODY              0
-- CREATE CLUSTER                           0
-- CREATE CONTEXT                           0
-- CREATE DATABASE                          0
-- CREATE DIMENSION                         0
-- CREATE DIRECTORY                         0
-- CREATE DISK GROUP                        0
-- CREATE ROLE                              0
-- CREATE ROLLBACK SEGMENT                  0
-- CREATE SEQUENCE                          0
-- CREATE MATERIALIZED VIEW                 0
-- CREATE MATERIALIZED VIEW LOG             0
-- CREATE SYNONYM                           0
-- CREATE TABLESPACE                        0
-- CREATE USER                              0
-- 
-- DROP TABLESPACE                          0
-- DROP DATABASE                            0
-- 
-- REDACTION POLICY                         0
-- 
-- ORDS DROP SCHEMA                         0
-- ORDS ENABLE SCHEMA                       0
-- ORDS ENABLE OBJECT                       0
-- 
-- ERRORS                                   3
-- WARNINGS                                 0
