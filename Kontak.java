package Models;

/**
 * Abstract Class: Kontak
 * Menerapkan Abstraction
 */
public abstract class Kontak {
    private int id;
    private String nama;
    private String nomor;

    public Kontak(int id, String nama, String nomor) {
        this.id = id;
        this.nama = nama;
        this.nomor = nomor;
    }

    public int getId() {
        return id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getNomor() {
        return nomor;
    }

    public void setNomor(String nomor) {
        this.nomor = nomor;
    }

    // Abstract method
    public abstract String getKategori();

    // Default toString (bisa dioverride di subclass)
    @Override
    public String toString() {
        return "[" + id + "] " + nama + " - " + nomor;
    }
}
