package Adapters;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.time.LocalDate;

public class LocalDateAdapter extends TypeAdapter<LocalDate> {

    @Override
    public void write(JsonWriter jsonWriter, LocalDate localDate) throws IOException {
        //OPTION 1
        if(localDate == null){
            jsonWriter.nullValue();
            return;
        }
        jsonWriter.beginObject();
        jsonWriter.name("day");
        jsonWriter.value(localDate.getDayOfMonth());
        jsonWriter.name("month");
        jsonWriter.value(localDate.getMonthValue());
        jsonWriter.name("year");
        jsonWriter.value(localDate.getYear());
        jsonWriter.endObject();

        // OPTION 2

        /*...
        String date = String.format("%02d/%02d/%d",localDate.getDayOfMonth(),
                localDate.getMonthValue(),localDate.getYear());

        jsonWriter.value(date);
         */
    }

    @Override
    public LocalDate read(JsonReader jsonReader) throws IOException {

        if (jsonReader.peek()==JsonToken.NULL){
            jsonReader.nextNull();
            return null;
        }

        int day ,month ,year ;

        String stringDate = jsonReader.nextString();
        String[] values = stringDate.split("-");

        year = Integer.parseInt(values[0]);
        month = Integer.parseInt(values[1]);
        day = Integer.parseInt(values[2]);


        return LocalDate.of(year,month,day);

    }

}
