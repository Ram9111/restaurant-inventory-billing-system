package com.mailservice.kafka;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mailservice.dto.OrderDetailDTO;
import com.mailservice.dto.OrderDetailXrefDTO;
import com.mailservice.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
//Bellow commented code is just normal mail trigger not stile
//@Component
//public class InvoiceMailListener {
//
//    @Autowired
//    private EmailService emailService;
//
//    @Autowired
//    private ObjectMapper objectMapper; // Spring can inject this
//    /* Spring automatically provides an ObjectMapper instance.
//          It is used to convert the incoming JSON string into a Java object. */
//
//    @KafkaListener(topics = "invoice-topic", groupId = "email_group")
//    public void listenInvoiceEvent(String invoiceJson) {
//
//        System.out.println("Received Kafka invoice event: " + invoiceJson);
//
//        try {
//            /* The received message from Kafka is in JSON format.
//               This JSON is converted into an OrderDetailDTO object
//               so that individual fields like customer name, email, total amount
//               can be accessed easily. */
//
//            OrderDetailDTO invoiceEvent = objectMapper.readValue(invoiceJson, OrderDetailDTO.class);
//
//            /* An email is sent to the customer using the values
//               received from the Kafka event. The customer's email address,
//               the subject of the email, and the body containing customer details
//               are all taken dynamically from the invoice event data. */
//            // Send email to the customer's actual email
//            emailService.sendEmail(
//                    invoiceEvent.getCustomerEmail(), // dynamic email
//                    "Invoice Generated",
//                    "Dear " + invoiceEvent.getCustomerName() + ",\n\n"
//                            + "Your invoice has been generated. Details:\n"
//                            + "Order No: " + invoiceEvent.getOrderNo() + "\n"
//                            + "Total: " + invoiceEvent.getTotalAmount() + "\n"
//            );
//
//        } catch (Exception e) {
//            /* If any problem occurs while processing the JSON
//               or sending the email, the error is printed
//               so developers can diagnose what went wrong. */
//            e.printStackTrace();
//        }
//    }
//}

/**
 * InvoiceMailListener listens to Kafka events on the "invoice-topic" and
 * sends a styled HTML invoice email to the customer when an order is placed.
 *
 * Purpose:
 * - Receive invoice details from Kafka
 * - Convert JSON invoice data to Java objects
 * - Generate a formatted HTML invoice with order items and totals
 * - Send email to customer using EmailService
 *
 * Author: Ram Choudhary
 * Date: 28-Nov-2025
 */

@Component
public class InvoiceMailListener {

    @Autowired
    private EmailService emailService;
    /* EmailService is used to send HTML emails to customers */

    @Autowired
    private ObjectMapper objectMapper;
    /* ObjectMapper is used to convert JSON strings from Kafka into Java objects */

    /**
     * Listens to the Kafka topic "invoice-topic" for new invoice events.
     *
     * @param invoiceJson JSON string representing the invoice data
     *
     * Steps:
     * 1. Deserialize the JSON into OrderDetailDTO object
     * 2. Calculate totals for the order
     * 3. Build a styled HTML table with all order items
     * 4. Compose email body with order info and totals
     * 5. Send HTML email to the customer
     */
    @KafkaListener(topics = "invoice-topic", groupId = "email_group")
    public void listenInvoiceEvent(String invoiceJson) {
        try {
            // Convert JSON string to OrderDetailDTO object
            OrderDetailDTO invoice = objectMapper.readValue(invoiceJson, OrderDetailDTO.class);

            BigDecimal subTotal = BigDecimal.ZERO; // Initialize subtotal

            // Build HTML table for order items
            StringBuilder table = new StringBuilder();
            table.append("<table style='width:100%; border-collapse: collapse;'>");

            // Table header row with light gray background
            table.append("<tr style='background-color:#f8f8f8;'>")
                    .append("<th style='border: 1px solid #ddd; padding: 8px;'>Item Name</th>")
                    .append("<th style='border: 1px solid #ddd; padding: 8px;'>Quantity</th>")
                    .append("<th style='border: 1px solid #ddd; padding: 8px;'>Price</th>")
                    .append("<th style='border: 1px solid #ddd; padding: 8px;'>Total</th>")
                    .append("</tr>");

            // Loop through each item in the order
            for (OrderDetailXrefDTO item : invoice.getOrderDetailXrefList()) {
                BigDecimal quantity = item.getQuantity(); // Quantity of the item
                BigDecimal total = item.getSellingPrice().multiply(quantity); // Total price for this item
                subTotal = subTotal.add(total); // Add to subtotal

                // Append HTML table row for this item
                table.append("<tr>")
                        .append("<td style='border: 1px solid #ddd; padding: 8px;'>").append(item.getRecipeName()).append("</td>")
                        .append("<td style='border: 1px solid #ddd; padding: 8px; text-align:center;'>").append(quantity.toPlainString()).append("</td>")
                        .append("<td style='border: 1px solid #ddd; padding: 8px; text-align:right;'>").append(item.getSellingPrice().toPlainString()).append("</td>")
                        .append("<td style='border: 1px solid #ddd; padding: 8px; text-align:right;'>").append(total.toPlainString()).append("</td>")
                        .append("</tr>");
            }

            table.append("</table>"); // Close the HTML table

            // Build full email body with HTML formatting
            String emailBody = "<p>Dear " + invoice.getCustomerName() + ",</p>" +
                    "<p>Your invoice has been successfully generated. Below are the details:</p>" +
                    "<h3>Order Information</h3>" +
                    "<p>Order Number: " + invoice.getOrderNo() + "<br>" +
                    "Order Date: " + invoice.getOrderDate() + "<br>" +
                    "Payment Mode: " + invoice.getPaymentMode() + "</p>" +
                    "<h3>Order Items</h3>" +
                    table.toString() +
                    "<p><b>Sub Total:</b> " + subTotal.toPlainString() + "<br>" +
                    "<b>Discount:</b> " + invoice.getDiscount().toPlainString() + "<br>" +
                    "<b>Tax Amount:</b> " + invoice.getTaxAmount().toPlainString() + "<br>" +
                    "<b>Grand Total:</b> " + invoice.getGrandTotal().toPlainString() + "</p>" +
                    "<p>Thank you for your order!<br>Regards,<br>Your Restaurant</p>";

            // Send HTML email using EmailService
            emailService.sendEmail(
                    invoice.getCustomerEmail(),
                    "Invoice Generated – " + invoice.getOrderNo(),
                    emailBody
            );

        } catch (Exception e) {
            // Print stack trace for debugging if something goes wrong
            e.printStackTrace();
        }
    }
}

