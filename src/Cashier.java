import java.util.List;

/**
 * Представляет кассу в магазине, работающую как отдельный поток.
 * Обрабатывает покупателей из общей очереди и фиксирует время ожидания.
 */
public class Cashier extends Thread {
    private List<Customer> queue;
    private int cashierId;
    private List<Long> waitTimes;

    /**
     * Создаёт новую кассу с указанной очередью покупателей и списком для записи времени ожидания.
     *
     * @param queue     список покупателей для обработки (должен быть потокобезопасным)
     * @param cashierId уникальный идентификатор кассы
     * @param waitTimes список для записи времени ожидания каждого покупателя
     */
    public Cashier(List<Customer> queue, int cashierId, List<Long> waitTimes) {
        this.queue = queue;
        this.cashierId = cashierId;
        this.waitTimes = waitTimes;
    }

    /**
     * Запускает процесс обслуживания покупателей.
     * Извлекает покупателей из очереди, имитирует их обслуживание и записывает время ожидания.
     */
    @Override
    public void run() {
        while (true) {
            Customer customer = null;
            synchronized (queue) {
                if (queue.isEmpty()) {
                    break;
                }
                customer = queue.remove(0);
            }
            if (customer != null) {
                try {
                    customer.setStartServiceTime(System.currentTimeMillis());
                    long waitTime = customer.getStartServiceTime() - customer.getArrivalTime();
                    waitTimes.add(waitTime);
                    System.out.println("Касса " + cashierId + ": Покупатель " + customer.getName() +
                            " начал обслуживание (ожидание: " + waitTime + " мс)");
                    Thread.sleep(customer.getServiceTime());
                    System.out.println("Касса " + cashierId + ": Покупатель " + customer.getName() +
                            " обслужен (время: " + customer.getServiceTime() + " мс)");
                } catch (InterruptedException e) {
                    System.err.println("Ошибка в кассе " + cashierId + ": " + e.getMessage());
                }
            }
        }
    }
}