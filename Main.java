import functions.*;

public class Main {

    public static void main(String[] args) {
        // Создание функции с 5 точками от -2 до 2
        TabulatedFunction func = new TabulatedFunction(-2, 2, 5);

        // Установка значений функции параболы
        for (int i = 0; i < func.getPointsCount(); i++) {
            double x = func.getPointX(i);
            func.setPointY(i, x * x);
        }

        // Вывод значений функции в разных точках
        System.out.println("Тестирование исходной функции:");
        testFunction(func);

        // Изменение точки
        func.setPoint(2, new FunctionPoint(1.5, 2.25));
        System.out.println("\nПосле изменения точки:");
        testFunction(func);

        // Добавление точки
        func.addPoint(new FunctionPoint(2.5, 6.25));
        System.out.println("\nПосле добавления точки:");
        testFunction(func);

        // Удаление точки
        func.deletePoint(1);
        System.out.println("\nПосле удаления точки:");
        testFunction(func);

        System.out.println("\nВсё!");
    }

    public static void testFunction(TabulatedFunction func) {
        double[] testPoints = {-3, -2, -1, 0, 0.5, 1, 2, 3};

        for (double x : testPoints) {
            double value = func.getFunctionValue(x);
            System.out.printf("f(%.1f) = %s\n", x, Double.isNaN(value) ? "не определена" : String.format("%.2f", value));
        }

        System.out.println("\nТочки функции:");
        for (int i = 0; i < func.getPointsCount(); i++) {
            System.out.printf("[%.2f; %.2f] ", func.getPointX(i), func.getPointY(i));
        }
        System.out.println("\n");
    }
}