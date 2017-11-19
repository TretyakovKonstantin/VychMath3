import java.util.Scanner;
import javax.swing.JFrame;

public class Main extends JFrame {
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        try {
            double[][] points = readCoordinates();
            double[] x = points[0];
            double[] y = points[1];
            int basis = readBasis(x.length);

            double[] result = OrdinaryLeastSquare.solve(x, y, basis);
            printResult(result);

            double[][] pointsWithoutFurthest = OrdinaryLeastSquare.getPointsWithoutFurthest(x, y, result, basis);
            double[] xCoordinatesWithoutFurthestPoint = pointsWithoutFurthest[0];
            double[] yCoordinatesWithoutFurthestPoint = pointsWithoutFurthest[1];

            double[] newResult = OrdinaryLeastSquare.solve(xCoordinatesWithoutFurthestPoint, yCoordinatesWithoutFurthestPoint, basis);
            printResult(newResult);

            double[] functionValues = OrdinaryLeastSquare.getFunctionValues(x, result, basis);
            double[] newFunctionValues = OrdinaryLeastSquare.getFunctionValues(xCoordinatesWithoutFurthestPoint, newResult, basis);

            GUICreater.buildGrafic(x, y, functionValues, xCoordinatesWithoutFurthestPoint, newFunctionValues);

        } catch (NumberFormatException e) {
            System.out.println("Аргументы должны быть типа double");
        } catch (ArithmeticException e) {
            System.out.println("Базис должен быть хотя бы на 2 больше, чем количество точек");
        } catch (IllegalArgumentException e) {
            System.out.println("Количество координат x и y должно быть равно");
        }
    }

    private static double[][] readCoordinates() {
        System.out.println("Введите координаты x через пробел:");
        String[] xStrRepresentation = scanner.nextLine().split(" ");
        System.out.println("Введите координаты y через пробел:");
        String[] yStrRepresentation = scanner.nextLine().split(" ");
        if (yStrRepresentation.length != xStrRepresentation.length) {
            throw new IllegalArgumentException();
        }

        int length = xStrRepresentation.length;

        double[] x = new double[length];
        double[] y = new double[length];
        for (int i = 0; i < length; i++) {
            x[i] = Double.parseDouble(xStrRepresentation[i]);
            y[i] = Double.parseDouble(yStrRepresentation[i]);
        }
        return new double[][]{x, y};
    }

    private static int readBasis(int coordinatesCount) {
        System.out.println("Введите степень многочлена");
        int basis = scanner.nextInt();

        if (basis > coordinatesCount - 2) {
            throw new ArithmeticException();
        }
        return basis;
    }

    private static void printResult(double[] result) {
        for (int i = 0; i < result.length; i++) {
            System.out.print(result[i] + "c^" + i + ((i == result.length - 1) ? "" : " + "));
        }
        System.out.println(" = 0");
    }

}