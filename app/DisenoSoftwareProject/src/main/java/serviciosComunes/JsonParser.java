/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package serviciosComunes;

import java.io.StringReader;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;

/**
 *
 * @author defre
 */
public class JsonParser {
    public static JsonObject stringToJson(String s){
        JsonObject jsonObject;
        try (JsonReader jsonReader = Json.createReader(new StringReader(s))) {
            jsonObject = jsonReader.readObject();
        }
        return jsonObject;
    }
}
