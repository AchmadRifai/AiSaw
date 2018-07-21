package achmad.rifai.aisaw.beans;

public class MyResult {
	private String nama;
	private double value;

	@Override
	public String toString() {
		return "MyResult [nama=" + nama + ", value=" + value + "]";
	}

	public String getNama() {
		return nama;
	}

	public void setNama(String nama) {
		this.nama = nama;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}
}
