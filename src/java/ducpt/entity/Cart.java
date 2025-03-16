/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ducpt.entity;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author LENOVO-DUCKY
 */
public class Cart {

    private String customerID;
    private Map<String, Integer> items;

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public Map<String, Integer> getItems() {
        return items;
    }

    public void setItems(Map<String, Integer> items) {
        this.items = items;
    }

    public void addItemToCart(String title) {
        if (items == null) {
            items = new HashMap<String, Integer>();
        }
        int quantity = 1;
        if (items.containsKey(title)) {
            quantity = items.get(title) + 1;
        }
        items.put(title, quantity);
    }

    public void removeItemFromCart(String title) {
        if (items == null) {
            System.out.println("items null");
            return;
        }

        if (items.containsKey(title)) {
            System.out.println("Remove "+title);
            items.remove(title);
            if (items.isEmpty()) {
                items = null;
            }
        }
    }
}
