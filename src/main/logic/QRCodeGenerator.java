/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.logic;

/**
 *
 * @author Francisco Valente
 */
import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;

public class QRCodeGenerator {
    public static final String QR_CODE_IMAGE_PATH = "./qrs/MyQRCode.png";
    private static final String CHARSET = "UTF-8";

    public static void generateQRCode(String texto, String path) {
        try {
            Map<EncodeHintType, Object> hints = new HashMap<>();
            hints.put(EncodeHintType.CHARACTER_SET, CHARSET);
            hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);

            BitMatrix bitMatrix = new MultiFormatWriter().encode(texto, BarcodeFormat.QR_CODE, 200, 200, hints);

            Path outputPath = Paths.get(path);
            File outputFile = outputPath.toFile();

            if (outputFile.exists()) {
                // O arquivo já existe, criar um novo nome de arquivo
                int contador = 1;

                String novoNomeArquivo = "./qrs/MyQRCode" + contador + ".png";
                while (Files.exists(Paths.get(novoNomeArquivo))) {
                    contador++;
                    novoNomeArquivo = "./qrs/MyQRCode" + contador + ".png";
                }
                outputPath = Paths.get(novoNomeArquivo);
                outputFile = outputPath.toFile();
            }

            MatrixToImageWriter.writeToPath(bitMatrix, "PNG", outputPath);

            System.out.println("QR code salvo em: " + outputFile.getAbsolutePath());
        } catch (WriterException | IOException e) {
            e.printStackTrace();
        }
    }

    public static void readQRCode(String caminhoImagemQRCode, String caminhoArquivo) {
        try {
            BufferedImage imagem = ImageIO.read(new File(caminhoImagemQRCode));
            BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(new BufferedImageLuminanceSource(imagem)));

            Map<DecodeHintType, Object> hints = new HashMap<>();
            hints.put(DecodeHintType.CHARACTER_SET, "UTF-8");

            MultiFormatReader leitor = new MultiFormatReader();
            Result resultado = leitor.decode(bitmap, hints);

            String textoCombinado = resultado.getText();
            String[] textos = textoCombinado.split("\n");

            System.out.println("Texto 1: " + textos[0]);
            System.out.println("Texto 2: " + textos[1]);
            System.out.println("Texto 3: " + textos[2]);
            System.out.println("Texto 4: " + textos[3]);

            generateCSV(textos, caminhoImagemQRCode,caminhoArquivo);
            Connect.saveData(textos[0], textos[1], Double.parseDouble(textos[2]), Double.parseDouble(textos[3]));

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("Falha ao decodificar o código QR: " + e.getMessage());
        }
    }

    public static void generateCSV(String[] textos, String caminho, String caminhoArquivo) {

        // Verificar se o arquivo já existe
        boolean arquivoExiste = new File(caminhoArquivo).exists();

        try {
            FileWriter escritor = new FileWriter(caminhoArquivo, true);

            // Escrever o cabeçalho do CSV
            if (!arquivoExiste) {
                escritor.append("Nome do Arquivo,Data-Hora do Arquivo,NIF,CATEGORIA,VALOR,IVA\n");
            }

            // Escrever as linhas de dados
            String dataHora = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

            // Escrever uma linha com o nome do arquivo, data-hora e dados extraídos
            escritor.append(caminho).append(",").append(dataHora).append(",").append(textos[0]).append(",")
                    .append(textos[1]).append(",").append(textos[2]).append(",").append(textos[3]).append("\n");

            escritor.flush();
            escritor.close();

            System.out.println("Dados salvos em " + caminhoArquivo);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
