package com.budget.app.controller;

import com.budget.app.entity.*;
import com.budget.app.service.LineItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/lineitems")
public class LineItemController {

    @Autowired
    private LineItemService lineItemService;

    @PostMapping
    public ResponseEntity<LineItem> createLineItem(@RequestBody LineItemRequestDTO request) {
        LineItem newLineItem = lineItemService.createLineItem(
                request.getLineItemName(), request.getPlannedAmount(), request.getIsIncome(), request.getCategoryId()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(newLineItem);
    }

    @GetMapping
    public ResponseEntity<List<LineItem>> getAllLineItems(@RequestParam(required = false) Boolean isIncome) {
        List<LineItem> lineItems = (isIncome == null) ?
                lineItemService.getAllLineItems() :
                lineItemService.getLineItemsByIncomeType(isIncome);
        return ResponseEntity.ok(lineItems);
    }
}
