
INSERT INTO users (login, password, role)
values
    ('${admin.email}', crypt('${admin.password}', gen_salt('bf')), 'ADMIN')
