# Post_Test-4
Nama: Moch. Farris Alfiansyah Nim: 2409116079

# Sistem Catatan Kontak

## Deskripsi Program
Program Sistem Catatan Kontak adalah aplikasi berbasis console yang digunakan untuk mencatat daftar kontak dengan kategori Keluarga dan Teman.

Fitur utama:
- Tambah kontak (Create)
- Lihat semua kontak (Read)
- Ubah kontak (Update)
- Hapus kontak (Delete)
- Sortir kontak A–Z
- Cari kontak berdasarkan nama
- Validasi input (nama tidak boleh kosong, nomor telepon hanya angka)

Arsitektur menggunakan konsep **MVC sederhana**:
- `model` → struktur data
- `service` → logika CRUD
- `main` → menu interaksi user

## Struktur project Catatan_Kontak dibagi menjadi tiga package utama:

<img width="269" height="235" alt="image" src="https://github.com/user-attachments/assets/6ab7c165-631c-4740-95ec-1712030588d4" />

### Main
- Berisi file `Main.java` sebagai entry point program.
Menampilkan menu utama, membaca input user, dan menjalankan CRUD.

### Models
- `Kontak.java` → superclass utama dengan atribut dasar (id, nama, nomor).
- `Keluarga.java` → subclass dari Kontak, kategori keluarga.
- `Teman.java` → subclass dari Kontak, kategori teman.
- `IKontak.java` → interface abstrak untuk menampilkan info kontak.

### Services

**KontakService.java → berisi logika utama CRUD, pencarian, dan sorting.**
Penerapan OOP
1. Encapsulation
Atribut id, nama, nomor di class Kontak dibuat private.
Akses hanya melalui getter dan setter.
2. Inheritance
Keluarga dan Teman adalah subclass dari Kontak.
3. Abstraction
Interface IKontak digunakan untuk kontrak method tampilkanInfo().
4. Polymorphism
Overriding: tampilkanInfo() dioverride di Keluarga dan Teman.
Overloading: cariKontak() di KontakService ada 2 versi (nama & id).
Alur Program
Program dijalankan → tampil menu utama.
User memilih menu (Tambah, Lihat, Ubah, Hapus, Cari, Sortir, Keluar).
Method di KontakService dijalankan sesuai pilihan.
Hasil operasi ditampilkan di console.
Menu utama akan tampil lagi sampai user memilih Keluar.


## Alur Program
1. Program dijalankan → tampil menu utama.
2. User memilih menu (Tambah, Lihat, Ubah, Hapus, Cari, Sortir, Keluar).
3. Program memproses pilihan menggunakan method di `KontakService`.
4. Hasil operasi ditampilkan di layar.
5. Program kembali ke menu utama sampai user memilih **Keluar**.

---

## Penjelasan Kode

### 1. `Kontak.java`
```java
ppackage Models;

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

```

Penjelasan:

1. public abstract class Kontak → Mendefinisikan class abstrak yang tidak bisa diinstansiasi langsung, hanya bisa diturunkan (inheritance).
2. private int id; private String nama; private String nomor; → Atribut dasar yang dimiliki setiap kontak, yaitu id, nama, dan nomor telepon.
3. public Kontak(int id, String nama, String nomor) → Konstruktor untuk menginisialisasi data kontak.
4. Getter & Setter (getId(), getNama(), setNama(), getNomor(), setNomor()) → Method untuk mengakses dan mengubah nilai atribut.
5. public abstract String getKategori(); → Method abstrak yang wajib dioverride oleh subclass (misalnya Keluarga atau Teman) untuk memberikan kategori kontak.
6. @Override public String toString() → Method untuk menampilkan data kontak dalam format string sederhana [id] nama - nomor. Method ini bisa dioverride lagi di subclass bila perlu.


### 2. `KontakService.java`
```java
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

```
Penjelasan:

1. private final List<Kontak> daftar; → Menyimpan daftar seluruh kontak menggunakan ArrayList.
2. private int nextId; → Memberikan ID unik secara otomatis setiap kali kontak baru ditambahkan.
3. Overloading →
- addKontak(String nama, String nomor) → menambahkan kontak default kategori Teman.
- addKontak(String nama, String nomor, String tambahan, boolean isKeluarga) → menambahkan kontak baik kategori Keluarga atau Teman dengan data tambahan.
Kedua method ini menunjukkan penerapan polymorphism overloading.
4. getAllKontak() → Mengembalikan seluruh kontak dalam bentuk list baru agar data asli tidak langsung bisa dimodifikasi dari luar.
5. updateKontak(int id, String namaBaru, String nomorBaru) → Memperbarui nama dan nomor telepon kontak berdasarkan ID.
6. deleteKontak(int id) → Menghapus kontak berdasarkan ID.
7. sortKontakAZ() → Mengurutkan kontak berdasarkan nama dari A–Z menggunakan Comparator.
8. searchByName(String query) → Mencari kontak berdasarkan nama yang mengandung kata tertentu (case-insensitive).
9. findById(int id) → Mencari kontak berdasarkan ID.
10. isEmpty() → Mengecek apakah daftar kontak masih kosong atau tidak.

