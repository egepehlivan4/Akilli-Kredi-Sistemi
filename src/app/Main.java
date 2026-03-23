package app;

// Kullanılan katmanlar
import model.*;
import service.BankaService;
import service.RiskAnalizService;
import service.RiskHesaplanabilir;
import util.DosyaIslemleri;
import util.GenelRapor;
import java.util.*;

public class Main {

    public static void main(String[] args) {

        String dosyaAdi = "/Users/ege/Desktop/proje3 5/Kisiler.txt";

        // Sistem başlangıcından kişiler dosyadan belleğe alınır
        HashMap<String, Kisi> tumMusteriler = DosyaIslemleri.dosyaOkuyucu(dosyaAdi);

        Scanner scanner = new Scanner(System.in);

        // Ana servis: kişi işlemleri
        BankaService bankaService = new BankaService();
        bankaService.verileriYukle(tumMusteriler);

        boolean quit = false;

        while (!quit) {

            // Menü arayüzü
            System.out.println("---- AKILLI KREDİ SİSTEMİ ----");
            System.out.println("------------------------------");
            System.out.println("1 - Yeni Kişi Kaydı Ekle");
            System.out.println("2 - Kişiyi Sorgula");
            System.out.println("3 - Kişinin Kredi Başvurusu Onay Durumu");
            System.out.println("4 - Kişinin Kredi Başvurusu Bilgileri");
            System.out.println("5 - Kişinin Kredilerini Listele");
            System.out.println("6 - Kişileri Alfabetik Sırala");
            System.out.println("7 - Kişiyi Sil");
            System.out.println("8 - Sistem Raporu");
            System.out.println("0 - Çıkış");
            System.out.println("------------------------------");
            System.out.print("Lütfen seçiminizi giriniz: ");

            int secim = 0;

            try {

                secim = scanner.nextInt();

            } catch (InputMismatchException e) {

                // Menü seçimi sayı değilse buffer temizlenip menüye dönülür
                System.out.println("Hata. Lütfen sadece rakam giriniz!");

                scanner.nextLine();

                continue;

            }

            switch (secim) {

                case 1:

                    // Yeni kişi kayıt ekranı, TC doğrulama/kayıt kontrolü/dosyaya yazma işlemleri
                    System.out.println("------------------------------");
                    System.out.print("Kişi tipi seçiniz (1 - Müşteri, 2 - Memur, 3 - Yönetici): ");
                    String denetleyici = scanner.next();

                    try {

                        short tip = Short.parseShort(denetleyici);

                        if (!(tip >= 1 && tip <= 3)) {

                            System.out.println("Lütfen geçerli bir değer giriniz.");

                            break;

                        }

                        int sonHaneDenetleyici = 0;

                        System.out.println("------------------------------");

                        System.out.print("TC kimlik numarası giriniz: ");
                        String tcNo = scanner.next();

                        // Basit TC kontrolü
                        char[] denetleyici2 = tcNo.toCharArray();

                        for (int i = 0; i < denetleyici2.length - 1; i++) {

                            int haneler = Integer.parseInt(String.valueOf(denetleyici2[i]));
                            sonHaneDenetleyici += haneler;

                        }

                        int sonHane = sonHaneDenetleyici % 10;

                        // Uzunluk ve sadece rakam kontrolü
                        if (tcNo.length() != 11 || !(tcNo.toUpperCase().equals(tcNo.toLowerCase()))) {

                            System.out.println("Geçersiz TC kimlik numarası. Tekrar deneyiniz!");

                            break;

                        }

                        // Dosyada var mı kontrolü
                        if (DosyaIslemleri.dosyaOkuyucu(dosyaAdi).containsKey(tcNo)) {

                            System.out.println("Bu TC kimlik numarası zaten kayıtlı!");

                            break;

                        }

                        if (!(denetleyici2[10] - '0' == sonHane))
                        {

                            System.out.println("Geçersiz TC kimlik numarası. Tekrar deneyin!");

                            break;

                        }

                        System.out.print("İsim giriniz: ");
                        String isim = scanner.next();

                        System.out.print("Soyisim giriniz: ");
                        String soyisim = scanner.next();

                        System.out.print("Aylık gelir giriniz (TL): ");
                        int aylikGelir = scanner.nextInt();

                        // Polymorphism: tek referansla farklı kişi tipleri üretilebilir
                        Kisi kisi = null;

                        if (tip == 1) {

                            kisi = new Musteri(isim, soyisim, tcNo, aylikGelir);

                        } else if (tip == 2) {

                            kisi = new Memur(isim, soyisim, tcNo, aylikGelir);

                        } else if (tip == 3) {

                            kisi = new Yonetici(isim, soyisim, tcNo, aylikGelir);

                        } else {

                            System.out.println("Geçersiz kişi tipi seçimi.");

                            break;

                        }

                        bankaService.kisiEkle(kisi);

                        // Güncel liste dosyaya yazılır
                        DosyaIslemleri.dosyayaYaz(bankaService.hashMapAl(), dosyaAdi);

                    } catch (NumberFormatException e) {

                        System.out.println("Lütfen geçerli bir değer giriniz!");

                    } finally {

                        break;

                    }

                case 2:

                    // TC kimlik numarası ile kişi sorgulama ve Override edilmiş özetini yazdırma işlemi
                    System.out.println("------------------------------");

                    System.out.print("TC kimlik numarası giriniz: ");
                    String tcNo2 = scanner.next();

                    System.out.println("------------------------------");

                    Kisi bulunanKisi = bankaService.kisiBul(tcNo2);

                    if (bulunanKisi == null) {

                        System.out.println("Kişi bulunamadı.");

                    } else {

                        System.out.println("Kişi bulundu.");

                        bulunanKisi.kisiOzetiYazdir();

                    }

                    System.out.println("------------------------------");

                    break;

                case 3:

                    // Kredi eklemeden önce risk skoruna göre maksimum limit gösterme işlemi
                    System.out.println("------------------------------");

                    System.out.print("TC kimlik numarası giriniz: ");
                    String tcNo3 = scanner.next();

                    System.out.println("------------------------------");

                    Kisi basvuranKisi = bankaService.kisiBul(tcNo3);

                    if  (basvuranKisi != null) {

                        System.out.println("Kişinin aylık geliri: " + basvuranKisi.getAylikGelir() + " TL");

                        System.out.println("------------------------------");

                        try {

                            System.out.print("Kişinin risk skorunu giriniz: ");
                            int girilenSkor = scanner.nextInt();

                            if (basvuranKisi instanceof Musteri) {

                                // Downcasting: Musteri'ye özel alan/metot için
                                Musteri musteri = (Musteri) basvuranKisi;
                                musteri.setRiskSkoru(girilenSkor);

                                System.out.println("Girilen risk skoru: " + musteri.getRiskSkoru());

                                // Interface referansı ile doğrulama çağrısı
                                RiskHesaplanabilir riskHesaplanabilir = musteri;
                                riskHesaplanabilir.riskSkoruHesapla();

                                RiskAnalizService riskAnalizService = new RiskAnalizService();
                                double kisiMaxLimit = riskAnalizService.krediLimitiHesapla(basvuranKisi, girilenSkor);

                                System.out.println("------------------------------");
                                System.out.println("Banka tarafından hesaplanan maksimum kredi limiti: " + kisiMaxLimit + " TL.");
                                System.out.println("------------------------------");

                            } else {

                                RiskAnalizService riskAnalizService = new RiskAnalizService();
                                double kisiMaxLimit = riskAnalizService.krediLimitiHesapla(basvuranKisi, girilenSkor);

                                System.out.println("------------------------------");
                                System.out.println("Banka tarafından hesaplanan maksimum kredi limiti: " + kisiMaxLimit + " TL.");
                                System.out.println("------------------------------");

                            }

                        } catch (exception.RiskCokYuksekException e) {

                            System.out.println("------------------------------");
                            System.out.println("İşlem başarısız. Sebep: " + e.getMessage());
                            System.out.println("------------------------------");

                        } catch (InputMismatchException e) {

                            System.out.println("------------------------------");
                            System.out.println("İşlem başarısız. Risk skoru tam sayı olmalı.");
                            System.out.println("------------------------------");

                            scanner.nextLine();

                        } catch (Exception e) {

                            System.out.println("------------------------------");
                            System.out.println("Beklenmedik hata. Hata sebebi: " + e.getMessage());
                            System.out.println("------------------------------");

                        }
                    } else {

                        System.out.println("------------------------------");
                        System.out.println("Hata. Kişi bulunamadı.");
                        System.out.println("------------------------------");

                    }

                    break;

                case 4:

                    // Kredi başvurusu, limit kontrol ve kişiye ekleme işlemleri
                    System.out.println("------------------------------");

                    System.out.print("TC kimlik numarası giriniz: ");
                    String tcNo4 = scanner.next();

                    System.out.println("------------------------------");

                    Kisi basvuranKisi2 = bankaService.kisiBul(tcNo4);

                    if (basvuranKisi2 == null) {

                        System.out.println("Hata. Aranan kişi bulunamadı.");
                        System.out.println("------------------------------");
                        break;

                    }

                    double maxLimit = 0;

                    try {

                        System.out.print("Kişinin risk skorunu giriniz (0 - 1900): ");
                        int riskSkoru = scanner.nextInt();

                        // Risk skoru aralık kontrolü
                        if (riskSkoru < 0 || riskSkoru > 1900) {

                            System.out.println("Hata. Risk skoru 0 - 1900 aralığında olmalıdır.");

                            break;

                        }

                        RiskAnalizService riskAnalizService = new RiskAnalizService();
                        maxLimit = riskAnalizService.krediLimitiHesapla(basvuranKisi2, riskSkoru);

                        System.out.print("Talep edilen kredi tutarını giriniz: ");
                        int talepEdilenTutar = scanner.nextInt();

                        // Talep edilen tutar limitten büyükse LimitAsimiException fırlatılır
                        riskAnalizService.krediTalebiUygunMu(talepEdilenTutar, maxLimit);

                        System.out.print("Kredi vadesi giriniz (Ay): ");
                        int krediVadesi = scanner.nextInt();

                        System.out.print("Kredi türü seçiniz (Ihtiyac veya Konut): ");
                        String krediTuru = scanner.next();

                        Kredi kredi = null;

                        if (krediTuru.equalsIgnoreCase("Ihtiyac")) {

                            kredi = new IhtiyacKredisi(talepEdilenTutar, krediVadesi);

                        } else if (krediTuru.equalsIgnoreCase("Konut")) {

                            kredi = new KonutKredisi(talepEdilenTutar, krediVadesi);

                        } else {

                            System.out.println("------------------------------");
                            System.out.println("Geçersiz kredi türü.");
                            System.out.println("------------------------------");

                            break;

                        }

                        // Kişi tipine göre faiz çarpanı uygulanır
                        kredi.setFaizOrani(kredi.getFaizOrani() * basvuranKisi2.faizCarpani());

                        basvuranKisi2.krediEkle(kredi);

                        System.out.println("------------------------------");
                        System.out.println("Kredi başarıyla eklendi. Kalan limit: " + (maxLimit - talepEdilenTutar) + " TL");

                    } catch (exception.RiskCokYuksekException e) {

                        System.out.println("------------------------------");
                        System.out.println("Kredi reddedildi. Sebep: "  + e.getMessage());

                    } catch (exception.LimitAsimiException e) {

                        System.out.println("------------------------------");
                        System.out.println("Kredi oluşturulamadı: " + e.getMessage());

                    } catch (InputMismatchException e) {

                        System.out.println("------------------------------");
                        System.out.println("Hata. Lütfen sayısal değer giriniz.");

                        scanner.nextLine();

                    } catch (Exception e) {

                        System.out.println("------------------------------");
                        System.out.println("Beklenmedik hata. Hata sebebi: " + e.getMessage());

                    }

                    System.out.println("------------------------------");

                    break;

                case 5:

                    // Kişinin aktif kredilerini listele (tarih ve ödeme hesapları)
                    System.out.println("------------------------------");

                    System.out.print("TC kimlik numarası giriniz: ");
                    String tcNo5 = scanner.next();

                    System.out.println("------------------------------");

                    Kisi bulunanKisi3 = bankaService.kisiBul(tcNo5);

                    if (bulunanKisi3 == null) {

                        System.out.println("Aranan kişi bulunamadı.");
                        System.out.println("------------------------------");

                        break;

                    }

                    List<Kredi> krediler = bulunanKisi3.getAktifKrediListesi();

                    if (krediler.isEmpty()) {

                        System.out.println("Kişinin aktif kredisi bulunmamaktadır.");
                        System.out.println("------------------------------");

                        break;

                    } else {

                        System.out.println("Kişinin " + krediler.size() + " tane aktif kredisi bulunmaktadır.");

                        for (Kredi kredi : krediler) {

                            System.out.println("Kredi tutarı: " + kredi.getKrediTutari());
                            System.out.println("Kredi vadesi: " + kredi.getVade() + " Ay");
                            System.out.println("Kredi faiz oranı: " + kredi.getFaizOrani());

                            // TarihUtil sınıfında LocalDateTime "dd-MM-yyyy" formatında kullanılıp yazdırılır
                            System.out.println("Kredi başlangıç tarihi: " + util.TarihUtil.tarihFormat(kredi.getBaslangicTarihi()));
                            System.out.println("Kredi bitiş tarihi: " + util.TarihUtil.tarihFormat(util.TarihUtil.vadeSonuHesapla(kredi.getBaslangicTarihi(), kredi.getVade())));
                            System.out.println("Kredi aylık ödemesi: " + kredi.aylikOdemeHesapla() + " TL");
                            System.out.println("Kredi toplam geri ödemesi: " + kredi.toplamGeriOdemeHesapla() + " TL");
                            System.out.println("------------------------------");

                            }
                        }

                    break;

                case 6:

                    // Servis üzerinden alfabetik sıralama ve ekrana yazdırma
                    System.out.println("------------------------------");
                    bankaService.kisileriAlfabetikSirala();
                    DosyaIslemleri.tumKisileriYazdir(tumMusteriler);

                    break;

                case 7:

                    // Silme: hem dosyadan hem bellekten (ikisi de kontrol edilir)
                    System.out.print("TC kimlik numarası giriniz: ");
                    String tcNo6 = scanner.next();

                    boolean dosyadanSilindi = DosyaIslemleri.kullaniciSil(dosyaAdi, tcNo6);
                    boolean bellektenSilindi = bankaService.kisiSil(tcNo6);

                    if (dosyadanSilindi) {

                        tumMusteriler.remove(tcNo6);

                    }

                    if (dosyadanSilindi || bellektenSilindi) {

                        System.out.println("Kişi silindi.");

                    } else {

                        System.out.println("TC kimlik numarası bulunamadı, kişi silinemedi.");

                    }

                    break;

                case 8:

                    // Sistem raporu, rapor satırları, generics genel rapor
                    System.out.println("------------------------------");

                    String rapor = bankaService.sistemRaporuMetin();
                    System.out.print(rapor);

                    System.out.println("------------------------------");
                    System.out.println("---- KİŞİ RAPOR SATIRLARI ----");

                    for (Kisi kisi2 : bankaService.getKisiler()) {
                        System.out.println(kisi2.raporSatiri());
                    }

                    if (bankaService.getKisiler().isEmpty()) {

                        System.out.println("Sistemde kayıtlı kişi olmadığı için rapor dosyaya yazılmadı.");

                    } else {


                        System.out.println("Sistem raporu dosyaya yazıldı.");
                    }

                    System.out.println("------------------------------");

                    if (bankaService.getKisiler().isEmpty()) {

                        System.out.println("Genel rapor oluşturmak için sistemde kişi yok.");
                        System.out.println("------------------------------");

                    } else {

                        Kisi ilkKisi = bankaService.getKisiler().iterator().next();

                        GenelRapor<Kisi> genelRapor = new GenelRapor(ilkKisi);
                        genelRapor.raporVer();

                        System.out.println("---- GENEL RAPOR LİSTESİ ----");
                        GenelRapor.listeRaporla(new ArrayList<>(bankaService.getKisiler()));

                        System.out.println("---- GENEL RAPOR ÖZETİ ----");
                        GenelRapor.ozetGec(new ArrayList<>(bankaService.getKisiler()));

                    }

                    break;

                case 0:

                    // Programdan çıkış
                    System.out.println("------------------------------");
                    System.out.println("Programdan çıkış yapıldı.");
                    System.out.println("------------------------------");

                    quit = true;

                    break;

                default:

                    System.out.println("------------------------------");
                    System.out.println("Lütfen geçerli bir seçim yapınız.");
                    System.out.println("------------------------------");

            }
        }
    }
}