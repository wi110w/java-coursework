package scatter_editor;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvParser;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;

import java.io.*;
import java.net.URL;

public class DataSetLoader {
    private InputStream csvFile;
    private String dataSetName;

    public DataSetLoader(File f, String datasetName) throws FileNotFoundException {
        csvFile = new FileInputStream(f);
        dataSetName = datasetName;
    }

    public DataSetLoader(URL u, String datasetName) throws IOException {
        csvFile = u.openStream();
        dataSetName = datasetName;
    }

    XYChart.Series<Number, Number> load() throws IOException {
        XYChart.Series<Number, Number> s = new XYChart.Series<>();
        s.setName(dataSetName);

        CsvMapper mapper = new CsvMapper();
        mapper.enable(CsvParser.Feature.WRAP_AS_ARRAY);
        MappingIterator<float[]> it = mapper.readerFor(float[].class).readValues(csvFile);
        while (it.hasNext()) {
            float[] row = it.nextValue();
            float x = row[0];
            float y = row[1];
            s.getData().add(new XYChart.Data<>(x, y));
        }
        return s;
    }
}
