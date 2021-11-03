class CifrarioCesare {

       public byte[] cripta(byte[] s , int chiave) {
    	       byte[] c = new byte[s.length];
               if(chiave>255) chiave -= 255;
               byte k = (byte)chiave;
               for (int i = 0; i < s.length; i++) {
                       byte n = (byte) (s[i] + k);
                       c[i] = n;
               }
               return c;
       }
       
       public byte[] decripta(byte[] s , int chiave) {
           byte[] c = new byte[s.length];
           if(chiave>255) chiave -= 255;
           byte k = (byte)(chiave);
           for (int i = 0; i < s.length;i++) {
                   byte n = (byte) (s[i] - k);
                   c[i] = n;
           }
           return c;
   }
}