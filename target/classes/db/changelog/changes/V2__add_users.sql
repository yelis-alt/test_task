
INSERT INTO business.users (name, date_of_birth, password) VALUES
    ('Иванов Иван Иванович', '1990-05-14', '$2a$12$lPvGEc/L4RJLKrQSVt4jH.WkqmJjV3afor3QWikFKHglEi0bm3iWi'),
    ('Краснов Станислав Андреевич', '1985-09-22', '$2a$12$EHiBLJ4mIf4XBcDGqTL/3eNt0cngi777yKQwGYTGDbCKyvX568Gv2'),
    ('Семенов Семен Дмитриевич', '1992-01-30', '$2a$12$ApHSp.EM1pod3zhOlAaJRuPYkxzdngkHomcX4KS9Qja.IRTMhM2t6'),
    ('Снежинский Дмитрий Венеаминович', '1988-11-10', '$2a$12$wb4UxgjkqUUkQcMAF8C8..3NFfBOu.F/jkinv/QqQYq53517jLfMS');

INSERT INTO business.account (user_id, initial_balance, balance) VALUES
    (1, 2500.75, 2500.75),
    (2, 1500.25, 1500.25),
    (3, 1000.10, 1000.10),
    (4, 3000.00, 3000.00);

INSERT INTO business.email_data (user_id, email) VALUES
    (1, 'ivan12@fgh.com'),
    (2, 'kras23@yan.ru'),
    (2, 'krasnov25@yan.ru'),
    (3, 'semka@der.com'),
    (4, 'dmit@der.com');

INSERT INTO business.phone_data (user_id, phone) VALUES
    (1, '72345678901'),
    (2, '79876543210'),
    (3, '71234567890'),
    (3, '76234567897'),
    (4, '70987654321');
