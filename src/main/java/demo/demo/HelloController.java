package demo.demo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;
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
    private Text currency;

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
        //Ловим нажатие на кнопку "Узнать погоду"
        getData.setOnAction(event -> {
            // Получаем данные из текстового поля
            String getCity = city.getText().trim();
            if(!getCity.equals("")) { // Данные введены
                // Получаем данные о погоде с OpenWeatherMap
                String output = getUrlContent("http://api.openweathermap.org/data/2.5/weather?q=" + getCity + "&appid=5fce0ef7b8c90e429f0fe6cd33103872&units=metric&lang=RU");
                System.out.println(output);
                if (!output.isEmpty()) { //Введено корректное название города
                    JSONObject obj = new JSONObject(output);
                    // Парсим JSON, добавляем к тестовым полям полученные данные
                    weather.setText("ПОГОДА: " + obj.getJSONArray("weather").getJSONObject(0).getString("description").toUpperCase());
                    time.setText("ТЕКУЩЕЕ ВРЕМЯ: " + (((LocalTime.now().getHour()-3)+(obj.getInt("timezone")/3600))<10?"0":"") +
                            (((LocalTime.now().getHour()-3)+(obj.getInt("timezone")/3600))==24?"00": ((LocalTime.now().getHour()-3)+(obj.getInt("timezone")/3600)))+
                            ":" + (LocalTime.now().getMinute()<10? "0":"") + LocalTime.now().getMinute());
                    String countryAbr = obj.getJSONObject("sys").getString("country");
                    for (int i = 0; i < Countries.STRINGS.length; i++) {
                        if (Countries.STRINGS[i].contains(" " + countryAbr + " ")){
                            String[] countryParse = Countries.STRINGS[i].split(" " + countryAbr + " ");
                            country.setText("СТРАНА: " + countryParse[0]);
                            currency.setText("МЕСТНАЯ ВАЛЮТА: " + countryParse[1]);
                        }
                    }
                    temp.setText("ТЕМПЕРАТУРА: " + Math.round(obj.getJSONObject("main").getDouble("temp")) + (char)176);
                    tempFeelsLike.setText("ОЩУЩАЕТСЯ КАК: " + Math.round(obj.getJSONObject("main").getDouble("feels_like")) + (char)176);
                    tempMin.setText("МИНИМАЛЬНАЯ ЗА СЕГОДНЯ: " + Math.round(obj.getJSONObject("main").getDouble("temp_min")) + (char)176);
                    tempMax.setText("МАКСИМАЛЬНАЯ ЗА СЕГОДНЯ: " + Math.round(obj.getJSONObject("main").getDouble("temp_max")) + (char)176);
                    pressure.setText("ДАВЛЕНИЕ: " + obj.getJSONObject("main").getInt("pressure") + " mmhg");
                    humidity.setText("ВЛАЖНОСТЬ: " + obj.getJSONObject("main").getInt("humidity") + " %");

                }
            }
        });

    }
    private static String getUrlContent(String urlAdress) {
        StringBuffer content = new StringBuffer();

        try {
            System.out.println(urlAdress);
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
