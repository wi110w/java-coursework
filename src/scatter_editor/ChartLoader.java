package scatter_editor;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvParser;
import javafx.collections.ObservableList;
import javafx.scene.chart.Axis;
import javafx.scene.chart.XYChart;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.*;
import java.util.List;

public class ChartLoader {
    private File file;

    ChartLoader(File f, XYChart<Number, Number> chart) {
        this.file = f;

        this.chart = chart;
    }

    void load() throws FileNotFoundException {
        InputStream in = new FileInputStream(file);
        JSONObject root = new JSONObject(new JSONTokener(in));

        JSONObject axesConfig = root.optJSONObject("axes");

        updateAxis(axesConfig, "x", "x", chart.getXAxis());
        updateAxis(axesConfig, "y", "y", chart.getYAxis());

        chart.setTitle(root.getString("title"));

        String baseDir = file.getParent();
        JSONArray dataConfig = root.optJSONArray("data");
        ObservableList<XYChart.Series<Number, Number>> data = chart.getData();
        data.clear();
        for (Object o : dataConfig) {
            try {
                loadDataSet(baseDir, data, (JSONObject) o);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void loadDataSet(String baseDir, List<XYChart.Series<Number, Number>> data, JSONObject setConfig) throws IOException {
        if (setConfig == null) {
            return; // hey! that's not an object
        }

        String name = setConfig.optString("name");
        XYChart.Series<Number, Number> s = new XYChart.Series<>();
        s.setName(name);

        String source = setConfig.getString("source");

        CsvMapper mapper = new CsvMapper();
        mapper.enable(CsvParser.Feature.WRAP_AS_ARRAY);
        InputStream csvFile = new FileInputStream(new File(baseDir, source));

        MappingIterator<String[]> it = mapper.readerFor(String[].class).readValues(csvFile);
        while (it.hasNext()) {
            String[] row = it.nextValue();
            float x = Float.parseFloat(row[0]);
            float y = Float.parseFloat(row[1]);
            s.getData().add(new XYChart.Data<>(x, y));
        }
        data.add(s);
    }

    private void updateAxis(JSONObject config, String key, String defaultName, Axis<Number> axis) {
        String label = (config != null) ? config.optString(key, defaultName) : defaultName;
        axis.setLabel(label);
    }

    public XYChart<Number, Number> getChart() {
        return chart;
    }

    public void setChart(XYChart<Number, Number> chart) {
        this.chart = chart;
    }

    XYChart<Number, Number> chart;
}
