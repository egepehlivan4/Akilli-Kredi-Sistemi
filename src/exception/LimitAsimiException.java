package exception;

// Kredi talebi, hesaplanan maksimum limiti aştığında fırlatılan özel exception
public class LimitAsimiException extends Exception {

    // Hata nedeni dışarıdan mesaj olarak alınır
    public LimitAsimiException(String message) {

        // Üst sınıfa hata mesajı iletilir
        super(message);

    }
}
