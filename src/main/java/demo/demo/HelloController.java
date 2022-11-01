package demo.demo;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import org.json.JSONObject;

public class HelloController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Text country;

    @FXML
    private Text currencyExchangeRate;

    @FXML
    private Text currencyExchangeRate1;

    @FXML
    private TextField city;

    @FXML
    private Button getData;

    @FXML
    private Text humidity;

    @FXML
    private Text pressure;

    @FXML
    private Text temp;

    @FXML
    private Text tempFeelsLike;

    @FXML
    private Text tempMax;

    @FXML
    private Text tempMin;

    @FXML
    private Text time;

    @FXML
    private Text weather;

    @FXML
    void initialize() {
        getData.setOnAction(event -> {
            // Получаем данные из текстового поля
            String getUserCity = city.getText().trim();
            if(!getUserCity.equals("")) { // Если данные не пустые
                // Получаем данные о погоде с сайта openweathermap
                String output = getUrlContent("http://api.openweathermap.org/data/2.5/weather?q=" + getUserCity + "&appid=5fce0ef7b8c90e429f0fe6cd33103872&units=metric&lang=ru");
                System.out.println(output);

                if (!output.isEmpty()) { // Нет ошибки и такой город есть

                    JSONObject obj = new JSONObject(output);
                    // Обрабатываем JSON и устанавливаем данные в текстовые надписи
                    weather.setText(obj.getJSONArray("weather").getJSONObject(0).getString("description") +
                            obj.getJSONObject("sys").getString("country"));
//                    temp_feels.setText("Ощущается: " + obj.getJSONObject("main").getDouble("feels_like"));
//                    temp_max.setText("Максимум: " + obj.getJSONObject("main").getDouble("temp_max"));
//                    temp_min.setText("Минимум: " + obj.getJSONObject("main").getDouble("temp_min"));
//                    pressure.setText("Давление: " + obj.getJSONObject("main").getDouble("pressure"));
                }
            }
        });

    }
    private static String getUrlContent(String urlAdress) {
        StringBuffer content = new StringBuffer();

        try {
            URL url = new URL(urlAdress);
            URLConnection urlConn = url.openConnection();

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
            String line;

            while((line = bufferedReader.readLine()) != null) {
                content.append(line + "\n");
            }
            bufferedReader.close();
        } catch(Exception e) {
            System.out.println("Такой город был не найден!");
        }
        return content.toString();
    }


}
