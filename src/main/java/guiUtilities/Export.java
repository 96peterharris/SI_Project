package guiUtilities;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;

public class Export
{
    private String path;

    public Export()
    {
        this.path = "";
    }

    private byte[] stringToJson(HashMap<String, String> contentMap){
        ObjectMapper objectMapper = new ObjectMapper();

        try
        {
            String json = objectMapper.writeValueAsString(contentMap);
            return json.getBytes();
        }
        catch (JsonProcessingException ex)
        {
            ex.printStackTrace();
        }
        return null;
    }

    public void exportMap(Button button, HashMap<String, String> contentMap) {
        final FileChooser fileChooser = new FileChooser();
        Stage stage = (Stage) button.getScene().getWindow();
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Json doc", "*.json"));
        File file = fileChooser.showSaveDialog(stage);
        if (file != null)
        {
            this.path = file.getAbsolutePath();

            try
            {
                Files.write(file.toPath(),  stringToJson(contentMap));
            }
            catch (IOException ex)
            {
                ex.printStackTrace();
            }
        }
    }

    public String getPath()
    {
        return this.path;
    }
}
