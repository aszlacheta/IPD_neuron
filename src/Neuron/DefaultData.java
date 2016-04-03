package Neuron;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aleksandra on 2016-03-20.
 */
public class DefaultData {

    public final static DecimalFormat DECIMAL = new DecimalFormat("#.####");

    public static final List<List<Double>> DATA_IN = new ArrayList<List<Double>>() {{
        add(new ArrayList<Double>() {{
            add((double) 2);
            add((double) 1);
        }});
        add(new ArrayList<Double>() {{
            add((double) 2);
            add((double) 2);
        }});
        add(new ArrayList<Double>() {{
            add((double) 0);
            add((double) 6);
        }});
        add(new ArrayList<Double>() {{
            add((double) -2);
            add((double) 8);
        }});
        add(new ArrayList<Double>() {{
            add((double) -2);
            add((double) 0);
        }});
        add(new ArrayList<Double>() {{
            add((double) 0);
            add((double) 0);
        }});
        add(new ArrayList<Double>() {{
            add((double) 4);
            add((double) -20);
        }});
    }};

    public static final List<Double> RESULTS = new ArrayList<Double>() {{
        add((double) 1);
        add((double) 1);
        add((double) 1);
        add((double) -1);
        add((double) -1);
        add((double) -1);
        add((double) -1);
    }};

    public static final List<List<Double>> EQUATION_DATA_5x3 = new ArrayList<List<Double>>() {{
        add(new ArrayList<Double>() {{
            add((double) 2);
            add((double) -2);
            add((double) 2);
            add((double) -7);
            add((double) 6);
        }});
        add(new ArrayList<Double>() {{
            add((double) 7);
            add((double) -3);
            add((double) -2);
            add((double) 7);
            add((double) 2);
        }});
        add(new ArrayList<Double>() {{
            add((double) 9);
            add((double) 8);
            add((double) -2);
            add((double) 2);
            add((double) -2);
        }});
    }};
    public static final List<Double> EQUATION_RESULTS_5x3 = new ArrayList<Double>() {{
        add((double) -4);
        add((double) 11);
        add((double) 21);
    }};

    public static final List<List<Double>> EQUATION_DATA_5x5 = new ArrayList<List<Double>>() {{
        add(new ArrayList<Double>() {{
            add((double) 2);
            add((double) -2);
            add((double) 2);
            add((double) -7);
            add((double) 6);
        }});
        add(new ArrayList<Double>() {{
            add((double) 7);
            add((double) -3);
            add((double) -2);
            add((double) 7);
            add((double) 2);
        }});
        add(new ArrayList<Double>() {{
            add((double) 2);
            add((double) 2);
            add((double) -1);
            add((double) 1);
            add((double) 4);
        }});
        add(new ArrayList<Double>() {{
            add((double) 9);
            add((double) 8);
            add((double) -2);
            add((double) 2);
            add((double) -2);
        }});
        add(new ArrayList<Double>() {{
            add((double) 4);
            add((double) 8);
            add((double) -3);
            add((double) 3);
            add((double) -1);
        }});
    }};
    public static final List<Double> EQUATION_RESULTS_5x5 = new ArrayList<Double>() {{
        add((double) -4);
        add((double) 11);
        add((double) 9);
        add((double) 21);
        add((double) 16);
    }};

}
