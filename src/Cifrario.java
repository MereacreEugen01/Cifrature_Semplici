import java.util.*;
public class Cifrario {

       public static void main(String[] args) {
               CifrarioCesare cifra = new CifrarioCesare();
               Scanner t = new Scanner(System.in);
               String s;
               int chiave;
               System.out.println("Inserire il messaggio: ");
               s = t.nextLine();
               byte[] frase = s.getBytes();
               System.out.println("Inserire la chiave: ");
               char[] v1;
               v1 = t.nextLine().toCharArray();
               byte [] key = new byte[v1.length];
               for(int i = 0; i < v1.length; i++)
            	   key[i] = (byte)v1[i];
               byte[] c= cifra.cifraVigenere(frase , key);
               byte[] d= cifra.decifraVigenere(c , key);
               System.out.print("Stringa cifrata : ");
               for(byte st : c)
            	   System.out.print((int)st);
               System.out.print("\nStringa cecifrata : " );
               for(byte st : d)
            	   System.out.print((char)st);
               t.close();      }
}