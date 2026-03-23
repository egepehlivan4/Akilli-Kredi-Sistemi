package model;

// Kurum içi çalışanları temsil eden ara soyut sınıf
// Musteri ile Personel ayrımını netleştirir
public abstract class Personel extends Kisi {

    // Personelin çalıştığı departman
    private String departman;

    // Ortak kişi bilgileri ve personel departmanı atanır
    public Personel(String ad, String soyad, String tcNo, int aylikGelir, String departman) {

        super(ad, soyad, tcNo, aylikGelir);

        this.setDepartman(departman);

    }

    public String getDepartman() {

        return departman;

    }

    public void setDepartman(String departman) {
        this.departman = departman;
    }
}
