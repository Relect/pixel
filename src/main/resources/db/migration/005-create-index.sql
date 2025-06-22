--liquibase formatted sql
--changeset Gennadiy:005
--preconditions onFail:HALT onError:HALT


CREATE INDEX idx_user_date_of_birth ON "user" (date_of_birth);
CREATE INDEX idx_user_name ON "user" (name);

CREATE INDEX idx_email_data_email ON email_data USING HASH (email);
CREATE INDEX idx_email_data_user_id ON email_data USING HASH (user_id);

CREATE INDEX idx_phone_data_phone ON phone_data USING HASH (phone);
CREATE INDEX idx_phone_data_user_id ON phone_data USING HASH (user_id);