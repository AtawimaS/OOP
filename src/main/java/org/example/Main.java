package org.example;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


class Head {
    static void head() {
        System.out.print("\033[H\033[2J");
        System.out.flush();

//        System.out.println("");
//        System.out.println("==============");
    }
}

class Student {
    String nama;
    String id;
    String gender;
    int umur;
    Kelas kelas;
    ArrayList<MataPelajaran> MataPelajaranList = new ArrayList<>();

    public void tambahMataPelajaran(MataPelajaran mataPelajaran) {
//        MataPelajaranList = new ArrayList<>();
        MataPelajaranList.add(mataPelajaran);
    }

    public void View(String namaFile){
        Student siswaRead = Student.fromJson(namaFile);
        if (siswaRead != null) {
            System.out.println("ID      : " + siswaRead.id);
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            System.out.println("1. Nama    : " + siswaRead.nama);
            System.out.println("2. Gender  : " + siswaRead.gender);
            System.out.println("3. Umur    : " + siswaRead.umur);
            System.out.println("4. Kelas   : " + siswaRead.kelas.namaKelas);

            System.out.println("5. Mata Pelajaran:");
            for (MataPelajaran mataPelajaran : siswaRead.MataPelajaranList) {
                System.out.println("  - " + mataPelajaran.namaPelajaran + " (Guru: " + mataPelajaran.namaGuru + ")");
            }
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        }
    }

    public String toJson() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(this);
    }
    public static Student fromJson(String namaFILE) {
        Gson gson = new Gson();
        try (Reader reader = new FileReader(namaFILE)) {
            return gson.fromJson(reader, Student.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

class Kelas{
    String namaKelas;
    String walikelas;

    public void inputKelas(Scanner input) {
        System.out.print("Input nama kelas: ");
        this.namaKelas = input.next();
        System.out.print("Input wali kelas: ");
        this.walikelas = input.next();
    }
}

class MataPelajaran extends Student{
    String namaPelajaran;
    String namaGuru;

    public void inputMataPelajaran(Scanner input) {
        System.out.print("Input nama mata pelajaran: ");
        this.namaPelajaran = input.next();
        System.out.print("Input nama guru: ");
        this.namaGuru = input.next();
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        String eks = ".json";
        while (true) {
            System.out.println("1. Create\n2. View\n3. Edit\n4. Exit");
            int pilih_1 = input.nextInt();
            switch (pilih_1) {
                case 1:
                    Student siswa = new Student();
                    System.out.print("Input nama siswa: ");
                    siswa.nama = input.next();

                    System.out.print("Input id siswa: ");
                    siswa.id = input.next();
                    String namaFile = siswa.id.concat(eks);

                    System.out.print("Input gender siswa: ");
                    siswa.gender = input.next();

                    System.out.print("Input umur siswa: ");
                    siswa.umur = input.nextInt();

                    Kelas kelas = new Kelas();
                    kelas.inputKelas(input);
                    siswa.kelas = kelas;
                    do{
                        MataPelajaran mataPelajaran = new MataPelajaran();
                        mataPelajaran.inputMataPelajaran(input);
                        siswa.tambahMataPelajaran(mataPelajaran);
                        System.out.print("Tambah mata pelajaran lagi? (1: Ya, 0: Tidak): ");
                    }while(input.nextInt() == 1);

                    String siswaJson = siswa.toJson();
                    System.out.println(siswaJson);

                    try (FileWriter file = new FileWriter(namaFile)) {
                        file.write(siswaJson);
                        file.flush();
                        System.out.println("File JSON telah berhasil dibuat: " + namaFile);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case 2:
                    System.out.print("Masukan ID siswa yang akan di lihat : ");
                    String IDinput = input.next();
                    String namaFile1 = IDinput.concat(eks);
                    Student reading = new Student();
                    reading.View(namaFile1);

                    break;
                case 3:
                    Head.head();

                    System.out.print("Masukan ID siswa yang akan di edit : ");
                    String IDinput_edit = input.next();

                    String namaFile_edit = IDinput_edit.concat(eks);
                    Student siswa_edit = Student.fromJson(namaFile_edit);
                    Kelas kelas_edit = new Kelas();
                    siswa_edit.View(namaFile_edit);
                    System.out.println("Baris yang akan di update (0..5) : ");
                    int pilih_edit = input.nextInt();
                    switch (pilih_edit){
                        case 1:
                            System.out.print("Ganti nama dari " + siswa_edit.nama + " menjadi ");
                            siswa_edit.nama = input.next();
                            break;
                        case 2:
                            System.out.print("Ganti gender dari "+ siswa_edit.gender + " menjadi ");
                            siswa_edit.gender = input.next();
                            break;
                        case 3:
                            System.out.print("Ganti umur dari "+ siswa_edit.umur + " menjadi ");
                            siswa_edit.umur = input.nextInt();
                            break;
                        case 4:
                            kelas_edit.inputKelas(input);
                            break;
                        case 5:
                            break;
                    }
                    String siswaJson_edit = siswa_edit.toJson();
                    try (FileWriter file = new FileWriter(namaFile_edit)) {
                        file.write(siswaJson_edit);
                        file.flush();
                        System.out.println(namaFile_edit + " telah terupdate!");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case 4:
                    System.exit(0);
                    break;
                default:
                    break;
            }
        }
    }
}
