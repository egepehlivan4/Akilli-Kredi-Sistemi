package util;

import model.Kisi;
import model.Memur;
import model.Musteri;
import model.Yonetici;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class DosyaIslemleri {

    private static String dosyaAdi;

    // Bellekteki kişi kayıtlarını dosyaya yazar (append modunda)
    public static void dosyayaYaz(HashMap<String, Kisi> kisiKayitlari, String dosyaAdi) {

       try (BufferedWriter yazici = new BufferedWriter(new FileWriter(dosyaAdi, true))) {

           for (Map.Entry<String, Kisi> girdi : kisiKayitlari.entrySet()) {

               Kisi kisi = girdi.getValue();

               // Kişi tipine göre satır formatı oluşturulur
               if (kisi instanceof Musteri) {

                   Musteri musteri = (Musteri) kisi;
                   yazici.write("Müşteri; " + musteri.getAd() + "; " + musteri.getSoyad() + "; " + musteri.getTcNo() + "; " + musteri.getAylikGelir());

               } else if (kisi instanceof Memur) {

                   Memur memur = (Memur) kisi;
                   yazici.write("Memur; " + memur.getAd() + ";" + memur.getSoyad() + ";" + memur.getTcNo() + "; " + memur.getAylikGelir());

               } else if (kisi instanceof Yonetici) {

                   Yonetici yonetici = (Yonetici)  kisi;
                   yazici.write("Yönetici; " + yonetici.getAd() + "; " + yonetici.getSoyad() + "; " + yonetici.getTcNo() + "; " + yonetici.getAylikGelir());

               }

               yazici.newLine();

           }

           System.out.println("Veriler dosyaya kaydedildi.");

       } catch (IOException e) {

           System.out.println("Dosyaya kaydediledi. " + e.getMessage());

       }

    }

    // Dosyadaki kayıtları okuyup TC -> Kisi Map'i oluşturur
    public static HashMap<String, Kisi> dosyaOkuyucu(String dosyaAdi) {

        HashMap<String, Kisi> kisiKayitlari = new HashMap<>();

        try (BufferedReader okuyucu = new BufferedReader(new FileReader(dosyaAdi))) {

            String line;

            while ((line = okuyucu.readLine()) != null) {

                String[] parcalar = line.split(";");

                Kisi kisi = parcadanKisiOlustur(parcalar);

                if (kisi != null) {

                    kisiKayitlari.put(kisi.getTcNo() , kisi);

                }
            }

            System.out.println("Dosyadan (" + kisiKayitlari.size() + " kayıt alındı.)");

        } catch (IOException e) {

            System.out.println("Dosya okunamadı: " + e.getMessage());

        }

        return kisiKayitlari;

    }

    // Dosya satırındaki parçalardan uygun Kisi alt sınıfını üretir
    private static Kisi parcadanKisiOlustur(String[] parcalar) {

        if (parcalar.length < 5) {

            System.out.println("Eksik veri!" + Arrays.toString(parcalar));

            return null;

        }

        String tur = parcalar[0];
        String ad = parcalar[1];
        String soyad = parcalar[2];
        String tc = parcalar[3];
        int aylikGelir;

        try {

            aylikGelir = Integer.parseInt(parcalar[4].replace(" ", ""));

        } catch (NumberFormatException e) {

            System.out.println("Hatalı aylık gelir formatı: " + parcalar[4]);
            System.out.println("Satır: " + Arrays.toString(parcalar));

            return null;

        }

        // Kişi tipi bilgisine göre nesne oluşturulur
        switch (tur) {

            case "Müşteri":

                return new Musteri(ad,soyad,tc,aylikGelir);

            case "Memur":

                return new Memur(ad,soyad,tc,aylikGelir);

            case "Yönetici":

                return new Yonetici(ad,soyad,tc,aylikGelir);

            default:

                System.out.println("Bilinmeyen tür.");

                return null;
        }
    }

    // Bellekteki Map üzerinden TC ile kişi bulur
    public static Kisi kimliktenKullaniciBul(String tcNo, HashMap<String, Kisi> kisiKayitlari) {

        return kisiKayitlari.get(tcNo);

    }

    // Bellekteki tüm kişileri ekrana yazdırır
    public static void tumKisileriYazdir(HashMap<String, Kisi> kisiKayitlari) {

        System.out.println("\n === TÜM KİŞİLER === ");

        for(Kisi kisi : kisiKayitlari.values()) {

            System.out.println(kisi);

        }

        System.out.println("====================");
    }

    // Dosya içinde TC numarasına göre kişi arar
    public static Kisi kimliktenDosyadaBul(String tcNo, String dosyaAdi) {

        try(BufferedReader okuyucu = new BufferedReader(new FileReader(dosyaAdi))) {

            String line;

            while ((line = okuyucu.readLine()) != null) {

                String[] parcalar = line.split(";");

                if(parcalar.length >=2 && parcalar[1].equals(tcNo)) {

                    return parcadanKisiOlustur(parcalar);

                }
            }
        } catch (IOException ex) {

            System.out.println("Dosya arama hatası: " + ex.getMessage());

        }

        return null;

    }

    // Dosyadan belirtilen TC'ye sahip kişiyi siler
    public static boolean kullaniciSil (String dosyaAdi, String tcNo) {

        try {

            List<String> satirlar = Files.readAllLines(Paths.get(dosyaAdi));
            List<String> yeniSatirlar = new ArrayList<>();

            boolean bulundu = false;

            for(String satir : satirlar) {

                String[] parcalar = satir.split(";");

                if(parcalar.length > 4 && parcalar[3].equals(tcNo)) {

                    bulundu = true;

                    System.out.println(tcNo + " TcNo lu birey silindi.");

                } else {

                    yeniSatirlar.add(satir);

                }
            }

            if (!bulundu) {

                System.out.println("--- TcNo bulunamadı ---");

                return false;

            }

            Files.write(Paths.get(dosyaAdi), yeniSatirlar);

            return true;

        } catch (IOException ex) {

            System.out.println("Hata: "+ex.getMessage());

            return false;

        }
    }

    public static String getDosyaAdi() {
        return dosyaAdi;
    }

    public static void setDosyaAdi(String dosyaAdi) {
        DosyaIslemleri.dosyaAdi = dosyaAdi;
    }
}
