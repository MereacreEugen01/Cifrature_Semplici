class CifrarioCesare {

       public byte[] cifraCesare(byte[] s , int chiave) {
    	       byte[] c = new byte[s.length];
               if(chiave>255) chiave %= 255;
               byte k = (byte)chiave;
               for (int i = 0; i < s.length; i++) {
                       byte n = (byte) (s[i] + k);
                       c[i] = n;
               }
               return c;
       }
       
       public byte[] decifraCesare(byte[] s , int chiave) {
           byte[] c = new byte[s.length];
           if(chiave>255) chiave -= 255;
           byte k = (byte)(chiave);
           for (int i = 0; i < s.length;i++) {
                   byte n = (byte) (s[i] - k);
                   c[i] = n;
           }
           return c;
   }
       
   public byte[] cifraVigenere(byte[] s , byte[] chiave) {
	   byte[] c = new byte[s.length];
               for (int i = 0; i < s.length; i++) {
                       c[i] = (byte)((byte)s[i] + (byte)chiave[i%chiave.length]);
               }
               return c;
   }
   public byte[] decifraVigenere(byte[] s , byte[] chiave) {
           byte[] c = new byte[s.length];
           for (int i = 0; i < s.length;i++) {
           c[i] = (byte)(s[i] - chiave[i%chiave.length]);
           }
           return c;
   }
}