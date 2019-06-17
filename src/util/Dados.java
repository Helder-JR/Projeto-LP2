package util;

import Controllers.ECOController;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Classe responsável pela persistência de arquivos do sistema, podendo salvar ou carregar o estado atual em que o mesmo
 * se encontra.
 */
public class Dados {

    /**
     * Tenta salvar o estado atual do ECOController em um arquivo. Caso não seja possível uma mensagem de erro será
     * exibida.
     *
     * @param controller o controller que irá ser salvo.
     * @param filePath o camimnho para o arquivo que irá conter os dados a serem salvos.
     * @return um booleano indicando o sucesso no salvamento do objeto.
     */
    public boolean salvar(ECOController controller, File filePath) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filePath))) {
            out.writeObject(controller);
            return true;
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    /**
     * Tenta carregar o estado atual do ECOController presente em um arquivo, apresentando uma mensagem de erro caso a
     * classe do objeto lido a partir do arquivo não seja uma classe presente no sistema, ou caso o arquivo não seja
     * encontrado.
     *
     * @param filePath o caminho para o arquivo que contém os dados a serem carregados.
     * @return o objeto controller que contém os dados salvos do sistema.
     */
    public ECOController carregar(File filePath) {
        ECOController controller = null;
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filePath))) {
            controller = (ECOController) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return controller;
    }

    public ECOController limparSistema(File filePathSave, File filePathReset) {
        filePathSave.delete();
        return carregar(filePathReset);
    }
}
