package achmad.rifai.aisaw.util;

import java.io.IOException;
import java.util.List;

import com.google.gson.JsonArray;

import achmad.rifai.aisaw.beans.*;

public class Work {
	public static List<Syarat> loadSyarat(String syarat_path) throws IOException {
		List<Syarat>l=new java.util.LinkedList<>();
		java.io.File f=new java.io.File(syarat_path);
		com.google.gson.JsonParser p=new com.google.gson.JsonParser();
		if(f.exists()) {
			java.io.FileReader r=new java.io.FileReader(f);
			JsonArray a=(JsonArray) p.parse(r);
			for(int x=0;x<a.size();x++) {
				com.google.gson.JsonElement i=a.get(x);
				Syarat s=new Syarat();
				s.setCb(i.getAsJsonObject().get("cb").getAsString());
				s.setNama(i.getAsJsonObject().get("nama").getAsString());
				s.setKep(i.getAsJsonObject().get("kep").getAsFloat());
				l.add(s);
			} r.close();
		}else System.out.println("Syarat.json not found");
		return l;
	}

	public static List<Data> loadData(String data_path) throws IOException {
		List<Data>l=new java.util.LinkedList<>();
		java.io.File f=new java.io.File(data_path);
		com.google.gson.JsonParser p=new com.google.gson.JsonParser();
		if(f.exists()) {
			java.io.FileReader r=new java.io.FileReader(f);
			JsonArray a=(JsonArray) p.parse(r);
			for(int x=0;x<a.size();x++) {
				com.google.gson.JsonObject i=a.get(x).getAsJsonObject();
				Data d=new Data();
				d.setKrit(i.get("krit").getAsString());
				d.setNama(i.get("nama").getAsString());
				d.setValue(i.get("value").getAsFloat());
				l.add(d);
			} r.close();
		}else System.out.println("Data.json not found");
		return l;
	}

	public static boolean isOk(List<Syarat> ls) {
		float f=0;
		for(Syarat s:ls)f+=s.getKep();
		return f==1;
	}

	public static Matriks genData(List<Data> ld, List<Syarat> ls) {
		List<String>ln=listNamaData(ld);
		Matriks m=new Matriks("Matriks Data",ln.size(),ls.size());
		for(int x=0;x<m.getBaris();x++) {
			String n=ln.get(x);
			for(int y=0;y<m.getKolom();y++) {
				Syarat s=ls.get(y);
				m.set(x, y, getValueData(ld,n,s.getNama()));
			}
		}
		return m;
	}

	private static double getValueData(List<Data> ld, String nama, String krit) {
		double f=0;
		for(Data d:ld)
			if(nama.equals(d.getNama())&&krit.equals(d.getKrit()))
				f=d.getValue();
		return f;
	}

	private static List<String> listNamaData(List<Data> ld) {
		List<String>l=new java.util.LinkedList<>();
		ld.forEach((d)->{
			String s=d.getNama();
			if(!l.contains(s))l.add(s);
		});
		return l;
	}

	public static Matriks genSyarat(List<Syarat> ls) {
		Matriks m=new Matriks("Syarat",1,ls.size());
		for(int y=0;y<ls.size();y++) {
			Syarat s=ls.get(y);
			m.set(0, y, s.getKep());
		}
		return m;
	}

	public static Matriks genPembagi(Matriks dm, List<Syarat> ls) {
		Matriks m=new Matriks("Pembagi",1,ls.size());
		for(int y=0;y<ls.size();y++) {
			Syarat s=ls.get(y);
			double f=0;
			for(int x=0;x<dm.getBaris();x++) {
				if(f!=0) {
					if("BENEFIT".equals(s.getCb()))
						f=Math.max(f, dm.get(x, y));
					else f=Math.min(f, dm.get(x, y));
				}else f=dm.get(x, y);
			} m.set(0, y, f);
		} return m;
	}

	public static Matriks normaling(Matriks dm, List<Syarat> ls, Matriks pembagi) {
		Matriks m=new Matriks("Normalisasi",dm.getBaris(),dm.getKolom());
		for(int x=0;x<m.getBaris();x++)for(int y=0;y<m.getKolom();y++) {
			Syarat s=ls.get(y);
			double f;
			if("BENEFIT".equals(s.getCb()))
				f=dm.get(x, y)/pembagi.get(0, y);
			else f=pembagi.get(0, y)/dm.get(x, y);
			m.set(x, y, f);
		} return m;
	}

	public static List<MyResult> genHasil(Matriks normal, List<Data> ld, List<Syarat> ls) {
		List<String>ln=listNamaData(ld);
		List<MyResult>l=new java.util.LinkedList<>();
		for(int x=0;x<normal.getBaris();x++) {
			MyResult m=new MyResult();
			m.setNama(ln.get(x));
			double f=0;
			for(int y=0;y<ls.size();y++) {
				Syarat s=ls.get(y);
				f+=s.getKep()*normal.get(x, y);
			} m.setValue(f);
			l.add(m);
		} return l;
	}
}
