package service;

import model.Kisi;
import exception.RiskCokYuksekException;
import exception.LimitAsimiException;

// Risk skoruna göre kredi limiti ve talep uygunluğu iş kurallarına içerir
public class RiskAnalizService {

    // Risk skoruna bağlı çarpan ile maksimum kredi limitini hesaplar
    public double krediLimitiHesapla (Kisi kisi, int riskSkoru) throws RiskCokYuksekException {

        double carpan = 0;

        if (riskSkoru >= 1500) {

            carpan = 20;

        } else if (riskSkoru >= 1000) {

            carpan = 10;

        } else if (riskSkoru >= 500) {

            carpan = 4;

        } else {

            throw new RiskCokYuksekException();

        }

        return kisi.getAylikGelir() * carpan;

    }

    // Talep edilen tutarın hesaplanan limiti aşıp aşmadığını kontrol eder
    public void krediTalebiUygunMu(double talepEdilenTutar, double maxLimit) throws LimitAsimiException {

        if (talepEdilenTutar > maxLimit) {

            throw new LimitAsimiException("Talep edilen tutar limitinizi aşıyor. Maksimum limit: " + maxLimit + " TL.");

        }
    }
}
