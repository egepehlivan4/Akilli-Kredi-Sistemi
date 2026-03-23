package util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

// Tarih formatlama ve vade hesaplama yardımcı sınıfı
public class TarihUtil {

    // Uygulama genelinde kullanılacak tarih formatı
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    // LocalDateTime değerini standart formatta String'e çevirir
    public static String tarihFormat(LocalDateTime tarih) {

        if (tarih == null) {

            return "Tarih yok.";

        } else {

            return tarih.format(getFormatter());

        }
    }

    // Başlangıç tarihine vade ayı ekleyerek bitiş tarihini hesaplar
    public static LocalDateTime vadeSonuHesapla(LocalDateTime baslangic, int vadeAy) {

        return baslangic.plusMonths(vadeAy);

    }

    // Formatter'e kontrollü erişim sağlar
    public static DateTimeFormatter getFormatter() {
        return formatter;
    }
}
