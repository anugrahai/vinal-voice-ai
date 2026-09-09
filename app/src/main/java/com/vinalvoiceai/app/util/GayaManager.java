package com.vinalvoiceai.app.util;

import android.content.Context;

import com.vinalvoiceai.app.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GayaManager {
    public static class Gaya {
        public int id;
        public String name;
        public String description;
        public Gaya(int id, String name, String description) {
            this.id = id; this.name = name; this.description = description;
        }
    }

    public static class Category {
        public int id;
        public String name;
        public List<Gaya> items = new ArrayList<>();
        public Category(int id, String name) { this.id = id; this.name = name; }
    }

    public static List<Category> getAllCategories(Context ctx) {
        List<Category> cats = new ArrayList<>();
        // Category 1: Chat Biasa
        Category c1 = new Category(1, ctx.getString(R.string.gaya_cat_1));
        c1.items.addAll(Arrays.asList(
            new Gaya(101, "Chat EYD Rapi (Tapi Spoken)", "Ketik rapi sesuai EYD, tapi tetap terasa ngobrol."),
            new Gaya(102, "Chat Formal (EYD Presisi)", "Bahasa baku presisi untuk situasi resmi."),
            new Gaya(103, "Chat Jeda Napas Panjang", "Pakai jeda natural saat typing panjang."),
            new Gaya(104, "Chat Ketikan Cepat (Jelas & Ringkas)", "Singkat, jelas, to the point."),
            new Gaya(105, "Chat Minimalis Elegan", "Simpel tapi elegan, hemat kata."),
            new Gaya(106, "Chat Rapi Natural (WA Style)", "Gaya WhatsApp natural."),
            new Gaya(107, "Chat Santai (One-Liner)", "Hemat, satu kalimat tapi bermakna."),
            new Gaya(108, "Chat Santai Huruf Kecil", "Santai, huruf kecil semua."),
            new Gaya(109, "Chat Serius & Tegas", "Tegas, serius, gak ada basa-basi."),
            new Gaya(110, "Chat Sopan & Mengalir", "Sopan tapi tetap mengalir natural."),
            new Gaya(111, "Singkatan Normal (yg, dgn, utk)", "Singkatan umum yang familiar.")
        ));
        cats.add(c1);

        // Category 2: Chat Gaul
        Category c2 = new Category(2, ctx.getString(R.string.gaya_cat_2));
        c2.items.addAll(Arrays.asList(
            new Gaya(201, "Anak Skena (Kalcer & Chill)", "Skena, kalcer, anak nongkrong."),
            new Gaya(202, "Anak Twitter (Savage & Cerdas)", "Twitter style, tajam & cerdas."),
            new Gaya(203, "Curhat Bestie (Emosional)", "Curhat ke bestie, emosional."),
            new Gaya(204, "Ekstra Singkat (Fast Reply)", "Bales chat secepat mungkin."),
            new Gaya(205, "Gamer Ngegas Positif", "Gamer vibe, ngegas positif."),
            new Gaya(206, "Gaya Manja (Bucin)", "Manja, bucin, minta perhatian."),
            new Gaya(207, "Hype Antusias (Capslock Jebol)", "Antusias, CAPSLOCK penuh!"),
            new Gaya(208, "Jaksel Vibes (Literally)", "Jakarta Selatan abis, pakai 'literally'."),
            new Gaya(209, "Mager Santuy", "Malas gerak, tapi tetep asik."),
            new Gaya(210, "Sarkas Elegan (Bercanda)", "Sarkas tapi elegan, candaan halus."),
            new Gaya(211, "Singkatan Ekstrem Gaul (ak, km, yg)", "Singkatan super gaul."),
            new Gaya(212, "Slang Sosmed Kekinian", "Slang TikTok/IG jaman sekarang."),
            new Gaya(213, "Tongkrongan Asyik (Gua/Lu)", "Gua/lu, anak tongkrongan.")
        ));
        cats.add(c2);

        // Category 3: Ungkapan Perasaan
        Category c3 = new Category(3, ctx.getString(R.string.gaya_cat_3));
        c3.items.addAll(Arrays.asList(
            new Gaya(301, "Bucin Brutal (Cinta Mati)", "Cinta mati, bucin parah."),
            new Gaya(302, "Cuek Bebek (Mental Pemenang)", "Cuek, mental juara."),
            new Gaya(303, "Deep Talk Baper (Nyentuh Hati)", "Ngobrol dalem, bikin baper."),
            new Gaya(304, "Galau Elegan (Mahal & Detail)", "Galau tapi tetap classy."),
            new Gaya(305, "Kecewa Berat Tapi Ikhlas", "Kecewa tapi masih bisa nerima."),
            new Gaya(306, "Manipulatif Halus (Guilt-Trip)", "Manipulasi halus, bikin nyesek."),
            new Gaya(307, "Nyindir Halus (Sarkas Tongkrongan)", "Sindiran halus anak tongkrongan."),
            new Gaya(308, "Permintaan Maaf Tulus", "Minta maaf dengan tulus."),
            new Gaya(309, "Puitis Anak Senja (Estetik & Dalam)", "Puitis, estetik, dalem."),
            new Gaya(310, "Savage & Menohok (Nodong Kepastian)", "Savage, ngegas minta kejelasan."),
            new Gaya(311, "Tarik Ulur (Playful & Menggoda)", "Playful, menggoda."),
            new Gaya(312, "Ucapan Terima Kasih Mendalam", "Makasih yang bermakna.")
        ));
        cats.add(c3);

        // Category 4: Motivasi Kasar
        Category c4 = new Category(4, ctx.getString(R.string.gaya_cat_4));
        c4.items.addAll(Arrays.asList(
            new Gaya(401, "Motivasi Kasar (Real Talk)", "Real talk, gak ada basa-basi."),
            new Gaya(402, "Motivasi Santai (Bestie Mode)", "Motivasi dari bestie, lembut."),
            new Gaya(403, "Motivasi Keras (Life Coach)", "Tegas, life coach mode."),
            new Gaya(404, "Motivasi Religi (Tenang)", "Motivasi religi, adem.")
        ));
        cats.add(c4);

        return cats;
    }
}
