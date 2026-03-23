package model;

import java.time.LocalDateTime;

// Tüm kredi türleri için ortak üst sınıf
public abstract class Kredi {

    // Krediye ait ana para tutarı
    private int krediTutari;

    // Kredi için uygulanacak faiz oranı
    private double faizOrani;

    // Kredinin vadesi (ay cinsinden)
    private int vade;

    // Kredinin başlangıç tarihi
    private LocalDateTime baslangicTarihi;

    // Ortak kredi alanlarını set eden üst sınıf constructor'ı
    public Kredi (int krediTutari, int krediVadesi, double faizOrani) {

        this.setKrediTutari(krediTutari);
        this.setVade(krediVadesi);
        this.setFaizOrani(faizOrani);
        this.setBaslangicTarihi(LocalDateTime.now());

    }

    // Aylık ödeme tutarını hesaplama sorumluluğu alt sınıflara bırakılmıştır
    public abstract String aylikOdemeHesapla();

    // Toplam geri ödeme tutarını hesaplama sorumluluğu alt sınıflara bırakılmıştır
    public abstract String toplamGeriOdemeHesapla();

    public int getKrediTutari() {
        return krediTutari;
    }

    public void setFaizOrani(double faizOrani) {

        this.faizOrani = faizOrani;

    }
    public double getFaizOrani() {
        return faizOrani;
    }

    public int getVade() {
        return vade;
    }

    public LocalDateTime getBaslangicTarihi() {
        return baslangicTarihi;
    }

    public void setBaslangicTarihi(LocalDateTime baslangicTarihi) {

        this.baslangicTarihi = baslangicTarihi;

    }

    // Kredi nesnesinin kısa özet metni
    @Override
    public String toString() {

        return "Kredi: " + getKrediTutari() + " TL (" + getVade() + " ay vade)";

    }

    public void setKrediTutari(int krediTutari) {
        this.krediTutari = krediTutari;
    }

    public void setVade(int vade) {
        this.vade = vade;
    }
}
