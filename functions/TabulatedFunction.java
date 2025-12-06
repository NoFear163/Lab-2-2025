package functions;

public class TabulatedFunction {
    private FunctionPoint[] points;
    private int pointsCount;

    public TabulatedFunction(double leftX, double rightX, int pointsCount) {
        if (leftX >= rightX || pointsCount < 2) {
            return;
        }

        this.pointsCount = pointsCount;
        points = new FunctionPoint[pointsCount + 10]; // Запас на 10 элементов
        double step = (rightX - leftX) / (pointsCount - 1);

        for (int i = 0; i < pointsCount; i++) {
            points[i] = new FunctionPoint(leftX + i * step, 0);
        }
    }

    public TabulatedFunction(double leftX, double rightX, double[] values) {
        this(leftX, rightX, values.length);
        for (int i = 0; i < values.length; i++) {
            points[i].setY(values[i]);
        }
    }

    // ЗАДАНИЕ 4

    public double getLeftDomainBorder() {
        return points[0].getX();
    }

    public double getRightDomainBorder() {
        return points[pointsCount - 1].getX();
    }

    public double getFunctionValue(double x) {
        if (x < getLeftDomainBorder() || x > getRightDomainBorder()) {
            return Double.NaN;
        }

        // Сравнение с машинным эпсилоном
        final double eps = 1e-10;
        for (int i = 0; i < pointsCount; i++) {
            if (Math.abs(points[i].getX() - x) <= eps) {
                return points[i].getY();
            }
        }

        // Поиск интервала
        int i = 0;
        while (i < pointsCount && points[i].getX() < x) i++;

        if (i == 0) return points[0].getY();
        if (i == pointsCount) return points[pointsCount - 1].getY();

        // Линейная интерполяция
        FunctionPoint left = points[i - 1];
        FunctionPoint right = points[i];
        return left.getY() + (right.getY() - left.getY()) * (x - left.getX()) / (right.getX() - left.getX());
    }

    // ЗАДАНИЕ 5

    public int getPointsCount() {
        return pointsCount;
    }

    public FunctionPoint getPoint(int index) {
        return new FunctionPoint(points[index]);
    }

    public void setPoint(int index, FunctionPoint point) {
        if (index < 0 || index >= pointsCount) return;

        // Проверка соседних точек
        if (index > 0 && point.getX() <= points[index - 1].getX()) return;
        if (index < pointsCount - 1 && point.getX() >= points[index + 1].getX()) return;

        points[index] = new FunctionPoint(point);
    }

    public double getPointX(int index) {
        return points[index].getX();
    }

    public void setPointX(int index, double x) {
        if (index < 0 || index >= pointsCount) return;

        // Проверка соседних точек
        if (index > 0 && x <= points[index - 1].getX()) return;
        if (index < pointsCount - 1 && x >= points[index + 1].getX()) return;

        points[index].setX(x);
    }

    public double getPointY(int index) {
        return points[index].getY();
    }

    public void setPointY(int index, double y) {
        if (index >= 0 && index < pointsCount) {
            points[index].setY(y);
        }
    }

    // ЗАДАНИЕ 6

    public void deletePoint(int index) {
        if (pointsCount <= 2 || index < 0 || index >= pointsCount) return;

        System.arraycopy(points, index + 1, points, index, pointsCount - index - 1);
        pointsCount--;
    }

    public void addPoint(FunctionPoint point) {
        // Поиск позиции для вставки
        int i = 0;
        while (i < pointsCount && points[i].getX() < point.getX()) i++;

        // Увеличение массива при необходимости
        if (pointsCount == points.length) {
            FunctionPoint[] newPoints = new FunctionPoint[points.length * 2];
            System.arraycopy(points, 0, newPoints, 0, pointsCount);
            points = newPoints;
        }

        // Сдвиг элементов
        System.arraycopy(points, i, points, i + 1, pointsCount - i);
        points[i] = new FunctionPoint(point);
        pointsCount++;
    }
}