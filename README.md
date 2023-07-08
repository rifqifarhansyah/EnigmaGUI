# Enigma Encoder and Decoder
<h2 align="center">
   Enigma Simulator
</h2>
<hr>

## Table of Contents
1. [General Info](#general-information)
2. [Creator Info](#creator-information)
3. [Features](#features)
4. [Setup](#setup)
5. [Usage](#usage)
6. [Structure](#structure)
7. [Project Status](#project-status)
8. [Room for Improvement](#room-for-improvement)
9. [Acknowledgements](#acknowledgements)
10. [Contact](#contact)

<a name="general-information"></a>

## General Information
Sebuah aplikasi berbasis GUI (Graphical User Interface) sederhana yang dapat digunakan untuk melakukan enkripsi dan dekripsi pesan dengan metode enigma. Terdapat beberapa `pengaturan` yang dapat diubah oleh pengguna, seperti: rotor yang digunakan, initial position tiap rotor, ring dari tiap rotor, reflektor, serta plugboard yang hendak digunakan. Pengguna juga dapat melakukan import dari sebuah file txt ataupun mengexport hasil enkripsi/dekripsi dalam format txt. Aplikasi ini disusun menggunakan bahasa java. Tugas ini disusun untuk memenuhi tugas keempat seleksi Lab IRK tahun 2023.
 
<a name="creator-information"></a>

## Creator Information

| Nama                        | NIM      | E-Mail                      |
| --------------------------- | -------- | --------------------------- |
| Mohammad Rifqi Farhansyah   | 13521166 | 13521166@std.stei.itb.ac.id |

<a name="features"></a>

## Features
- Memilih `rotor` yang akan digunakan
- Meng-import atau meng-export `file txt`
- Menentukan `initial position` dan `ring` untuk tiap rotor
- Mengatur `reflektor` dan `plugboard` yang akan digunakan

<a name="setup"></a>

## Setup
1. Clone Repository ini dengan menggunakan command berikut
   ```sh
   git clone https://github.com/rifqifarhansyah/EnigmaGUI.git
   ```
2. Buka Folder "EnigmaGUI" yang telah di-clone
3. Buka terminal pada direktori tersebut
4. Masuk ke direktori src dengan menggunakan command berikut
   ```sh
   cd src
   ```
5. Jalankan command berikut untuk menjalankan program
   ```sh
   java Main
   ```

<a name="usage"></a>

## Usage
1. Pilih pengaturan untuk `rotor, initial position, ring, reflektor, serta plugboard` yang akan digunakan
2. Tekan tombol `set` ketika semuanya telah diatur
3. Klik `display` pada `menu bar` untuk memilih tampilan yang ingin digunakan (`encrypt atau decrypt`)
4. Klik `file` pada `menu bar` untuk melakukan `import atau export` file txt
5. Masukan text yang diinginkan pada kolom `input`

<a name="structure"></a>

## Structure
```bash
│   README.md
│
├───doc
│       Laporan_13521166.docx
│       Laporan_13521166.pdf
│
└───src
    │   Main.class
    │   Main.java
    │
    ├───enigma
    │       Enigma.class
    │       Enigma.java
    │       EnigmaOutput.class
    │       EnigmaOutput.java
    │       Reflector.class
    │       Reflector.java
    │       Rotor.class
    │       Rotor.java
    │       RotorEncrypt.class
    │       RotorEncrypt.java
    │
    └───gui
        │   EnigmaMenuBar$1.class
        │   EnigmaMenuBar$2.class
        │   EnigmaMenuBar$3.class
        │   EnigmaMenuBar$4.class
        │   EnigmaMenuBar$5.class
        │   EnigmaMenuBar$6.class
        │   EnigmaMenuBar$7.class
        │   EnigmaMenuBar.class
        │   EnigmaMenuBar.java
        │   Machine$1.class
        │   Machine$2.class
        │   Machine$3.class
        │   Machine$4.class
        │   Machine.class
        │   Machine.java
        │
        ├───images
        │       icon.png
        │
        ├───listener
        │       EnigmaMenuListener.class
        │       EnigmaMenuListener.java
        │       RotorListener.class
        │       RotorListener.java
        │       TypeListener.class
        │       TypeListener.java
        │
        └───panels
                DecodePanel$1.class
                DecodePanel$2.class
                DecodePanel$3.class
                DecodePanel$4.class
                DecodePanel$5.class
                DecodePanel.class
                DecodePanel.java
                EncodePanel$1.class
                EncodePanel$2.class
                EncodePanel$3.class
                EncodePanel$4.class
                EncodePanel$5.class
                EncodePanel.class
                EncodePanel.java
                PlugboardPanel$1.class
                PlugboardPanel.class
                PlugboardPanel.java
                RotorsPanel$1.class
                RotorsPanel$2.class
                RotorsPanel$3.class
                RotorsPanel.class
                RotorsPanel.java
```

<a name="project-status">

## Project Status
Project is: _complete_

<a name="room-for-improvement">

## Room for Improvement
Perbaikan yang dapat dilakukan pada program ini adalah:
- Menambahkan fungsionalitas lainnya

<a name="acknowledgements">

## Acknowledgements
- Terima kasih kepada Tuhan Yang Maha Esa

<a name="contact"></a>

## Contact
<h4 align="center">
  Kontak Saya : mrifki193@gmail.com<br/>
  2023
</h4>
<hr>
