package javaimpls.two.controller;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import javaimpls.two.models.Order;
import javaimpls.two.models.OrderType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static javaimpls.two.utils.Constants.BASE_REQUEST_MAPPING;

@Slf4j
@RestController
@RequestMapping(BASE_REQUEST_MAPPING)
public class ImplTwoController {

    AmazonDynamoDB dynamoDB;
    DynamoDBMapper dynamoDBMapper;

    public ImplTwoController(AmazonDynamoDB dynamoDB, DynamoDBMapper dynamoDBMapper) {
        this.dynamoDB = dynamoDB;
        this.dynamoDBMapper = dynamoDBMapper;
    }

    @PostMapping(path = "/create", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> createTable() {
        log.info("Creating Order table");

        CreateTableRequest createTableRequest = dynamoDBMapper.generateCreateTableRequest(Order.class);
        createTableRequest.setProvisionedThroughput(new ProvisionedThroughput(5L, 5L));
        dynamoDB.createTable(createTableRequest);

        return new ResponseEntity<>("Successfully created Order table.", HttpStatus.OK);
    }

    @PostMapping(path = "/order", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> saveOrders() {
        log.info("Saving orders");

        saveOrderObject(OrderType.BUY);
        saveOrderObject(OrderType.SELL);

        return new ResponseEntity<>("Successfully saved orders.", HttpStatus.OK);
    }

    @GetMapping(path = "/order", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Order>> fetchOrdersByEntitySymbol(@RequestParam String entitySymbol) {
        log.info("Querying for entitySymbol {}", entitySymbol);

        Map<String, AttributeValue> filters = new HashMap<>();
        filters.put(":val1", new AttributeValue().withS(entitySymbol));
        DynamoDBScanExpression queryExpression = new DynamoDBScanExpression()
                .withFilterExpression("entitySymbol = :val1")
                .withExpressionAttributeValues(filters);

        return new ResponseEntity<>(dynamoDBMapper.scan(Order.class, queryExpression), HttpStatus.OK);
    }

    private void saveOrderObject(OrderType orderType) {
        Order order = new Order();
        order.setEntityName("Avenue Supermarts");
        order.setEntitySymbol("DMART");
        order.setMarket("NSE");
        order.setOrderType(orderType.getValue());
        order.setShares(10);
        order.setPrice(4000F);
        dynamoDBMapper.save(order);
    }
}
