package com.lagoinha.connect.service;

import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import com.lagoinha.connect.model.Aluno;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.HtmlEmail;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.xhtmlrenderer.simple.Graphics2DRenderer;

@Service
public class CertificadoService {

	public List<Aluno> testarPlanilha(MultipartFile multipartFile) {

		List<Aluno> alunos = new ArrayList<>();
		try {
			Reader reader = new InputStreamReader(multipartFile.getInputStream());
			CSVReader csvReader = new CSVReaderBuilder(reader).build();
			List<String[]> allData = csvReader.readAll();
			int linha = 1;
			for (String[] row : allData) {
				try {
					Aluno aluno = new Aluno();
					aluno.setNome(row[0].toUpperCase());
					aluno.setEmail(row[1]);
					alunos.add(aluno);
					linha++;
				} catch (Exception e) {
					System.out.println(linha + " - " + e.getMessage());
					linha++;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return alunos;
	}

	public List<Aluno> importarPlanilha(MultipartFile multipartFile) {

		List<Aluno> alunos = new ArrayList<>();
		try {
			Reader reader = new InputStreamReader(multipartFile.getInputStream());
			CSVReader csvReader = new CSVReaderBuilder(reader).build();
			List<String[]> allData = csvReader.readAll();
			int linha = 1;
			for (String[] row : allData) {
				try {
					Aluno aluno = new Aluno();
					aluno.setNome(row[0].toUpperCase());
					aluno.setEmail(row[1]);
					alunos.add(aluno);
					sendEmail(aluno, linha, LocalDate.now().getYear());
					linha++;
				} catch (Exception e) {
					System.out.println(linha + " - " + e.getMessage());
					linha++;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return alunos;
	}

	public File criarHtml(Aluno aluno, Integer matricula, Integer ano) {

		String texto = aluno.getNome();
		String nomeArquivo = "src/certificados/" + matricula + ".html";
		String turma = ano.toString();
		String html = "<html> <head> <style>body{width: 1457px; height: 1017px; background-image: url('certificado_start.png'); background-repeat: no-repeat;}#nome{margin-top: 37.5%; margin-left: 30%; font-family: Helvetica, sans-serif; font-size:40px; font-weight: 600;}#turma{margin-top: 10%; margin-left: 45%; font-family: Helvetica, sans-serif; font-size:40px; font-weight: 600;}</style> </head> <body> <p id=\"nome\">${texto}</p><p id=\"turma\">${turma}</p></body></html>";
		html = html.replace("${texto}", texto);
		html = html.replace("${turma}", turma);
		try {
			File htmlFile = new File(nomeArquivo);
			FileOutputStream fos = new FileOutputStream(htmlFile);
			BufferedOutputStream bos = new BufferedOutputStream(fos);
			byte[] bytes = html.getBytes();
			// write byte array to file
			bos.write(bytes);
			bos.close();
			fos.close();
			return htmlFile;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	public File criarImagem(Aluno aluno, File htmlFile, Integer ano) {

		String texto = aluno.getNome();
		String turma = ano.toString();
		String nomeImagem = aluno.getNome().split(" ")[0];
		String html = "<html> <head> <style>body{width: 1457px; height: 1017px; background-image: url('certificado_start.png'); background-repeat: no-repeat;}#nome{margin-top: 37.5%; margin-left: 30%; font-family: Helvetica, sans-serif; font-size:40px; font-weight: 600;}#turma{margin-top: 10%; margin-left: 45%; font-family: Helvetica, sans-serif; font-size:40px; font-weight: 600;}</style> </head> <body> <p id=\"nome\">${texto}</p><p id=\"turma\">${turma}</p></body></html>";
		html = html.replace("${texto}", texto);
		html = html.replace("${turma}", turma);
		try {
			// gerar imagem
			String imageFilePath = "src/certificados/" + nomeImagem + ".png";
			int WIDTH = 1457;
			int HEIGHT = 1017;
			String IMAGE_FORMAT = "png";
			String url = htmlFile.toURI().toURL().toExternalForm();
			BufferedImage image = Graphics2DRenderer.renderToImage(url, WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
			File imgFile = new File(imageFilePath);
			ImageIO.write(image, IMAGE_FORMAT, imgFile);
			return imgFile;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	public String stringHTML(Aluno aluno) {

		String nomeImagem = aluno.getNome().split(" ")[0];
		String html = "<!doctype html><html><head> <meta charset=\"UTF-8\"></head><body> <p>Olá ${nome}!</p><p>Parabéns por ter concluído o Start.</p><p>Segue em anexo seu certificado.</p><p>Atenciosamente,</p><p>Renan Figueiredo <br>Lagoinha Niteroi - START</p></body></html>";
		html = html.replace("${nome}", nomeImagem);
		return html;

	}

	public void sendEmail(Aluno aluno, Integer matricula, Integer ano) {

		String meuEmail = "renan.figueiredo.05@gmail.com";
		String senha = "xsmtpsib-b937c77d8cffc635666984bbe9bac212dadc45bb21eabad8faccc53300078b0c-SCZUx7aOGJKDy3fX";
		String subject = "CERTIFICADO START - " + aluno.getNome();

		try {
			HtmlEmail email = new HtmlEmail();
			email.setHostName("smtp-relay.sendinblue.com");
			email.setSmtpPort(587);
			email.setAuthenticator(new DefaultAuthenticator(meuEmail, senha));
			email.setSSLOnConnect(true);
			email.setFrom(meuEmail);
			email.setSubject(subject);
			email.setHtmlMsg(stringHTML(aluno));
			email.setCharset("UTF8");
			email.addTo(aluno.getEmail());

			// Anexo
			File htmlFile = criarHtml(aluno, matricula, ano);
			File anexo = criarImagem(aluno, htmlFile, ano);
			EmailAttachment attachment = new EmailAttachment();
			attachment.setPath(anexo.getPath()); // caminho da imagem
			attachment.setDisposition(EmailAttachment.ATTACHMENT);
			attachment.setDescription("Certificado do Start");
			attachment.setName("certificado");

			email.attach(attachment);
			email.send();

			// apagando o lixo
			anexo.delete();
			htmlFile.delete();

			// sucesso
			System.out.println("Email enviado com sucesso!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
