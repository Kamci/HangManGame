
package Server;
import java.io.*;
import java.util.*;

public class ZarzadzanieSlowami {
    private static final String sciezka = "slowa.csv";

    public ZarzadzanieSlowami() {
    }
    
    public static List<String> czytajSlowa(){
        List<String> slowa = new ArrayList<>();
        try (BufferedReader br = new BufferedReader (new FileReader(sciezka))){
            String linia;
            while ((linia = br.readLine()) != null){
                slowa.add(linia.trim());
            }
        }catch (IOException e){
            System.out.println(e);
        }
        return slowa;
    }
    
    public static void dodajSlowo(String slowo){
        try(PrintWriter pw = new PrintWriter(new FileWriter(sciezka,true))){
            pw.println(slowo);
        }catch (IOException e){
            System.out.println(e);
        }
    }
    
}
