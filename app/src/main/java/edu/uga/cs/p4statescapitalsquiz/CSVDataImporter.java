package edu.uga.cs.p4statescapitalsquiz;

import android.content.Context;
import android.util.Log;

import com.opencsv.CSVReader;

import java.io.InputStream;
import java.io.InputStreamReader;

public class CSVDataImporter extends AsyncTask<Void, Void> {
    private Context context;
    private QuestionsData questionsData;

    public CSVDataImporter(Context context, QuestionsData questionsData) {
        this.context = context;
        this.questionsData = questionsData;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        // Check if the database is already initialized
        if (!questionsData.isDatabaseInitialized()) {
            try {
                InputStream inputStream = context.getAssets().open("StateCapitals.csv");
                CSVReader reader = new CSVReader(new InputStreamReader(inputStream));
                String[] nextRow;

                while ((nextRow = reader.readNext()) != null) {
                    if (nextRow.length >= 4) {
                        String state = nextRow[0].trim();
                        String capitalCity = nextRow[1].trim();
                        String secondCity = nextRow[2].trim();
                        String thirdCity = nextRow[3].trim();

                        Question question = new Question(state, capitalCity, secondCity, thirdCity);
                        questionsData.storeQuestion(question);
                    }
                }
            } catch (Exception e) {
                Log.e("CSVDataImporter", e.toString());
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {

    }
}
