create table mobil
(
	id serial not null,
	merk varchar (25) not null,
	tipe varchar (15) not null,
	no_kendaraan varchar (15) not null,
	harga int not null,
	jumlah int not null,
	is_avaible boolean DEFAULT true,
	primary key(id),
	constraint no_kendaraan_unique unique (no_kendaraan)
);


create table sewa_mobil
(
	id serial not null,
	nama_customer varchar(30) not null,
	no_ktp varchar(16) not null,
	tanggal date not null default current_date,
	is_return boolean default false,
	id_mobil int not null,
	primary key(id),
	constraint fk_id_car foreign key (id_mobil) references mobil(id),
	constraint no_ktp_unique unique (no_ktp)
);

create table return_mobil
(
	id serial not null,
	jumlah_pembayaran int not null,
	jangka_sewa int not null,
	tanggal_kembali date not null,
	no_ktp_customer varchar(16) not null,
	constraint fk_no_ktp foreign key (no_ktp_customer) references sewa_mobil(no_ktp),
	constraint no_ktp_customer_unique unique (no_ktp_customer)
);