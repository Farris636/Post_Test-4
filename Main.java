package Main;

import Services.KontakService;
import Models.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        KontakService service = new KontakService();

        while (true) {
            System.out.println("\n===== MENU UTAMA =====");
            System.out.println("1. Tambah Kontak Teman");
            System.out.println("2. Tambah Kontak Keluarga");
            System.out.println("3. Lihat Semua Kontak");
            System.out.println("4. Update Kontak");
            System.out.println("5. Hapus Kontak");
            System.out.println("6. Cari Kontak");
            System.out.println("7. Urutkan Kontak (A-Z)");
            System.out.println("0. Keluar");
            System.out.print("Pilih menu: ");
            int pilih = sc.nextInt(); sc.nextLine();

            switch (pilih) {
                case 1:
                    System.out.print("Nama: ");
                    String namaT = sc.nextLine();
                    System.out.print("Nomor: ");
                    String nomorT = sc.nextLine();
                    System.out.print("Alamat: ");
                    String alamat = sc.nextLine();
                    service.addKontak(namaT, nomorT, alamat, false);
                    System.out.println("Kontak teman berhasil ditambahkan.");
                    break;
                case 2:
                    System.out.print("Nama: ");
                    String namaK = sc.nextLine();
                    System.out.print("Nomor: ");
                    String nomorK = sc.nextLine();
                    System.out.print("Hubungan: ");
                    String hubungan = sc.nextLine();
                    service.addKontak(namaK, nomorK, hubungan, true);
                    System.out.println("Kontak keluarga berhasil ditambahkan.");
                    break;
                case 3:
                    if (service.isEmpty()) {
                        System.out.println("Daftar kontak kosong.");
                    } else {
                        for (Kontak k : service.getAllKontak()) {
                            System.out.println(k);
                        }
                    }
                    break;
                case 4:
                    System.out.print("ID kontak yang ingin diupdate: ");
                    int idU = sc.nextInt(); sc.nextLine();
                    System.out.print("Nama baru: ");
                    String namaBaru = sc.nextLine();
                    System.out.print("Nomor baru: ");
                    String nomorBaru = sc.nextLine();
                    if (service.updateKontak(idU, namaBaru, nomorBaru)) {
                        System.out.println("Kontak berhasil diupdate.");
                    } else {
                        System.out.println("Kontak tidak ditemukan.");
                    }
                    break;
                case 5:
                    System.out.print("ID kontak yang ingin dihapus: ");
                    int idH = sc.nextInt(); sc.nextLine();
                    if (service.deleteKontak(idH)) {
                        System.out.println("Kontak berhasil dihapus.");
                    } else {
                        System.out.println("Kontak tidak ditemukan.");
                    }
                    break;
                case 6:
                    System.out.print("Cari nama: ");
                    String query = sc.nextLine();
                    List<Kontak> hasil = service.searchByName(query);
                    if (hasil.isEmpty()) {
                        System.out.println("Tidak ada hasil.");
                    } else {
                        for (Kontak k : hasil) {
                            System.out.println(k);
                        }
                    }
                    break;
                case 7:
                    service.sortKontakAZ();
                    System.out.println("Kontak berhasil diurutkan.");
                    break;
                case 0:
                    System.out.println("Keluar dari program...");
                    return;
                default:
                    System.out.println("Menu tidak valid.");
            }
        }
    }
}
