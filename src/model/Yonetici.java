package model;

// Personel türlerinden biri olan Yönetici
public class Yonetici extends Personel {

    // Yönetici için departman bilgisi sabit olarak atanır
    public Yonetici(String ad, String soyad, String tcNo, int aylikGelir) {

        super(ad, soyad, tcNo, aylikGelir, "Yönetici");

    }

    // Yöneticilere özel daha avantajlı faiz çarpanı
    @Override
    public double faizCarpani() {

        return 0.90;

    }

    // Kişi tipini belirtir
    @Override
    public String getKisiTipi() {

        return "Yönetici";

    }

    // Yöneticiye ait özet bilgileri ekrana yazdırır
    @Override
    public void kisiOzetiYazdir() {

        System.out.println(getKisiTipi() + ": " +  getAd() + " " + getSoyad() + " (TC: " + getTcNo() + "). Aylık gelir: " + getAylikGelir() + " TL.");

    }
}

