package model;

import service.Raporlanabilir;
import java.util.ArrayList;
import java.util.List;

// Sistemdeki tüm kişi türlerinin ortak üst sınıfı
public abstract class Kisi implements Raporlanabilir {

    private String ad;
    private String soyad;
    private String tcNo;
    private int aylikGelir = 10000;

    // Kişinin sahip olduğu aktif krediler
    private List<Kredi> aktifKrediListesi;

    // Ortak alanları set eden üst sınıf constructor'ı
    public Kisi(String ad, String soyad, String tcNo, int aylikGelir) {

        this.setAd(ad);
        this.setSoyad(soyad);
        this.setTcNo(tcNo);
        this.setAylikGelir(aylikGelir);

        // Her kişi oluşturulduğunda boş bir kredi listesi atanır
        this.setAktifKrediListesi(new ArrayList<>());

    }

    // Kişiye yeni bir kredi ekler
    public void krediEkle(Kredi kredi) {
        getAktifKrediListesi().add(kredi);
    }

    // Faiz çarpanı metodu
    public double faizCarpani() {
        return 1.0;
    }

    public List<Kredi> getAktifKrediListesi() {
        return aktifKrediListesi;
    }

    public int getAylikGelir() {
        return aylikGelir;
    }

    public String getAd() {
        return ad;
    }

    public String getSoyad() {
        return soyad;
    }

    public String getTcNo() {
        return tcNo;
    }

    // Alt sınıflar kendi kişi tipini belirtmek zorundadır
    public abstract String getKisiTipi();

    // Kişiye ait özet bilgi yazdırma sorumluluğu alt sınıflardadır
    public abstract void kisiOzetiYazdir();

    @Override
    public String toString() {

        if (this instanceof Personel) {

            Personel personel = (Personel) this;
            return personel.getDepartman() + ": " + personel.getAd() + " " + personel.getSoyad() + " " + personel.getTcNo();

        }

        return "Müşteri: " + getAd() + " " + getSoyad() + " " + getTcNo();

    }

    // Raporlama için standart kişi satırı formatı
    @Override
    public String raporSatiri() {

        return getKisiTipi() + ": "  + getAd() + " " + getSoyad() + " (TC:" + getTcNo() + ")";

    }

    public void setAd(String ad) {
        this.ad = ad;
    }

    public void setSoyad(String soyad) {
        this.soyad = soyad;
    }

    public void setTcNo(String tcNo) {
        this.tcNo = tcNo;
    }

    public void setAylikGelir(int aylikGelir) {
        this.aylikGelir = aylikGelir;
    }

    public void setAktifKrediListesi(List<Kredi> aktifKrediListesi) {
        this.aktifKrediListesi = aktifKrediListesi;
    }
}
