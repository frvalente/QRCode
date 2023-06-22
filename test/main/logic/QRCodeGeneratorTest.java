package main.logic;

import org.junit.*;


public class QRCodeGeneratorTest {

    @Test
    public void testGenerateQRCode() {
        // Chama o método que deseja testar
        String qrCodeImagePath = "./MyQRCodeTEST.png";
        QRCodeGenerator.generateQRCode("102\nAlojamento\n100\n0.23",qrCodeImagePath);

        // Verifica as condições desejadas após a execução do método
        // Por exemplo, verifica se o arquivo do QR Code foi criado com sucesso
        // e se existe no caminho especificado.
        
        java.io.File qrCodeFile = new java.io.File(qrCodeImagePath);
        Assert.assertTrue(qrCodeFile.exists());
    }

    @Test
    public void testReadQRCode() {
        // Gera um QR Code para leitura
        String qrCodeImagePath = "./MyQRCodeTEST.png";
        // Chama o método que deseja testar
        String csvFilePath = "outputTEST.csv";

        QRCodeGenerator.readQRCode(qrCodeImagePath,csvFilePath);

        // Verifica as condições desejadas após a execução do método
        // Por exemplo, verifica se o arquivo CSV foi criado e se contém
        // os dados esperados.
        java.io.File csvFile = new java.io.File(csvFilePath);
        Assert.assertTrue(csvFile.exists());

        // Também é possível verificar o conteúdo do arquivo CSV, se necessário
        // Por exemplo, é possível ler o arquivo e verificar dados específicos.
    }

    @Test
    public void testGenerateCSV() {
        // Cria um arquivo temporário de QR Code para teste
        String qrCodeImagePath = "testQRCode.png";
        String csvFilePath = "outputTEST.csv";

        QRCodeGenerator.generateQRCode("Teste QR Code",qrCodeImagePath);
        java.io.File qrCodeFile = new java.io.File(qrCodeImagePath);
        Assert.assertTrue(qrCodeFile.exists());

        // Chama o método que deseja testar
        String[] textos = {"Teste", "Dados", "123.45", "10.0"};
        QRCodeGenerator.generateCSV(textos, qrCodeImagePath,csvFilePath);

        // Verifica as condições desejadas após a execução do método
        // Por exemplo, verifica se o arquivo CSV foi criado e se contém
        // os dados esperados.
     
        java.io.File csvFile = new java.io.File(csvFilePath);
        Assert.assertTrue(csvFile.exists());

        // Também é possível verificar o conteúdo do arquivo CSV, se necessário
        // Por exemplo, é possível ler o arquivo e verificar dados específicos.
    }

    // Adicione métodos de teste para os outros métodos públicos em QRCodeGenerator

}

