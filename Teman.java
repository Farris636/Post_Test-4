package Models;

public class Teman extends Kontak {
    private String alamat;

    public Teman(int id, String nama, String nomor, String alamat) {
        super(id, nama, nomor);
        this.alamat = alamat;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    @Override
    public String getKategori() {
        return "Teman";
    }

    // Overriding toString
    @Override
    public String toString() {
        return "[Teman] " + getNama() + " - " + getNomor() + " | Alamat: " + alamat;
    }
}
