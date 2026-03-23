package model;

import service.RiskAnalizService;
import service.RiskHesaplanabilir;
import java.time.LocalDateTime;

// Banka sistemindeki müşteri tipini temsil eden sınıf
// Risk hesaplama yeteneğine sahiptir
public class Musteri extends Kisi implements RiskHesaplanabilir {

    // Kredi değerlendirmesinde kullanılan risk skoru
    private int riskSkoru;

    // Müşterinin sisteme kayıt olduğu tarih
    private LocalDateTime kayitTarihi;

    // Müşteri nesnesi oluşturulurken ortak kişi bilgileri üst sınıfa gönderilir
    public Musteri(String ad, String soyad, String tcNo, int aylikGelir) {

        super(ad, soyad, tcNo, aylikGelir);
        this.setKayitTarihi(LocalDateTime.now());
        this.setRiskSkoru(0);

    }

    // Kişi tipini belirtir
    @Override
    public String getKisiTipi() {

        return "Müşteri";

    }

    // Müşteriye ait özet bilgileri yazdırır
    @Override
    public void kisiOzetiYazdir() {

        System.out.println(getKisiTipi() + ": " + getAd() + " " + getSoyad() + " (TC: " + getTcNo() + "). Aylık gelir: " + getAylikGelir() + " TL.");

    }

    // Risk skorunun geçerli aralıkta olup olmadığını kontrol eder
    @Override
    public void riskSkoruHesapla() {

        if (getRiskSkoru() < 0 || getRiskSkoru() > 1900) {

            System.out.println("Uyarı! Risk skoru aralık dışında.");

        }
    }

    // Müşteri için maksimum kredi tutarını hesaplar
    @Override
    public double maxKrediTutariHesapla() throws exception.RiskCokYuksekException {

        RiskAnalizService riskAnalizService = new RiskAnalizService();

        return riskAnalizService.krediLimitiHesapla(this, getRiskSkoru());


    }

    public int getRiskSkoru() {

        return riskSkoru;

    }

    public void setRiskSkoru(int riskSkoru) {

        this.riskSkoru = riskSkoru;

    }

    public LocalDateTime getKayitTarihi() {
        return kayitTarihi;
    }

    public void setKayitTarihi(LocalDateTime kayitTarihi) {
        this.kayitTarihi = kayitTarihi;
    }
}
