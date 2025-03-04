
package Server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashSet;
import java.util.List;
import java.util.Random;


public class ObslugaKlienta extends Thread {
    private Socket socket;
    private BufferedReader wejscie;
    private PrintWriter wyjscie;
    private OpisKlienta opis;
    private ServerWisielec server;
    static HashSet<OpisKlienta> klienci = new HashSet<>();
     private static final List<String> slowa = ZarzadzanieSlowami.czytajSlowa();
     
      public ObslugaKlienta(Socket socket, ServerWisielec server) throws IOException {
        this.socket = socket;
        this.server = server;
        wejscie = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        wyjscie = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
    }

  
   @Override
    public void run() {
       
String nazwaKlienta = null;
        boolean polaczenieOtwarte = true;

        try {
            while (nazwaKlienta == null || nazwaKlienta.length() < 3) {
            wyjscie.println("Podaj nazwę (minimum 3 litery): ");
            nazwaKlienta = wejscie.readLine();

         if (nazwaKlienta == null) {
               
                polaczenieOtwarte = false;
                break;
            }

            if (nazwaKlienta.length() < 3) {
                wyjscie.println("Nazwa gracza musi zawierać przynajmniej 3 litery.");
                nazwaKlienta = null;
            }
            
        }

        if (!polaczenieOtwarte) {
            return;
        }

        server.log("Zalogował się: " + nazwaKlienta);
        wyjscie.println("Nazwa gracza: " + nazwaKlienta);

            while (polaczenieOtwarte) {
                rozpocznijNowaGre(nazwaKlienta);

                boolean kontynuujGre = true;

                while (kontynuujGre) {
                    wyswietlStanGry();
                    wyjscie.println("Zgadnij literę: ");
                    String linia = wejscie.readLine();

                    if (linia == null) {
                        polaczenieOtwarte = false;
                        break;
                    }

                    if (linia.equalsIgnoreCase("NOWA_GRA")) {
                        kontynuujGre = false;
                        break;
                    }

                    if (linia.equalsIgnoreCase("WYLOGUJ")) {
                        server.log(nazwaKlienta + " się wylogował.");
                        polaczenieOtwarte = false;
                        kontynuujGre = false;
                        break;
                    }

                    if (linia.length() != 1) {
                        wyjscie.println("Podaj tylko jedną literę.");
                        continue;
                    }

                    char litera = linia.charAt(0);
                    if (!Character.isLetter(litera)) {
                        wyjscie.println("Podaj literę.");
                        continue;
                    }

                    litera = Character.toLowerCase(litera);
                    if (opis.zgadnijLitere(litera)) {
                        wyjscie.println("Trafiony!");
                    } else {
                        wyjscie.println("Chybiony!");
                    }

                    if (opis.czyWygrana()) {
                        wyjscie.println("Gratulacje! Wygrałeś! Słowo to: " + opis.haslo);
                        server.log(nazwaKlienta + " wygrał/a!");
                        kontynuujGre = false;
                        break;
                    } else if (opis.czyPrzegrana()) {
                        wyjscie.println("Przegrałeś! Słowo to: " + opis.haslo);
                        server.log(nazwaKlienta + " przegrał/a!");
                        kontynuujGre = false;
                        break;
                    }
                }
            }

            klienci.remove(opis);
            wejscie.close();
            wyjscie.close();
            socket.close();
        } catch (IOException e) {
            server.log("Błąd: " + e.getMessage());
        }
    }
    // Dodatkowe metody 
     private void rozpocznijNowaGre(String nazwaKlienta) {
        String haslo = wybierzHaslo();
        opis = new OpisKlienta(nazwaKlienta, wyjscie, haslo);
        klienci.add(opis);
    }
    private void wyswietlStanGry() {
        wyjscie.println("Słowo: " + opis.aktualnyStan.toString());
        wyjscie.println("Pozostałe próby: " + opis.pozostaleProby);
    }

    private String wybierzHaslo() {
        Random rand = new Random();
        return slowa.get(rand.nextInt(slowa.size()));
    }
    
    
}