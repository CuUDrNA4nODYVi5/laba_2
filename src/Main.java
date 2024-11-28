import java.io.PrintStream;
import java.util.Scanner;

public class Main {
    public static Scanner scanner = new Scanner(System.in);
    public static PrintStream out = System.out;
    public static void main(String[] args) {
        // 1. Считываем размеры массива N и M и его элементы
        System.out.println("Введите размеры массива N и M:");
        int N = scanner.nextInt();// количество строк
        int M = scanner.nextInt();//количество столбцов

        int[][] matrix = new int[N][M];//вводим двумерный массив
        System.out.println("Введите элементы массива:");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                matrix[i][j] = scanner.nextInt();//выводим произвольный массив с клавиатуры
            }
        }

        System.out.println("Введите значение T:");
        int T = scanner.nextInt(); // произвольное значение температуры

        // 2. Сортируем столбцы массива
        int[] lowerCount = new int[M]; // меньше T
        int[] higherCount = new int[M]; //больше T

// Подсчёт точек ниже и выше T для каждого столбца
        for (int j = 0; j < M; j++) {
            for (int i = 0; i < N; i++) {
                if (matrix[i][j] < T) {
                    lowerCount[j]++;//  считаем количество точек в каждом столбце, значения которых меньше T
                } else if (matrix[i][j] > T) {
                    higherCount[j]++;// считаем количество точек в каждом столбце, значения которых больше T
                }
            }
        }

        // Сортируем столбцы пузырьковой сортировкой
        for (int j = 0; j < M - 1; j++) { //каждый проход сравнивает соседние элементы и меняет их местами, если они находятся в неправильном порядке
            for (int k = 0; k < M - j - 1; k++) { //каждый проход сравнивает уже отсортированные элементы с индексами
                if (lowerCount[k] > lowerCount[k + 1] ||
                        (lowerCount[k] == lowerCount[k + 1] && higherCount[k] > higherCount[k + 1])) { // сравниваем значения точек относительно T с соседним столбцом
//Меняем столбцы местами, так как нужно расставить их по возрастанию
                    for (int i = 0; i < matrix.length; i++) { //
                        int temp = matrix[i][k];
                        matrix[i][k] = matrix[i][k+1];
                        matrix[i][k+1] = temp;
                    }
// Меняем данные в массиве подсчёта, так как изменили порядок столбцов
                    int temp = lowerCount[k];
                    lowerCount[k] = lowerCount[k + 1];
                    lowerCount[k + 1] = temp;

                    temp = higherCount[k];
                    higherCount[k] = higherCount[k + 1];
                    higherCount[k + 1] = temp;
                }
            }
        }

        // 3. Находим самую часто встречающуюся температуру
        int[] frequency = new int[201]; // Диапазон температур от -100 до 100

        for (int i = 0; i < matrix.length; i++) { // Проходим по строкам массива
            for (int j = 0; j < matrix[i].length; j++) { // Проходим по элементам в каждой строке
                frequency[matrix[i][j] + 100]++; // Смещение для работы с отрицательными значениями
            }
        }

        int maxFrequency = 0; // хранит максимальные частоты
        int mostFrequent = -100; // хранит индекс элемента
        for (int i = 0; i < frequency.length; i++) {
            if (frequency[i] > maxFrequency || (frequency[i] == maxFrequency && i - 100 < mostFrequent)) {
                maxFrequency = frequency[i]; // обновляет новую максимальную частоту
                mostFrequent = i - 100;// обновляет индекс с наибольшей частотой(или наименьшим индексом)
            }
        }
        System.out.println("Самая часто встречающаяся температура: " + mostFrequent);

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.print(matrix[i][j] + " ");
            }
                System.out.println();
        }

                // Подзадача 4
                System.out.println("Элементы массива выполнены в виде спирали:");

                int top = 0;// индекс первого элемента столбца
                int bottom = N - 1;// индекс последнего элемента столбца
                int left = 0; // индекс первого элемента строки
                int right = M - 1; // индекс последнего элемента строки

                // рассматриваем случаи существования спирали
                if (N==M && M > 2) { // в других случаях в массиве отсутствует центр, а значит спирали, начинающейся в центре, не существует
                    int[] arr = new int[N * M];
                    int count = 0;
                    while (top <= bottom && left <= right) { // рассматривает случаи до границ массива (снизу, сверху, слева, справа)
                        for (int i = left; i <= right; i++) { // начинаем с первого левого элемента строки и движемся до тех пор, пока справа элементы строки не закончатся
                            arr[count] = matrix[top][i]; // строка копирует элементы из двумерного массива в одномерный
                            count++; // счетчик увеличивается, показывая существование свободной позиции в массиве
                        }
                        top++; // поворачиваемся на 90 градусов по часовой стрелке, переходя от строки к столбцу
                        for (int i = top; i <= bottom; i++) { // // начинаем с первого верхнего элемента столбца и движемся до тех пор, пока снизу элементы столбца не закончатся
                            arr[count] = matrix[i][right];// строка копирует элементы из двумерного массива в одномерный
                            count++;// счетчик увеличивается, показывая существование свободной позиции в массиве
                        }
                        right--; // поворачиваемся на 90 градусов по часовой стрелке, переходя от столбца к строке
                        if (top <= bottom) { // если еще остались элементы, то рассматриваем те же случаи, поменяв местами начало и конец
                            for (int i = right; i >= left; i--) {
                                arr[count] = matrix[bottom][i];
                                count++;
                            }
                            bottom--;// поворачиваемся на 90 градусов по часовой стрелке, переходя от строки к столбцу
                        }
                        if (left <= right) {// если еще остались элементы, то рассматриваем те же случаи, поменяв местами начало и конец
                            for (int i = bottom; i >= top; i--) {
                                arr[count] = matrix[i][left];
                                count++;
                            }
                            left++;// поворачиваемся на 90 градусов по часовой стрелке, переходя от столбца к строке
                        }
                    }
                    for (int i = arr.length - 1; i >= 0; i--) { // развернем получившийся одномерный массив
                        System.out.print(arr[i] + " "); // получим массив в виде спирали, начиная от центра и двигаясь против часовой стрелки
                    }
                }
                else { // не забываем про условия, при которых необходимой спирали не существует
                    System.out.print("Невозможно вывести массив в виде спирали, так как нет центрального элемента ");
                }
            System.out.println();

                // Подзадача 5
                System.out.println("Обновленный массив:");
                //выводим отсортированный массив
                for (int i = 0; i < N; i++) {
                    for (int j = 0; j < M; j++) {
                        if (matrix[i][j] < T) { // находим элементы, значения которых меньше T
                            matrix[i][j] = T; // заменяем эти элементы T
                        }
                        System.out.print(matrix[i][j] + " "); // выводим получившийся массив
                    }
                    System.out.println();
                }
            }
        }




