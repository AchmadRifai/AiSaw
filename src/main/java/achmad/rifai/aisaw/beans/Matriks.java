package achmad.rifai.aisaw.beans;

public class Matriks {
	private String nama;
	private int baris,kolom;
	private double[]v;

	public Matriks(String nama, int baris, int kolom) {
		super();
		this.nama = nama;
		this.baris = baris;
		this.kolom = kolom;
		initField();
	}

	private void initField() {
		v=new double[baris*kolom];
		for(int x=0;x<baris;x++)
			for(int y=0;y<kolom;y++)v[y+(x*kolom)]=0;
	}

	public void set(int x, int y, double f) {
		if(x<baris&&y<kolom) v[y+(x*kolom)]=f;
		else System.out.println("Index terlalu besar");
	}

	public double get(int x, int y) {
		double f=0;
		if(x<baris&&y<kolom)f=v[y+(x*kolom)];
		else System.out.println("Index terlalu besar");
		return f;
	}

	@Override
	public String toString() {
		String s=nama+"\n";
		for(int x=0;x<baris;x++) {
			for(int y=0;y<kolom;y++)
				s+=""+v[y+(x*kolom)]+" ";
			s+="\n";
		} return s;
	}

	public int getBaris() {
		return baris;
	}

	public int getKolom() {
		return kolom;
	}
}
