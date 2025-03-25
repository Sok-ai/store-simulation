import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Симулирует работу магазина с несколькими кассами и покупателями.
 * Генерирует покупателей, распределяет их по кассам и выводит статистику обслуживания.
 */
public class StoreSimulation {

    /**
     * Точка входа в программу. Запускает симуляцию магазина с двумя кассами.
     *
     * @param args аргументы командной строки (не используются)
     * @throws InterruptedException если выполнение потока прерывается во время ожидания завершения касс
     */
    public static void main(String[] args) throws InterruptedException {
        // Потокобезопасные коллекции
        List<Customer> queue = Collections.synchronizedList(new ArrayList<>());
        List<Long> waitTimes = Collections.synchronizedList(new ArrayList<>());
        Random random = new Random();

        // Генерация покупателей
        for (int i = 1; i <= 5; i++) {
            String name = "Покупатель " + i;
            int serviceTime = (random.nextInt(5) + 1) * 100; // 100-500 мс на обслуживание
            queue.add(new Customer(name, serviceTime));
        }

        long startTime = System.currentTimeMillis();

        // Создание и запуск касс
        Cashier[] cashiers = new Cashier[2];
        for (int i = 0; i < cashiers.length; i++) {
            cashiers[i] = new Cashier(queue, i + 1, waitTimes);
            cashiers[i].start();
        }

        // Ожидание завершения работы касс
        for (Cashier cashier : cashiers) {
            cashier.join();
        }

        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;

        // Подсчет статистики
        int servedCustomers = waitTimes.size();
        long totalWaitTime = 0;
        for (long wait : waitTimes) {
            totalWaitTime += wait;
        }
        double averageWaitTime = servedCustomers > 0 ? (double) totalWaitTime / servedCustomers : 0;

        // Вывод результатов
        System.out.println("\nОбщее время работы: " + totalTime + " мс");
        System.out.println("Обслужено покупателей: " + servedCustomers);
        System.out.println("Среднее время ожидания: " + averageWaitTime + " мс");
    }
}