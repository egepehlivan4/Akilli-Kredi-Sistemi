package util;

import java.util.List;

// Generic raporlama sınıfı (tekil veri ve liste raporları)
public class GenelRapor <T> {

    private T veri;

    // Raporlanacak tekil veri alınır
    public GenelRapor(T veri) {

        this.setVeri(veri);

    }

    // Tekil veri için detaylı rapor çıktısı
    public void raporVer() {

        System.out.println("---- DETAYLI RAPOR ----");
        System.out.println(getVeri().toString());
        System.out.println("------------------------------");

    }

    // Generic liste için detaylı liste raporu
    public static <E> void listeRaporla(List<E> liste) {

        System.out.println("Listede " + liste.size() + " kayıt var: ");

        for (E eleman : liste) {

            System.out.println(liste.indexOf(eleman) + " - " + eleman.toString());

        }
    }

    // Wildcard kullanımı: listenin tek tür mü, karışık mı olduğunu özetler
    public static void ozetGec(List<?> liste) {

        if (liste.isEmpty()){

            System.out.println("Liste boş.");

        } else {

            boolean hepsiAyni = true;
            String ilkTur = liste.get(0).getClass().getSimpleName();

            for (Object eleman : liste) {

                if (!eleman.getClass().getSimpleName().equals(ilkTur)) {

                    hepsiAyni = false;
                    break;

                }
            }

            if (hepsiAyni){

                System.out.println("Bu liste " + ilkTur + " türünde nesneler içeriyor.");
                System.out.println("------------------------------");

            } else {

                System.out.println("Bu liste farklı türlerde nesneler içeriyor.");
                System.out.println("------------------------------");

            }


        }
    }

    public T getVeri() {
        return veri;
    }

    public void setVeri(T veri) {
        this.veri = veri;
    }
}
