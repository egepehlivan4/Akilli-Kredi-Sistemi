package model;

// İhtiyaç kredisi türü, Kredi soyut sınıfından türetilmiştir
public class IhtiyacKredisi extends Kredi {

    // İhtiyaç kredisi için sabit temel faiz oranı
    private static final double baseFaiz = 0.035;

    // Kredi tutarı ve vade bilgileri ile ihtiyaç kredisi oluşturur
    public IhtiyacKredisi(int krediTutari, int krediVadesi) {

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

        return String.format("%.2f", toplamOdeme/getVade());

    }

    // Toplam geri ödeme tutarını hesaplayan metot
    @Override
    public String toplamGeriOdemeHesapla() {

        double toplamFaiz = getKrediTutari() * getVade() * getFaizOrani();

        return String.format("%.2f", getKrediTutari() + toplamFaiz);

    }
}
