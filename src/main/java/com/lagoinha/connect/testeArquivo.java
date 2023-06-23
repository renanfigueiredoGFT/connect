package com.lagoinha.connect;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.time.LocalDate;
import java.util.Scanner;

public class testeArquivo {
	
	public static void main(String[] args) {
		File arq = gerarArquivo(5);
		System.out.println("Quantidade de Linhas: "+ qtdLinhasTotal(arq));
	}
	
	public static String gerarCabecalho() {
		return "Linha;Data";
	}
	
	public static String gerarLinha(Integer linha) {
		LocalDate data = LocalDate.now();
		return  linha + ";" + data;
	}
	
	public static File gerarArquivo(Integer qtdLinhas) {
		File file = new File("C:\\testes\\tmp.txt");
		try {
			OutputStream os = new FileOutputStream(file); 
	        Writer wr = new OutputStreamWriter(os); 
	        BufferedWriter br = new BufferedWriter(wr);
	        br.write(gerarCabecalho());
	        br.newLine();
	        for(int i = 1; i <= qtdLinhas; i++) {
	        	br.write(gerarLinha(i));
	        	br.newLine();
	        }
	        br.close();
		} catch (Exception e) {
		}
		return file;
	}
	
	public static Integer qtdLinhasTotal(File file) {
		int count = 0;
		Scanner sc;
		try {
			sc = new Scanner(file);
			 while(sc.hasNextLine()) {
			        sc.nextLine();
			        count++;
			      }
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return count;
	}

}
