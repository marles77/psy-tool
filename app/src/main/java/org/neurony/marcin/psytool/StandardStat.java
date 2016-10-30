package org.neurony.marcin.psytool;

/**
 * Created by Marcin on 2016-04-23.
 */
public class StandardStat {

    //z scores
    double tabZ[] = {-2.326, -2.054, -1.881, -1.751, -1.645, -1.555, -1.476,
            -1.405, -1.341, -1.282, -1.227, -1.175, -1.126, -1.08, -1.036, -0.994,
            -0.954, -0.915, -0.878, -0.842, -0.806, -0.772, -0.739, -0.706, -0.674,
            -0.643, -0.613, -0.583, -0.553, -0.524, -0.496, -0.468, -0.44, -0.412,
            -0.385, -0.358, -0.332, -0.305, -0.279, -0.253, -0.228, -0.202, -0.176,
            -0.151, -0.126, -0.1, -0.075, -0.05, -0.025, 0, 0.025, 0.05, 0.075, 0.1,
            0.126, 0.151, 0.176, 0.202, 0.228, 0.253, 0.279, 0.305, 0.332, 0.358,
            0.385, 0.412, 0.44, 0.468, 0.496, 0.524, 0.553, 0.583, 0.613, 0.643,
            0.674, 0.706, 0.739, 0.772, 0.806, 0.842, 0.878, 0.915, 0.954, 0.994,
            1.036, 1.08, 1.126, 1.175, 1.227, 1.282, 1.341, 1.405, 1.476, 1.555,
            1.645, 1.751, 1.881, 2.054, 2.326};



    //interpretation
    double x_wanlas[] = {-3, -2, -1, -0.65, 0.65, 1, 2, 3, 4};

    double x_lezak[] = {-2, -1.3, -0.6, 0.6, 1.3, 2, 4};


    Double ssParam[][] = {
            {50.0, 10.0}, //T
            {5.5, 2.0},   //Sten
            {5.0, 2.0},   //Stanine
            {100.0, 15.0},//IQ
            {10.0, 3.0}   //WP / SS

    };
    String stLabel[][] = {
            {"-3", "1", "20", "55", "1"},
            {"-2", "3", "30", "70", "4"},
            {"-1", "16", "40", "85", "7"},
            {"0", "50", "50", "100", "10"},
            {"1", "84", "60", "115", "13"},
            {"2", "97", "70", "130", "16"},
            {"3", "99", "80", "145", "19"}

    };

}
