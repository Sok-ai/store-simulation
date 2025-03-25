/**
 * Представляет покупателя в магазине с именем, временем обслуживания и временными метками.
 */
public class Customer {
    private final String name;
    private final int serviceTime;
    private final long arrivalTime; // Сделано final для неизменяемости
    private long startServiceTime;

    /**
     * Создаёт нового покупателя с указанным именем и временем обслуживания.
     *
     * @param name        имя покупателя, не должно быть null
     * @param serviceTime время обслуживания в миллисекундах, должно быть положительным
     * @throws IllegalArgumentException если имя null или время обслуживания отрицательное
     */
    public Customer(String name, int serviceTime) {
        if (name == null) {
            throw new IllegalArgumentException("Имя покупателя не может быть null");
        }
        if (serviceTime < 0) {
            throw new IllegalArgumentException("Время обслуживания не может быть отрицательным");
        }
        this.name = name;
        this.serviceTime = serviceTime;
        this.arrivalTime = System.currentTimeMillis();
    }

    /**
     * Возвращает имя покупателя.
     *
     * @return имя покупателя
     */
    public String getName() {
        return name;
    }

    /**
     * Возвращает время обслуживания покупателя.
     *
     * @return время обслуживания в миллисекундах
     */
    public int getServiceTime() {
        return serviceTime;
    }

    /**
     * Возвращает время прибытия покупателя в очередь.
     *
     * @return время прибытия в миллисекундах
     */
    public long getArrivalTime() {
        return arrivalTime;
    }

    /**
     * Устанавливает время начала обслуживания покупателя.
     *
     * @param time время начала обслуживания в миллисекундах
     */
    public void setStartServiceTime(long time) {
        this.startServiceTime = time;
    }

    /**
     * Возвращает время начала обслуживания покупателя.
     *
     * @return время начала обслуживания в миллисекундах
     */
    public long getStartServiceTime() {
        return startServiceTime;
    }
}