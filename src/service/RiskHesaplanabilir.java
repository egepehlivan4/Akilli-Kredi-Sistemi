package service;

import exception.RiskCokYuksekException;

// Risk skoru ve kredi limiti hesaplanabilen varlıklar için sözleşme
public interface RiskHesaplanabilir {

    // Risk skorunun geçerliliğini kontrol eder
    void riskSkoruHesapla();

    // Risk skoruna göre hesaplanan maksimum kredi tutarını döndürür
    double maxKrediTutariHesapla() throws RiskCokYuksekException;

}
