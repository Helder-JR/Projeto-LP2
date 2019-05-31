package util;

import Controllers.ECOController;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Dados {

    public void salvar(ECOController controller, File fileName) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileName))) {
            out.writeObject(controller);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public ECOController carregar(File fileName) {
        ECOController controller = null;
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName))) {
            controller = (ECOController) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return controller;
    }
}
