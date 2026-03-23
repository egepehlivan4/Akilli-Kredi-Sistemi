package service;

// Rapor üretebilen nesneler için ortak davranış
public interface Raporlanabilir {

    // Nesnenin raporda kullanılacak tek satırlık özet bilgisini döndürür
    String raporSatiri();

}
