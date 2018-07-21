package achmad.rifai.aisaw;

import java.io.IOException;
import java.util.List;

import achmad.rifai.aisaw.beans.*;
import achmad.rifai.aisaw.util.Work;

public class Main {

	public static void main(String[] args) {
		String data_path="parame/data.json",syarat_path="parame/syarat.json";
		try {
			List<Syarat>ls=Work.loadSyarat(syarat_path);
			List<Data>ld=Work.loadData(data_path);
			if(Work.isOk(ls)) {
				Matriks dm=Work.genData(ld,ls),ds=Work.genSyarat(ls),pembagi=
						Work.genPembagi(dm,ls),normal=
						Work.normaling(dm,ls,pembagi);
				System.out.println(""+dm+"\n"+ds+"\n"+pembagi+"\n"+normal);
				List<MyResult>lm=Work.genHasil(normal,ld,ls);
				lm.forEach((l)->System.out.println(l));
			}else System.out.println("Syarat tidak terpenuhi");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
