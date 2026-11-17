import java.util.Scanner;

public class Main {
    private static final Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("=== Матрицы комплексных чисел ===");
        boolean working = true;
        while (working) {
            printMenu();
            int choice = readInt("Ваш выбор: ");
            try {
                switch (choice) {
                    case 1 -> {
                        ComplexMatrix a = readMatrix("A");
                        ComplexMatrix b = readMatrix("B");
                        System.out.println("\nA + B:");
                        System.out.println(a.plus(b));
                    }
                    case 2 -> {
                        ComplexMatrix a = readMatrix("A");
                        ComplexMatrix b = readMatrix("B");
                        System.out.println("\nA * B:");
                        System.out.println(a.times(b));
                    }
                    case 3 -> {
                        ComplexMatrix a = readMatrix("A");
                        ComplexMatrix b = readMatrix("B");
                        System.out.println("\nA / B:");
                        System.out.println(a.dividedBy(b));
                    }
                    case 4 -> {
                        ComplexMatrix a = readMatrix("A");
                        System.out.println("\nТранспонированная A:");
                        System.out.println(a.transposed());
                    }
                    case 5 -> {
                        ComplexMatrix a = readMatrix("A");
                        System.out.println("\nОпределитель A: " + a.determinant());
                    }
                    case 0 -> working = false;
                    default -> System.out.println("Нет такого пункта");
                }
            } catch (Exception e) {
                System.out.println("Ошибка: " + e.getMessage());
            }
        }
        System.out.println("Пока.");
    }

    private static void printMenu() {
        System.out.println("\n1 - Сложение");
        System.out.println("2 - Умножение");
        System.out.println("3 - Деление");
        System.out.println("4 - Транспонирование");
        System.out.println("5 - Определитель");
        System.out.println("0 - Выход");
    }

    private static int readInt(String prompt) {
        System.out.print(prompt);
        while (true) {
            try {
                return Integer.parseInt(input.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.print("Введите число: ");
            }
        }
    }

    private static ComplexMatrix readMatrix(String name) {
        int rows = readInt("Строк в матрице " + name + ": ");
        int cols = readInt("Столбцов в матрице " + name + ": ");
        ComplexMatrix matrix = new ComplexMatrix(rows, cols);
        System.out.println("Вводите элементы как 'real imag' (например, '3 -2' для 3 - 2i):");
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                while (true) {
                    System.out.print("Элемент [" + i + "][" + j + "]: ");
                    String[] parts = input.nextLine().trim().split("\\s+");
                    if (parts.length == 2) {
                        try {
                            double real = Double.parseDouble(parts[0]);
                            double imag = Double.parseDouble(parts[1]);
                            matrix.setCell(i, j, new Complex(real, imag));
                            break;
                        } catch (NumberFormatException e) {
                            System.out.println("Неверный формат, попробуйте снова.");
                        }
                    } else {
                        System.out.println("Введите два числа через пробел.");
                    }
                }
            }
        }
        return matrix;
    }
}
