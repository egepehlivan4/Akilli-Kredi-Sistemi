package model;

// Konut kredisi türü, Kredi soyut sınıfından türetilmiştir
public class KonutKredisi extends Kredi {

    // Konut kredisi için sabit temel faiz oranı
    private static final double baseFaiz = 0.0259;

    // Kredi tutarı ve vade bilgileri ile konut kredisi oluşturulur
    public KonutKredisi(int krediTutari, int krediVadesi) {

        super(krediTutari, krediVadesi, getBaseFaiz());

    }

    public static double getBaseFaiz() {
        return baseFaiz;
    }

    // Aylık ödeme tutarını hesaplayan metot
    @Override
    public String aylikOdemeHesapla() {

        double toplamFaiz = getKrediTutari() * getVade() * getFaizOrani();
        double toplamOdeme = getKrediTutari() + toplamFaiz;

        // Aylık ödeme tutarı iki ondalık basamakla formatlanarak döndürülür
        return String.format("%.2f", toplamOdeme/getVade());

    }

    // Toplam geri ödeme tutarını hesaplayan metot
    @Override
    public String toplamGeriOdemeHesapla() {

        double toplamFaiz = getKrediTutari() * getVade() * getFaizOrani();

        // Toplam geri ödeme tutarı formatlanarak döndürülür
        return String.format("%.2f", getKrediTutari() + toplamFaiz);

    }
}
