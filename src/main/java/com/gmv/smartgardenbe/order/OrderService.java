package com.gmv.smartgardenbe.order;

import com.gmv.smartgardenbe.entity.Orders;

public interface OrderService {

    /**
     *  Обработка заказа, отправка сообщения в mqtt broker
     */
    void processing(Orders orders);


    /**
     * Обработка callback сообщения, обновление статусов
     * @param orderId - идентификатор заказа
     */
    void updateStatus(Long orderId);
}
