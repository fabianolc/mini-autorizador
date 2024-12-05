# create tables
create table cartao
(
    numero_cartao varchar(16) not null,
    senha varchar(500) not null,
    saldo decimal(15, 2) null,
    primary key(numero_cartao)
);

create table movimento_cartao
(
    id int not null auto_increment,
    numero_cartao varchar(16) not null,
    tipo_movimento varchar(10) not null,
    data_movimento date not null,
    valor decimal(15, 2) not null,
    primary key(id)
);


# create fks
alter table movimento_cartao
    add constraint fk_cartaotomovimentocartao
        foreign key (numero_cartao)
            references cartao(numero_cartao)
;


# create indexes
create index idx_numero_cartao on cartao (numero_cartao);

