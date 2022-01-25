package com.inventoryservice.inventoryservice.Controller;

import com.inventoryservice.inventoryservice.Repo.InventoryRepo;
import com.inventoryservice.inventoryservice.model.Inventory;
import com.netflix.discovery.converters.Auto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {

    @Autowired
    private InventoryRepo inventoryRepo;

    @GetMapping("/{skuCode}")
    Boolean isInStock(@PathVariable String skuCode){
        try {
            log.info("Checking stock for product with skucode - " + skuCode);
            Inventory inventory=inventoryRepo.findBySkuCode(skuCode).orElseThrow(() -> new RuntimeException("cannot find product by skucode "+skuCode));

         return inventory.getStock()>0;
        }catch (Exception e){
         log.info("An exception has occurred while checkig inventory be skucode ",skuCode);
            throw new RuntimeException("An exception occurred while retrieving Product by skucode "+skuCode+" "+e.getMessage());
        }
    }
}
