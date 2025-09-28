package Services;

import Models.*;
import java.util.*;

public class KontakService {
    private final List<Kontak> daftar;
    private int nextId;

    public KontakService() {
        this.daftar = new ArrayList<>();
        this.nextId = 1;
    }

    // Overloading addKontak
    public Kontak addKontak(String nama, String nomor) {
        Kontak k = new Teman(nextId++, nama, nomor, ""); // default teman tanpa alamat
        daftar.add(k);
        return k;
    }

    public Kontak addKontak(String nama, String nomor, String tambahan, boolean isKeluarga) {
        Kontak k;
        if (isKeluarga) {
            k = new Keluarga(nextId++, nama, nomor, tambahan);
        } else {
            k = new Teman(nextId++, nama, nomor, tambahan);
        }
        daftar.add(k);
        return k;
    }

    public List<Kontak> getAllKontak() {
        return new ArrayList<>(daftar);
    }

    public boolean updateKontak(int id, String namaBaru, String nomorBaru) {
        Kontak k = findById(id);
        if (k == null) return false;
        k.setNama(namaBaru);
        k.setNomor(nomorBaru);
        return true;
    }

    public boolean deleteKontak(int id) {
        Kontak k = findById(id);
        if (k == null) return false;
        return daftar.remove(k);
    }

    public void sortKontakAZ() {
        Collections.sort(daftar, Comparator.comparing(k -> k.getNama().toLowerCase()));
    }

    public List<Kontak> searchByName(String query) {
        List<Kontak> hasil = new ArrayList<>();
        String q = query.toLowerCase();
        for (Kontak k : daftar) {
            if (k.getNama().toLowerCase().contains(q)) {
                hasil.add(k);
            }
        }
        return hasil;
    }

    public Kontak findById(int id) {
        for (Kontak k : daftar) {
            if (k.getId() == id) return k;
        }
        return null;
    }

    public boolean isEmpty() {
        return daftar.isEmpty();
    }
}
