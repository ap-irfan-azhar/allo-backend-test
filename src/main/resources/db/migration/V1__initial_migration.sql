CREATE TYPE jenis_kelamin AS ENUM ('LAKI_LAKI', 'PEREMPUAN');

CREATE TABLE dapil (
   id UUID PRIMARY KEY,
   nama_dapil VARCHAR(255) NOT NULL,
   provinsi VARCHAR(255) NOT NULL,
   jumlah_kursi INTEGER NOT NULL
);

CREATE TABLE dapil_wilayah (
   dapil_id UUID NOT NULL,
   wilayah VARCHAR(255) NOT NULL,
   PRIMARY KEY (dapil_id, wilayah),
   FOREIGN KEY (dapil_id) REFERENCES dapil (id) ON DELETE CASCADE
);

CREATE TABLE partai (
    id UUID PRIMARY KEY,
    nama_partai VARCHAR(255) NOT NULL,
    nomor_urut INTEGER NOT NULL UNIQUE
);

CREATE TABLE caleg (
   id UUID PRIMARY KEY,
   dapil_id UUID NOT NULL,
   partai_id UUID NOT NULL,
   nomor_urut INTEGER NOT NULL,
   nama VARCHAR(255) NOT NULL,
   jenis_kelamin jenis_kelamin NOT NULL,
   FOREIGN KEY (dapil_id) REFERENCES dapil (id),
   FOREIGN KEY (partai_id) REFERENCES partai (id),
   UNIQUE (dapil_id, partai_id, nomor_urut)
);