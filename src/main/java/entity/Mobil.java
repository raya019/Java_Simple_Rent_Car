package entity;

import java.util.Objects;

public class Mobil {

    private Integer id;

    private String merk;

    private String tipe;

    private String noKendaraan;

    private Integer harga;

    private Integer jumlah;

    private boolean isAvaible = true;

    public Mobil() {
    }

    public Mobil(Integer id, String merk, String tipe, String noKendaraan, Integer harga, Integer jumlah, boolean isAvaible) {
        this.id = id;
        this.merk = merk;
        this.tipe = tipe;
        this.noKendaraan = noKendaraan;
        this.harga = harga;
        this.jumlah = jumlah;
        this.isAvaible = isAvaible;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMerk() {
        return merk;
    }

    public void setMerk(String merk) {
        this.merk = merk;
    }

    public String getTipe() {
        return tipe;
    }

    public void setTipe(String tipe) {
        this.tipe = tipe;
    }

    public String getNoKendaraan() {
        return noKendaraan;
    }

    public void setNoKendaraan(String noKendaraan) {
        this.noKendaraan = noKendaraan;
    }

    public Integer getHarga() {
        return harga;
    }

    public void setHarga(Integer harga) {
        this.harga = harga;
    }

    public Integer getJumlah() {
        return jumlah;
    }

    public void setJumlah(Integer jumlah) {
        this.jumlah = jumlah;
    }

    public boolean isAvaible() {
        return isAvaible;
    }

    public void setAvaible(boolean avaible) {
        isAvaible = avaible;
    }

    @Override
    public String toString() {
        return "Mobil{" +
                "id=" + id +
                ", merk='" + merk + '\'' +
                ", tipe='" + tipe + '\'' +
                ", noKendaraan='" + noKendaraan + '\'' +
                ", harga=" + harga +
                ", jumlah=" + jumlah +
                ", isAvaible=" + isAvaible +
                '}';
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;

        Mobil mobil = (Mobil) object;

        if (isAvaible != mobil.isAvaible) return false;
        if (!Objects.equals(id, mobil.id)) return false;
        if (!Objects.equals(merk, mobil.merk)) return false;
        if (!Objects.equals(tipe, mobil.tipe)) return false;
        if (!Objects.equals(noKendaraan, mobil.noKendaraan)) return false;
        if (!Objects.equals(harga, mobil.harga)) return false;
        return Objects.equals(jumlah, mobil.jumlah);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (merk != null ? merk.hashCode() : 0);
        result = 31 * result + (tipe != null ? tipe.hashCode() : 0);
        result = 31 * result + (noKendaraan != null ? noKendaraan.hashCode() : 0);
        result = 31 * result + (harga != null ? harga.hashCode() : 0);
        result = 31 * result + (jumlah != null ? jumlah.hashCode() : 0);
        result = 31 * result + (isAvaible ? 1 : 0);
        return result;
    }
}
