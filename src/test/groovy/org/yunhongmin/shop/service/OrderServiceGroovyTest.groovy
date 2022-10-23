package org.yunhongmin.shop.service


import org.yunhongmin.shop.domain.*
import org.yunhongmin.shop.dto.ItemIdCountDto
import org.yunhongmin.shop.repository.ItemRepository
import org.yunhongmin.shop.repository.OrderItemRepository
import org.yunhongmin.shop.repository.OrderRepository
import org.yunhongmin.shop.repository.UserRepository
import spock.lang.Specification

class OrderServiceGroovyTest extends Specification {
    OrderService orderService
    OrderRepository orderRepository = Mock(OrderRepository)
    UserRepository userRepository = Mock(UserRepository)
    ItemRepository itemRepository = Mock(ItemRepository)
    OrderItemRepository orderItemRepository = Mock(OrderItemRepository)

    def "Order: success"() {
        given:
        orderService = new OrderService(
                orderRepository: orderRepository,
                userRepository: userRepository,
                itemRepository: itemRepository,
                orderItemRepository: orderItemRepository
        )

        and:
        def address = new Address();
        address.setCity("서울")
        address.setStreet("마포구 연남동")
        address.setZipcode("12345")

        def userId = 1L
        def user = new User()
        user.id = 1L
        user.setAddress(address)
        userRepository.findOne(userId) >> user

        def itemIdCountDtos = new ArrayList<ItemIdCountDto>();
        itemIdCountDtos.add(new ItemIdCountDto(2L, 2))
        itemIdCountDtos.add(new ItemIdCountDto(1L, 3))


        def item1 = new Item();
        item1.id = 1L
        item1.setStockQuantity(10)
        item1.setPrice(1000)

        def item2 = new Item();
        item2.id = 2L
        item2.setStockQuantity(10)
        item2.setPrice(2000)

        itemRepository.findByIdIn(_ as List<Integer>) >> [item1, item2]

        when:
        def order = orderService.order(userId, itemIdCountDtos)

        then:
        def delivery = order.getDelivery()
        delivery.getAddress() == address
        delivery.getStatus() == DeliveryStatus.READY

        order.getUser().getId() == userId
        order.getStatus() == OrderStatus.ORDER

        order.getTotalPrice() == 3 * 1000 + 2 * 2000

    }
}
