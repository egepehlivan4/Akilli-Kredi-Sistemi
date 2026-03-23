package service;

import model.Kisi;
import model.Kredi;

import java.util.*;
import java.util.Collections;
import java.util.Comparator;

// Uygulamanın iş katmanı
public class BankaService {

    // Key: TC, Value: Kisi eşleşmesi (hızlı arama ve benzersizlik)
    private Map<String, Kisi> kisiKayitlari = new HashMap<>();

    // Dışarıya kayıtların kopyasını verir
    public HashMap<String, Kisi> hashMapAl() {

        return new HashMap<>(kisiKayitlari);

    }

    // Dosyadan okunan kayıtları bellekteki Map'e yükler (mevcut veriyi sıfırlar)
    public void verileriYukle (Map<String, Kisi> disKayitlar) {

        kisiKayitlari.clear();
        kisiKayitlari.putAll(disKayitlar);

    }

    // Duplicate kayıt önleme
    public void kisiEkle(Kisi kisi) {

        if (kisiKayitlari.containsKey(kisi.getTcNo())) {

            System.out.println("------------------------------");
            System.out.println("Hata. Bu TC kimlik numarası (" + kisi.getTcNo() + ") zaten sistemde kayıtlı.");
            System.out.println("------------------------------");

            return;

        }

        kisiKayitlari.put(kisi.getTcNo(), kisi);
        System.out.println("------------------------------");
        System.out.println(kisi.getAd() + " " + kisi.getSoyad() + " sisteme eklendi.");
        System.out.println("------------------------------");
    }

    public Kisi kisiBul(String tcNo) {

        return kisiKayitlari.get(tcNo);

    }

    public boolean kisiSil(String tcNo) {

        if (kisiKayitlari.containsKey(tcNo)) {

            kisiKayitlari.remove(tcNo);

            return true;

        }

        return false;

    }

    // Map sıralı olmadığı için önce Map -> List dönüşümü yapılır sonra Collections.sort + Comparator kullanılır
    public void kisileriAlfabetikSirala() {

        if (kisiKayitlari.isEmpty()) {

            System.out.println("Listelenecek kişi yok.");
            System.out.println("------------------------------");

            return;

        }

        List<Kisi> liste = new ArrayList<>(kisiKayitlari.values());

        Collections.sort(liste, new Comparator<Kisi>() {

            @Override
            public int compare(Kisi kisi1, Kisi kisi2) {

                return kisi1.getAd().compareTo(kisi2.getAd());

            }
        });

        System.out.println("---- ALFABETİK KİŞİ LİSTESİ ----");

        for (Kisi kisi : liste) {

            System.out.println(kisi);

        }

        System.out.println("------------------------------");

    }

    // Sistem genel raporunu metin olarak üretir
    public String sistemRaporuMetin () {

        int toplamKisi = getKisiKayitlari().size();
        int toplamKrediSayisi = 0;
        double toplamKrediTutari = 0;
        double toplamGeriOdeme = 0;

        for (Kisi kisi : getKisiKayitlari().values()) {

            for (Kredi kredi : kisi.getAktifKrediListesi()) {

                toplamKrediSayisi++;
                toplamKrediTutari += kredi.getKrediTutari();

                // toplamGeriOdemeHesapla() metodu String döndürdüğü için double'a çevrilirken virgül/nokta farkı gidiriliyor
                toplamGeriOdeme += Double.parseDouble(kredi.toplamGeriOdemeHesapla().replace(",", "."));

            }
        }

        return

                "---- SİSTEM RAPORU ----\n" +
                "Toplam kişi sayısı: " + toplamKisi + "\n" +
                "Toplam kredi sayısı: " + toplamKrediSayisi + "\n" +
                "Toplam verilen kredi tutarı: " + toplamKrediTutari + " TL\n" +
                "Toplam geri ödeme tutarı: " + String.format("%.2f", toplamGeriOdeme) + " TL\n";

    }

    // Sistemde kayıtlı tüm kişileri Collection olarak döndürür (Map'in value kısmı)
    public Collection<Kisi> getKisiler() {

        return getKisiKayitlari().values();

    }

    public Map<String, Kisi> getKisiKayitlari() {
        return kisiKayitlari;
    }

    public void setKisiKayitlari(Map<String, Kisi> kisiKayitlari) {
        this.kisiKayitlari = kisiKayitlari;
    }
}
