package guiUtilities;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Import
{
    private String path;

    public Import()
    {
        this.path = "";
    }

    public HashMap<String, ArrayList<HashMap<String, String>>> importJson(Button button) throws JsonProcessingException
    {
        final FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Json doc", "*.json"));
        Stage stage = (Stage) button.getScene().getWindow();
        File file = fileChooser.showOpenDialog(stage);
        String loadedContent = "";
        HashMap<String, ArrayList<HashMap<String, String>>> result = new HashMap<>();

        if(file != null)
        {
            this.path = file.getAbsolutePath();
            try (BufferedReader reader = new BufferedReader(new FileReader(file)))
            {
                String line;

                while ((line = reader.readLine()) != null)
                {
                    loadedContent += line;
                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            result = this.jsonToMap(loadedContent);
        }

        return result;
    }

    private HashMap<String, ArrayList<HashMap<String, String>>> jsonToMap(String content) throws JsonProcessingException
    {
        ObjectMapper objectMapper = new ObjectMapper();

        return objectMapper.readValue(content, new TypeReference<HashMap<String, ArrayList<HashMap<String, String>>>>(){});
    }

    public String getPath()
    {
        return this.path;
    }
}
