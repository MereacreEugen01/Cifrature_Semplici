public class CifrarioCesare {

	private byte[] messaggio;
	private int chiave;

	public CifrarioCesare(byte[] messaggio, int chiave)
	{
		this.messaggio = messaggio;
		this.chiave = chiave;
	}

	public byte[] cripta(byte[] messaggio , int chiave)
	{
		byte[] c = new byte[this.messaggio.length];
		if(this.chiave>255) this.chiave -= 255;
		byte k = (byte)this.chiave;
		for (int i = 0; i < this.messaggio.length; i++) 
		{
			byte n = (byte) (this.messaggio[i] + k);
			c[i] = n;
		}
		return c;
	}

	public byte[] decripta(byte[] messaggio , int chiave) 
	{
		byte[] c = new byte[this.messaggio.length];
		if(this.chiave>255) this.chiave -= 255;
		byte k = (byte)(this.chiave);
		for (int i = 0; i < this.messaggio.length;i++)
		{
			byte n = (byte) (this.messaggio[i] - k);
			c[i] = n;
		}
		return c;
	}
}