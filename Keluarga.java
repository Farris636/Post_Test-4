package Models;

public class Keluarga extends Kontak {
    private String hubungan;

    public Keluarga(int id, String nama, String nomor, String hubungan) {
        super(id, nama, nomor);
        this.hubungan = hubungan;
    }

    public String getHubungan() {
        return hubungan;
    }

    public void setHubungan(String hubungan) {
        this.hubungan = hubungan;
    }

    @Override
    public String getKategori() {
        return "Keluarga";
    }

    // Overriding toString
    @Override
    public String toString() {
        return "[Keluarga] " + getNama() + " (" + hubungan + ") - " + getNomor();
    }
}
