package scatter_editor;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.SequenceWriter;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvParser;
import javafx.collections.ObservableList;
import javafx.scene.chart.Axis;
import javafx.scene.chart.XYChart;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ChartLoader {
    private File file;

    ChartLoader(File f, XYChart<Number, Number> chart) {
        this.file = f;

        this.chart = chart;
    }

    void load() throws FileNotFoundException {
        JSONObject root = loadJsonData(file);
        if (root == null) {
            return;
        }

        JSONObject axesConfig = root.optJSONObject("axes");

        updateAxis(axesConfig, "x", "x", chart.getXAxis());
        updateAxis(axesConfig, "y", "y", chart.getYAxis());

        chart.setTitle(root.getString("title"));

        ObservableList<XYChart.Series<Number, Number>> data = chart.getData();
        data.clear();

        JSONArray dataConfig = root.optJSONArray("data");
        if (dataConfig == null) {
            return;
        }

        String baseDir = file.getParent();
        for (Object o : dataConfig) {
            try {
                loadDataSet(baseDir, data, (JSONObject) o);
            } catch (IOException | JSONException e) {
                System.err.println("Failed to load dataset: " + e.getMessage());
            }
        }
    }

    private static JSONObject loadJsonData(File file) throws FileNotFoundException {
        InputStream in = new FileInputStream(file);
        try {
            return new JSONObject(new JSONTokener(in));
        } catch (JSONException e) {
            System.err.println("Failed to load project: " + e.getMessage());
        }

        return null;
    }

    void save() throws IOException {
        Set<String> existing = loadDataSetSources(file);
        JSONObject root = new JSONObject()
            .put("axes", new JSONObject()
                .put("x", chart.getXAxis().getLabel())
                .put("y", chart.getYAxis().getLabel())
            )
            .put("title", chart.getTitle());

        String baseDir = file.getParent();
        String baseName = file.getName();
        final String ext = ".json";
        if (baseName.length() > ext.length() && baseName.endsWith(ext)) {
            baseName = baseName.substring(0, baseName.length() - ext.length());
        }
        baseName += "-%d.csv";

        JSONArray dataConfig = new JSONArray();
        List<XYChart.Series<Number, Number>> chartData = chart.getData();
        int index = 0;
        for (XYChart.Series<Number, Number> s : chartData) {
            File setFile = null;
            while (setFile == null) {
                setFile = validateName(baseDir, baseName, existing, index++);
            }

            CsvMapper mapper = new CsvMapper();
            OutputStream csvFile = new FileOutputStream(setFile);

            try {
                SequenceWriter writer = mapper.writerFor(Number[].class).writeValues(csvFile);
                for (XYChart.Data<Number, Number> d : s.getData()) {
                    writer.write(new Number[]{d.getXValue(), d.getYValue()});
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            String setFileName = setFile.getName();
            dataConfig.put(new JSONObject()
                    .put("name", s.getName())
                    .put("source", setFileName));
        }
        root.put("data", dataConfig);

        for (String n : existing) {
            boolean deleted = new File(baseDir, n).delete();
        }

        Writer out = new OutputStreamWriter(new FileOutputStream(file));
        root.write(out);
        out.close();
    }

    private File validateName(String baseDir, String baseName, Set<String> existing, int index) {
        String name = String.format(baseName, index);
        File f = new File(baseDir, name);
        if (existing.contains(name)) {
            existing.remove(name);
        } else if (f.exists()) {
            return null;
        }
        return f;
    }

    private static Set<String> loadDataSetSources(File file) {
        Set<String> sources = new HashSet<>();
        if (!file.exists()) {
            return sources;
        }

        JSONObject root = null;
        try {
            root = loadJsonData(file);
        } catch (FileNotFoundException e) {
            return sources;
        }

        if (root == null) {
            return sources;
        }

        JSONArray dataConfig = root.optJSONArray("data");
        if (dataConfig == null) {
            return sources;
        }

        for (Object o : dataConfig) {
            JSONObject setConfig = (JSONObject) o;
            if (setConfig == null) {
                continue;
            }

            try {
                String source = setConfig.getString("source");
                sources.add(source);
            } catch (JSONException ignored) {
            }
        }

        return sources;
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

        MappingIterator<float[]> it = mapper.readerFor(float[].class).readValues(csvFile);
        while (it.hasNext()) {
            float[] row = it.nextValue();
            float x = row[0];
            float y = row[1];
            s.getData().add(new XYChart.Data<>(x, y));
        }
        data.add(s);
    }

    static void loadDataSet(String url, String datasetName) {

    }

    private void updateAxis(JSONObject config, String key, String defaultName, Axis<Number> axis) {
        String label = (config != null) ? config.optString(key, "") : defaultName;
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