### 3. `Keluarga.java`
```java
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

```
Penjelasan:

1. Class Keluarga merupakan turunan dari Kontak dan menerapkan konsep inheritance.
2. Memiliki atribut tambahan hubungan untuk menyimpan relasi keluarga seperti ayah, ibu, atau saudara.
3. Constructor digunakan untuk menginisialisasi data dari superclass Kontak sekaligus atribut hubungan.
4. Getter dan setter digunakan untuk mengakses dan mengubah nilai atribut hubungan.
5. Method getKategori() mengembalikan nilai kategori “Keluarga” sebagai implementasi dari method abstrak di superclass.
6. Method toString() dioverride agar menampilkan format informasi kontak yang lebih detail dengan label [Keluarga], nama, hubungan, dan nomor.
7. Penerapan polymorphism terlihat dari overriding method toString().


### 4. `Kontak.java`
```Java
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
```

Penjelasan:

1. Class Kontak didefinisikan sebagai abstract class sehingga tidak bisa dibuat objek langsung dari class ini.
2. Atribut id, nama, dan nomor menggunakan modifier private untuk menerapkan encapsulation, sehingga akses hanya melalui getter dan setter.
3. Constructor digunakan untuk menginisialisasi nilai id, nama, dan nomor ketika objek dibuat.
4. Method getKategori() bersifat abstrak sehingga wajib dioverride oleh subclass seperti Keluarga dan Teman.
5. Method toString() sudah didefinisikan dengan format default, namun bisa dioverride pada subclass agar tampilan data lebih spesifik.
7. File ini menjadi pondasi inheritance karena subclass akan mewarisi atribut dan method dari Kontak.

### 5. `Teman.java`
```Java
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
```
Penjelasan:

1. Class Teman adalah subclass dari Kontak yang menerapkan inheritance.
2. Atribut tambahan alamat digunakan untuk menyimpan informasi tempat tinggal teman.
3. Constructor Teman memanggil constructor Kontak untuk mengisi id, nama, dan nomor, kemudian menambahkan alamat.
4. Getter dan setter digunakan untuk mengakses dan mengubah nilai alamat.
5. Method getKategori() dioverride untuk mengembalikan string "Teman" sesuai kategori objek.
6. Method toString() juga dioverride agar menampilkan data lebih detail, yaitu label [Teman], nama, nomor, dan alamat.
7. Dengan adanya overriding, class ini menunjukkan contoh polymorphism karena method dari superclass ditulis ulang dengan implementasi berbeda.

## Output 

<img width="296" height="256" alt="image" src="https://github.com/user-attachments/assets/7047730a-3a84-4ee1-871b-83d88077c88c" />

Tampilan dari awal program dijalankan.

### Menu 1
<img width="410" height="145" alt="image" src="https://github.com/user-attachments/assets/ca136785-5adc-4aa0-a8e4-712c5dd3674b" />

Tampilan dari menu 1 yaitu menambah kontak dengan memasukkan nama dan nomor handphone.

### Menu 2
<img width="431" height="147" alt="image" src="https://github.com/user-attachments/assets/434abb9c-07eb-4233-895c-c0e343914681" />

Tampilan dari menu 2 yaitu menambah kontak keluarga dengan memasukkan nama, nomor handphone dan hubungan keluarga.

### Menu 3
<img width="554" height="84" alt="image" src="https://github.com/user-attachments/assets/93d98b42-679d-4be4-9064-c72035bf805c" />

Tampilan dari menu 3 yaitu untuk melihat nama kontak yang sudah dimasukkan sebelumnya.

### Menu 4
<img width="396" height="127" alt="image" src="https://github.com/user-attachments/assets/adbe72d0-a804-4a7e-9f47-c115b431948c" />

Tampilan dari menu 4 yaitu mengubah kontak sesuai dengan ID nya.

### Menu 5
<img width="371" height="76" alt="image" src="https://github.com/user-attachments/assets/1fd9a9e8-870c-49c1-a89b-d9d5491564b7" />

Tampilan dari menu 5 yaitu menghapus kontak sesuai dengan ID nya.

### Menu 6
<img width="538" height="86" alt="image" src="https://github.com/user-attachments/assets/725d051d-1202-441b-a136-588e7ea77323" />

Tampilan dari menu 6 yaitu untuk mencari Kontak dengan menggunakan Nama yang ditambahkan.

### Menu 7
<img width="564" height="141" alt="image" src="https://github.com/user-attachments/assets/a8809a04-e0b7-4146-9bd5-9367c6c4ae26" />

Tampilan sebelum menggunakan menu 7 yaitu sortir sesuai abjad yang dimulai dari huruf A.

<img width="576" height="137" alt="image" src="https://github.com/user-attachments/assets/43b781e5-1330-4d93-b40c-dbb937c868b5" />

Tampilan setelah menggunakan sortir sesuai abjad.


### Menu 0
<img width="268" height="48" alt="image" src="https://github.com/user-attachments/assets/33a104ce-11ef-4e75-9457-1dff3325c072" />

Tampilan dari menu 0 yaitu keluar dari program.












