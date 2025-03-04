
package Server;
import java.io.PrintWriter;

public class OpisKlienta {
    String haslo;
    StringBuilder aktualnyStan;
    int pozostaleProby;
    
public OpisKlienta(String nazwa, PrintWriter wyjscie, String haslo) {
        this.haslo = haslo;
        this.pozostaleProby = 6;
        this.aktualnyStan = new StringBuilder("_".repeat(haslo.length()));
    }
boolean zgadnijLitere(char litera) {
        boolean trafiony = false;
        for (int i = 0; i < haslo.length(); i++) {
            if (haslo.charAt(i) == litera) {
                aktualnyStan.setCharAt(i, litera);
                trafiony = true;
            }
        }
        if (!trafiony) {
            pozostaleProby--;
        }
        return trafiony;
    }

    boolean czyWygrana() {
        return haslo.equals(aktualnyStan.toString());
    }

    boolean czyPrzegrana() {
        return pozostaleProby == -1;
    }

}
