CREATE TABLE IF NOT EXISTS banks (
                              id bigserial PRIMARY KEY NOT NULL,
                              title VARCHAR(64) NOT NULL,
                              percent_day INTEGER NOT NULL,
                              percent_week INTEGER NOT NULL,
                              percent_month INTEGER NOT NULL,
                              full_loan_coeff NUMERIC(5,1),
                              max_loan_amount NUMERIC(10,2)
);

