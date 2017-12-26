package scatter_editor;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvParser;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;

import java.io.*;

public class DataSetLoader {

    private File file;
    private String dataSetName;
    private ScatterChart<Number, Number> chart;

    public DataSetLoader(File f, String datasetName, ScatterChart<Number, Number> sc) {
        file = f;
        dataSetName = datasetName;
        chart = sc;
    }

    void load() throws FileNotFoundException {
        try {
            loadDataSet(file, dataSetName, chart);
        } catch (IOException e) {
            System.err.println("Failed to load dataset: " + e.getMessage());
        }
    }

    private void loadDataSet(File file, String dataSetName, ScatterChart<Number, Number> sc) throws IOException {
        CsvMapper mapper = new CsvMapper();
        mapper.enable(CsvParser.Feature.WRAP_AS_ARRAY);
        InputStream csvFile = new FileInputStream(file);

        XYChart.Series<Number, Number> s = new XYChart.Series<>();
        s.setName(dataSetName);

        MappingIterator<float[]> it = mapper.readerFor(float[].class).readValues(csvFile);
        while (it.hasNext()) {
            float[] row = it.nextValue();
            float x = row[0];
            float y = row[1];
            s.getData().add(new XYChart.Data<>(x, y));
        }
        sc.getData().add(s);
    }
}
