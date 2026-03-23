package exception;

// Risk skoru yetersiz olduğunda kredi işlemini durdurmak için kullanılan exception
public class RiskCokYuksekException extends Exception{

    // Varsayılan hata mesajı ile exception oluşturur
    public RiskCokYuksekException(){

        super("Risk skoru yetersiz.");

    }
}
