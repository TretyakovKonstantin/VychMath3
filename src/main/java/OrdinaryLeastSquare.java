class OrdinaryLeastSquare {


    private static double[][] makeSystem(double[][] xyTable, int basis) {
        double[][] matrix = new double[basis][basis + 1];

        for (int i = 0; i < basis; i++) {
            for (int j = 0; j < basis; j++) {
                double sumA = 0;
                double sumB = 0;
                for (int k = 0; k < xyTable[0].length; k++) {
                    sumA += Math.pow(xyTable[0][k], i + j);
                    sumB += xyTable[1][k] * Math.pow(xyTable[0][k], i);
                }
                matrix[i][j] = sumA;
                matrix[i][basis] = sumB;
            }
        }

        return matrix;
    }

    static double[] solve(double[] x, double[] y, int basis) {
        double[][] xyTable = new double[2][x.length];

        for (int i = 0; i < x.length; i++) {
            xyTable[0][i] = x[i];
            xyTable[1][i] = y[i];
        }

        double[][] matrix = makeSystem(xyTable, basis + 1);
        return Gauss.getSolution(matrix);
    }

    private static double getFurthestPoint(double[] x, double[] y, double[] result, int basis) {
        int maxDeltaIndex = 0;
        double delta = 0.0;
        for (int i = 0; i < x.length; i++) {
            double func = 0.0;

            for (int j = 0; j < basis + 1; j++) {
                func += result[j] * Math.pow(x[i], j);
            }
            if (Math.abs(func - y[i]) > delta) {
                delta = Math.abs(func - y[i]);
                maxDeltaIndex = i;
            }
        }
        return maxDeltaIndex;
    }

    static double[][] getPointsWithoutFurthest(double[] x, double[] y, double[] result, int basis) {
        int length = x.length;
        double maxDeltaIndex = getFurthestPoint(x, y, result, basis);

        double[] newX = new double[length - 1];
        double[] newY = new double[length - 1];
        for (int i = 0; i < length - 1; i++) {
            if (i < maxDeltaIndex) {
                newX[i] = x[i];
                newY[i] = y[i];
            } else {
                newX[i] = x[i + 1];
                newY[i] = y[i + 1];
            }
        }
        return new double[][]{newX, newY};
    }

    static double[] getFunctionValues(double[] x, double[] coeffs, double basis) {
        double[] funcValues = new double[x.length];

        for (int i = 0; i < x.length; i++) {
            double s = 0.D;

            for (int j = 0; j < basis + 1; j++) {
                s += coeffs[j] * Math.pow(x[i], j);
            }
            funcValues[i] = s;
        }

        return funcValues;
    }
}