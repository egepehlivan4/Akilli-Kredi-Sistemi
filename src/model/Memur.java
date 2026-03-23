package model;

// Personel türlerinden biri olan Memur
public class Memur extends Personel {

    // Memur için departman bilgisi sabit olarak atanır
    public Memur(String ad, String soyad, String tcNo, int aylikGelir) {

        super(ad, soyad, tcNo, aylikGelir, "Memur");

    }

    // Memurlara özel avantajlı faiz çarpan
    @Override
    public double faizCarpani() {

        return 0.95;

    }

    // Kişi tipini belirtir (Polymorphism ile üst sınıf üzerinden çağrılır)
    @Override
    public String getKisiTipi() {

        return "Memur";

    }

    // Memura ait özet bilgileri ekrana yazdırır
    @Override
    public void kisiOzetiYazdir() {

        System.out.println(getKisiTipi() + ": " + getAd() + " " + getSoyad() + " (TC: " + getTcNo() + "). Aylık gelir: " + getAylikGelir() + " TL.");

    }
}
